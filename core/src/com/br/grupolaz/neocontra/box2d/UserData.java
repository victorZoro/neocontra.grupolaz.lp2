package com.br.grupolaz.neocontra.box2d;

import com.br.grupolaz.neocontra.enums.UserDataType;

//Inspired by MartianRun
public abstract class UserData {
    
    protected UserDataType userDataType;
    protected float witdh;
    protected float height;

    public UserData(){}

    public UserData(float width, float height) {
        this.witdh = width;
        this.height = height;
    }

    /**
     * @return UserDataType return the userDataType
     */
    public UserDataType getUserDataType() {
        return userDataType;
    }

    /**
     * @return float return the witdh
     */
    public float getWitdh() {
        return witdh;
    }

    /**
     * @param witdh the witdh to set
     */
    public void setWitdh(float witdh) {
        this.witdh = witdh;
    }

    /**
     * @return float return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }

}
