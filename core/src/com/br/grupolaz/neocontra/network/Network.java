package com.br.grupolaz.neocontra.network;

import com.br.grupolaz.neocontra.network.packets.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


public class Network {
    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;

    static public void register(EndPoint endpoint) {
        Kryo kryo = endpoint.getKryo();

        kryo.register(AddPlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(PlayersConnected.class);
        kryo.register(PlayerPosition.class);
        kryo.register(KeyPressed.class);
        kryo.register(KeyReleased.class);
        kryo.register(int[].class);
    }
}
