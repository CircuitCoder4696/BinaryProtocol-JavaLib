package game.impl;

import impl.io.ServerBuff;

public interface coords_t extends entityData_t {
    public void decodeReq(ServerBuff buff);
    public void encodeRes(ServerBuff buff);
};
