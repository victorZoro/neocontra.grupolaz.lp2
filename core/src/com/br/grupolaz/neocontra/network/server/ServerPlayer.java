package com.br.grupolaz.neocontra.network.server;

import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {
    public float x, y;
    public String playerClass;
    public int id;
    public int lifeCount;
    public Connection conn;
    public boolean crouching;
    public boolean walking, right;
}
