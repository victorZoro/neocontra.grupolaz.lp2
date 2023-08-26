package com.br.grupolaz.neocontra.enums;

public enum Layers {
    GROUND(2),
    STAIRS(3),
    SEALEVEL(4),
    CEILING(5)
    ;

    final int layer;

    Layers(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

}
