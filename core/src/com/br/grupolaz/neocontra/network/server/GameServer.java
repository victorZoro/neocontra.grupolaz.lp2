package com.br.grupolaz.neocontra.network.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.network.Network;
import com.br.grupolaz.neocontra.network.packets.*;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameServer extends Listener {
    static private Server server;
    private final Map<Integer, ServerPlayer> players = new HashMap<>();
    private final Array<Integer> connectionIDs = new Array<>();

    public GameServer() throws IOException {
        server = new Server();

        Network.register(server);

        server.bind(Network.TCP_PORT, Network.UDP_PORT);

        server.start();

        server.addListener(this);

        Gdx.app.log("Server", "Up and running.");

        Log.set(Log.LEVEL_DEBUG);
    }

    public static void shutDownServer() {
        server.close();
        try {
            server.dispose();
        } catch (IOException e) {
            throw new RuntimeException(e + "\n Can't dispose of server.");
        }
    }

    public Server getServer() {
        return server;
    }

    public Map<Integer, ServerPlayer> getPlayers() {
        return players;
    }

    public Array<Integer> getConnectionIDs() {
        return connectionIDs;
    }

    @Override
    public void connected(Connection connection) {
        Gdx.app.log("Server", "Started connection with id " + connection.getID());

        ServerPlayer serverPlayer = new ServerPlayer();
        serverPlayer.conn = connection;

        AddPlayer addedPlayer = new AddPlayer();
        addedPlayer.id = connection.getID();

        server.sendToAllExceptTCP(connection.getID(), addedPlayer);

        for(ServerPlayer player: players.values()) {
            AddPlayer addPlayer = new AddPlayer();
            addPlayer.id = player.conn.getID();
            connection.sendTCP(addPlayer);
        }

        players.put(connection.getID(), serverPlayer);
        connectionIDs.add(connection.getID());

        System.out.println(Integer.toString(connectionIDs.size));

        if(connectionIDs.size == 2) {
            ConnectionReady connectionReady = new ConnectionReady();
            connectionReady.ready = true;
            System.out.println("Two players connected. Let's play!");
            server.sendToTCP(connection.getID(), connectionReady);
        }
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof UpdatePlayer) {
            System.out.println("Received update player packet from id " + connection.getID());
        }

        if(object instanceof UpdatePosition) {
            System.out.println("Received update position packet from id " + connection.getID());
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Server disconnected.");

        RemovePlayer packet = new RemovePlayer();
        packet.id = connection.getID();
        server.sendToAllExceptTCP(connection.getID(), packet);
    }

}
