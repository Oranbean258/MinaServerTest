package com.example.ms.game.basic;

public class Mob {
    private int mobId;
    private String stats;
    private int[] spawnPoint = new int[2];
    private float[] position = new float[2];

    public void spawn() {

    }

    public Mob(int mobId, int[] spawnPoint) {
        this.mobId = mobId;
        this.spawnPoint = spawnPoint;
        this.stats = "notSpawn";
    }

    public int getMobId() {
        return mobId;
    }

    public void setMobId(int mobId) {
        this.mobId = mobId;
    }

    public int[] getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(int[] spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }
}
