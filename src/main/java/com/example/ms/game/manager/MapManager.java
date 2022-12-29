package com.example.ms.game.manager;

import com.example.ms.game.basic.MapleMap;
import com.example.ms.net.ThreadManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class MapManager {
    private static MapManager instance = null;

    public static MapManager getInstance() {
        if (instance == null) {
            instance = new MapManager();
        }
        return instance;
    }

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public Map<Integer,MapleMap> allMaps = new HashMap<>();

    public void init() {
        if (instance == null) {
            log.error("Null MapManager Instance!");
            return;
        }

        MapleMap map1 = new MapleMap(0, Collections.emptyMap());
        MapleMap map2 = new MapleMap(1, Collections.emptyMap());
        allMaps.put(map1.getMapId(),map1);
        allMaps.put(map2.getMapId(),map2);
        isRunning.set(false);
        log.info("MapManager init over!");
    }

    public void start() {
        isRunning.set(true);
        ThreadManager.getInstance().newTask(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
        log.info("MapManager is start!");
    }

    public void update() {
        while (isRunning.get()) {
            allMaps.forEach((key,value)-> {
                if(!value.isSleep().get() && value.isAbleToSend().get()){
                    ThreadManager.getInstance().newTask(new Runnable() {
                        @Override
                        public void run() {
                            value.update();
                        }
                    });
                }

            });
        }
    }

    public void stop() {
        isRunning.set(false);
        log.info("MapManager is stop!");
    }

    public void activeOneMap(int mapId){
        allMaps.get(mapId).isSleep().set(false);
    }
}
