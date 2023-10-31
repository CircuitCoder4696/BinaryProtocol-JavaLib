package impl.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class PacketBuff extends Thread {
    public boolean running= true;
    TCP.Client client;
    protected static Logger log= new Logger();
    protected ByteBuffer request= ByteBuffer.allocate(65536).order(ByteOrder.LITTLE_ENDIAN);
    ByteBuffer response= ByteBuffer.allocate(750000).order(ByteOrder.LITTLE_ENDIAN);
    protected static boolean between(int start, int value, int stop) {
        if(value < start)return false;
        if(value >= stop)return false;
        return true;
    };
    public PacketBuff(TCP.Client client) {
        this.client= client;
    };
};
