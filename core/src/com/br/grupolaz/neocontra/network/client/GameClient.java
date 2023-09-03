package com.br.grupolaz.neocontra.network.client;

import com.br.grupolaz.neocontra.network.Network;
import com.br.grupolaz.neocontra.network.packets.AddPlayer;
import com.br.grupolaz.neocontra.network.packets.RemovePlayer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class GameClient extends Listener {
    private Client client;

    public boolean connect() {
        client = new Client();
        Network.register(client);
        client.addListener(this);

        client.start();
        try {
            client.connect(Network.TIMEOUT, "localhost", Network.TCP_PORT, Network.UDP_PORT);
            return true;
        } catch (IOException e) {
            client.close();
            return false;
        }
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof AddPlayer) {
            AddPlayer player = (AddPlayer) object;
            System.out.println("Received add player packet from id " + player.id);
        }

        if(object instanceof RemovePlayer) {
            RemovePlayer player = (RemovePlayer) object;
            System.out.println("Received remove player packet from id " + player.id);
        }
    }

    public Client getClient() {
        return client;
    }
}
