package com.example.ms.game;

import org.apache.mina.core.session.IoSession;

public class MapleClient {
    private int clientId;
    private IoSession session;
    private MapleCharacter mapleCharacter;

    public MapleClient(IoSession session, MapleCharacter mapleCharacter) {
        this.session = session;
        this.mapleCharacter = mapleCharacter;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public MapleCharacter getMapleCharacter() {
        return mapleCharacter;
    }

    public void setMapleCharacter(MapleCharacter mapleCharacter) {
        this.mapleCharacter = mapleCharacter;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
