package game.impl;

import impl.io.PacketBuff;

public interface entity_t extends packet_t {
    public void decodeFile(PacketBuff data);
    public void decodeReq(PacketBuff data);
    public void encodeFile(PacketBuff data);
    public void encodeRes(PacketBuff data);
};
