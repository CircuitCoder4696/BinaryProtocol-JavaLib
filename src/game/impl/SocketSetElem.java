package game.impl;

import java.util.ArrayList;

public class SocketSetElem {
    static ArrayList<SocketSetElem> socketset;
    static int counter= 0;
    byte[] uid;
    int clientId;
    private SocketSetElem(byte[] uid) {
        this.uid= uid;
        this.clientId= counter++;
    };
    public static int getSocketId(byte[] uid) {
        for(SocketSetElem ss: socketset)if(uid.equals(ss.uid)) {
            return ss.clientId;
        };
        var slot= new SocketSetElem(uid);
        socketset.add(slot);
        return slot.clientId;
    };
};
