package com.br.grupolaz.neocontra.enums;

public enum Layers {
    GROUND(1),
    STAIRS(2),
    SEALEVEL(3),
    CEILING(4)
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
