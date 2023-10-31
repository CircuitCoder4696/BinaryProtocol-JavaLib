package game.impl;

import impl.io.ServerBuff;

public class Orientation implements coords_t {
    double x,z;
    @Override public void decodeReq(ServerBuff buff) {
        double[] d00= new double[2];
        buff.readReq(d00);
        this.x= d00[0];
        this.z= d00[1];
    };
    @Override public void encodeRes(ServerBuff buff) {
        buff.writeRes(new double[] {this.x,this.z});
    };
};
