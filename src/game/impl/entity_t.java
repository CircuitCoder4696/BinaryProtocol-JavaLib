package game.impl;

import impl.io.ServerBuff;

public interface entity_t extends packet_t {
    public void decodeFile(ServerBuff data);
    public void decodeReq(ServerBuff data);
    public void encodeFile(ServerBuff data);
    public void encodeRes(ServerBuff data);
};
