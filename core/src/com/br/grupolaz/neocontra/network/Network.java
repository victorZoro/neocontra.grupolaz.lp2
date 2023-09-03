package com.br.grupolaz.neocontra.network;

import com.br.grupolaz.neocontra.network.packets.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    static public final int TCP_PORT = 54555;
    static public final int UDP_PORT = 54777;
    static public final int TIMEOUT = 5000;

    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

        kryo.register(AddPlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(UpdatePlayer.class);
        kryo.register(UpdatePosition.class);
        kryo.register(ConnectionReady.class);
    }
}
