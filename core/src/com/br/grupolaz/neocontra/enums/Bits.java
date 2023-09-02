package com.br.grupolaz.neocontra.enums;

public enum Bits {
    DEFAULT((short) 1),
    PLAYER((short) 2),
    ENEMY((short) 4),
    BULLET((short) 8),
    DEAD((short) 16),
    WALL((short) 32),
    DEAD_PLATFORM((short) 64),
    ALIVE_PLATFORM((short) 128),
    STAIRS((short) 256),
    WATER((short) 512),
    ;

    final short bitType;

    Bits(short bitType) {
        this.bitType = bitType;
    }

    public short getBitType() {
        return bitType;
    }

}
