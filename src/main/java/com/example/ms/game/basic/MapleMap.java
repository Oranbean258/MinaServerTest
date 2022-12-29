package com.example.ms.game.basic;

import com.example.ms.game.MapleClient;
import com.example.ms.utils.ByteTransformUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapleMap {
    private int mapId;
    private Map<Integer,Mob> wildMobs = new HashMap<>();
    private AtomicBoolean isSleep = new AtomicBoolean();
    private Map<Integer, MapleClient> clients = new HashMap<>();
    private final int asleepTime = 60000;
    private int asleepCount = 0;
    private AtomicBoolean isAbleToSend = new AtomicBoolean();

    public MapleMap(int mapId, Map<Integer, Mob> wildMobs) {
        this.mapId = mapId;
        this.wildMobs = wildMobs;
        this.isSleep.set(true);
        this.isAbleToSend.set(true);
    }

    public void update(){
        if(!isSleep.get()){
            isAbleToSend.set(false);
            if(!clients.isEmpty()){
                asleepCount = 0;
                Map<Integer, MapleClient> tmp = new HashMap<>(clients);
                for (int i = 0; i < tmp.size(); i++) {
                    for (int j = i+1; j < tmp.size(); j++) {
                        float[] tt1 = {j,tmp.get(j).getMapleCharacter().getPosition()[0],tmp.get(j).getMapleCharacter().getPosition()[1]};
                        float[] tt2 = {i,tmp.get(i).getMapleCharacter().getPosition()[0],tmp.get(i).getMapleCharacter().getPosition()[1]};

                        tmp.get(i).getSession().write(ByteTransformUtil.floatArrayToByteArray(tt1));
                        tmp.get(j).getSession().write(ByteTransformUtil.floatArrayToByteArray(tt2));
                    }
                }
            }else {
                asleepCount++;
                if(asleepCount==asleepTime){
                    isSleep.set(true);
                    asleepCount = 0;
                }
            }
            isAbleToSend.set(true);
        }
    }

    public int getMapId() {
        return mapId;
    }

    public AtomicBoolean isSleep() {
        return isSleep;
    }

    public AtomicBoolean isAbleToSend() {
        return isAbleToSend;
    }

    public Map<Integer, MapleClient> getClients() {
        return clients;
    }
}
