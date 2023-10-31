package impl.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ClientBuff extends PacketBuff {
    public boolean running= true;
    protected static Logger log= new Logger();
    public byte readResByte() { return this.response.get(); };
    protected short readResShort() { return this.response.getShort(); };
    public int readResInt() { return this.response.getInt(); };
    public long readResLong() { return this.response.getLong(); };
    public void writeReqByte(byte value) { this.request.put(value); };
    public void writeReqShort(short value) { this.request.putShort(value); };
    public void writeReqInt(int value) { this.request.putInt(value); };
    public void writeReqLong(long value) { this.request.putLong(value); };
    protected static boolean between(int start, int value, int stop) {
        if(value < start)return false;
        if(value >= stop)return false;
        return true;
    };
    protected void readRes(byte[] data) {
        this.response.get(data);
    };
    protected void readRes(char[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.response.getChar();
    };
    protected void readRes(short[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.response.getShort();
    };
    protected void readRes(int[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.response.getInt();
    };
    protected void readRes(float[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.response.getFloat();
    };
    protected void readRes(long[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.response.getLong();
    };
    public void readRes(double[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.response.getDouble();
    };
    protected void writeReq(byte[] data) {
        this.request.put(data);
    };
    protected void writeReq(char[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.request.putChar(data[i]);
    };
    protected void writeReq(short[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.request.putShort(data[i]);
    };
    protected void writeReq(int[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.request.putInt(data[i]);
    };
    protected void writeReq(float[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.request.putFloat(data[i]);
    };
    protected void writeReq(long[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.request.putLong(data[i]);
    };
    public void writeReq(double[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.request.putDouble(data[i]);
    };
    public ClientBuff(TCP.Client client) {
        super(client);
    };
    public String getResCStr() {
        int start,stop;
        start= this.response.position();
        stop= start;
        while(stop < this.response.limit() && this.response.get(stop) != 0)
            stop++;
        int size= stop - start;
        byte[] result= new byte[size];
        this.response.get(result);
        return new String(result);
    };
    public abstract byte[] call(String ipAddr, int port, byte[] call);   //   Write an override in your client to handle the binary-protocol for a game, or app.  
    @Override public void run() {
        int limit= 4;
        while(running && (limit--) > 0) {
            System.out.println("Ran thread.  ");
        };
    };
};
