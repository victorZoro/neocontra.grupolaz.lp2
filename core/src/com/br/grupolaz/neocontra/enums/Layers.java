package com.br.grupolaz.neocontra.enums;

public enum Layers {
    GROUND(2),
    STAIRS(3),
    PLATFORMS(4),
    WALLS(5),
    SEALEVEL(6),
    CEILING(7)
    ;

    final int layer;

    Layers(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public static int length() {
        return 8;
    }
}
