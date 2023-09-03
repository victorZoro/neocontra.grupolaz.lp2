package com.br.grupolaz.neocontra.network.server;

import com.br.grupolaz.neocontra.network.Network;
import com.br.grupolaz.neocontra.network.packets.AddPlayer;
import com.br.grupolaz.neocontra.network.packets.PlayerPosition;
import com.br.grupolaz.neocontra.network.packets.PlayersConnected;
import com.br.grupolaz.neocontra.network.packets.RemovePlayer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

public class GameServer {
    Server server;

    public GameServer() {
        server = new Server();

        Network.register(server);

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof AddPlayer) {
                    AddPlayer packet = (AddPlayer) object;
                    System.out.println("Add player: " + packet.id);
                }

                if(object instanceof PlayerPosition) {
                    PlayerPosition packet = (PlayerPosition) object;
                    System.out.println("Player position: " + packet.id + " " + packet.x + " " + packet.y);
                }

                if(object instanceof PlayersConnected) {
                    PlayersConnected packet = (PlayersConnected) object;
                    System.out.println("Players connected: " + packet.ids.length);
                }

                if(object instanceof RemovePlayer) {
                    RemovePlayer packet = (RemovePlayer) object;
                    System.out.println("Remove player: " + packet.id);
                }
            }

            @Override
            public void connected(Connection connection) {
                System.out.println("Client connected with ID: " + connection.getID());
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client disconnected with ID: " + connection.getID());
            }
        });

        try {
            server.bind(Network.TCP_PORT);
        } catch (IOException e) {
            throw new RuntimeException("Could not bind server to port: " + Network.TCP_PORT);
        }

        server.start();
    }

    public static void main(String[] args) {
        Log.set(Log.LEVEL_DEBUG);
        new GameServer();
    }
}
