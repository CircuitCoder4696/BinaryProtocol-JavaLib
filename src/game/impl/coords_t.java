package game.impl;

import impl.io.PacketBuff;

public interface coords_t extends entityData_t {
    public void decodeReq(PacketBuff buff);
    public void encodeRes(PacketBuff buff);
};
