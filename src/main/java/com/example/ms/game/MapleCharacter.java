package com.example.ms.game;

public class MapleCharacter {
    private float[] position = new float[2];
    private int jobClass;

    public MapleCharacter(float[] position, int jobClass) {
        this.position = position;
        this.jobClass = jobClass;
    }
    public MapleCharacter(){}

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public int getJobClass() {
        return jobClass;
    }

    public void setJobClass(int jobClass) {
        this.jobClass = jobClass;
    }
}
