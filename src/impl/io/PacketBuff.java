package impl.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class PacketBuff implements buffer_t {
    public boolean running= true;
    TCP.Client client;
    boolean bigEndianReqBuff,bigEndianResBuff;
    protected static Logger log= new Logger();
    protected ByteBuffer request= ByteBuffer.allocate(65536).order(ByteOrder.LITTLE_ENDIAN);
    protected ByteBuffer response= ByteBuffer.allocate(750000).order(ByteOrder.LITTLE_ENDIAN);
    protected static boolean between(int start, int value, int stop) {
        if(value < start)return false;
        if(value >= stop)return false;
        return true;
    };
    protected void littleEndianRequests() {
        if(!this.bigEndianReqBuff)return;
        this.request.order(ByteOrder.LITTLE_ENDIAN);
        this.bigEndianReqBuff= false;
    };
    protected void bigEndianRequests() {
        if(this.bigEndianReqBuff)return;
        this.request.order(ByteOrder.BIG_ENDIAN);
        this.bigEndianReqBuff= true;
    };
    protected void littleEndianResposes() {
        if(!this.bigEndianReqBuff)return;
        this.response.order(ByteOrder.LITTLE_ENDIAN);
        this.bigEndianReqBuff= false;
    };
    protected void bigEndianResposes() {
        if(this.bigEndianReqBuff)return;
        this.response.order(ByteOrder.BIG_ENDIAN);
        this.bigEndianReqBuff= true;
    };
    public PacketBuff(TCP.Client client) {
        this.client= client;
    };
};
