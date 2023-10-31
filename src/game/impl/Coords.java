package game.impl;

import impl.io.PacketBuff;

public class Coords implements coords_t {
    long world;
    double x,y,z;
    @Override public void decodeReq(PacketBuff buff) {
        this.world= buff.readReqLong();
        double[] d00= new double[3];
        buff.readReq(d00);
        this.x= d00[0];
        this.y= d00[1];
        this.z= d00[2];
    };
    @Override public void encodeRes(PacketBuff buff) {
        buff.writeResLong(this.world);
        buff.writeRes(new double[] {this.x,this.y,this.z});
    };
};
