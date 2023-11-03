package impl.io;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ServerBuff extends PacketBuff {
    public boolean running= true;
    protected static Logger log= new Logger();
    public int sliceStart, sliceStop;
    public void setSliceOffsets(int size) {   //   `-1` sets both the packet-start and packet-stop to 0, and the `packetDataRemaining` tells you how much data is left in the slice.
        this.sliceStart= this.request.position();
        this.sliceStop = this.sliceStart + size;
        if(size == -1) {
            this.sliceStart= 0;
            this.sliceStop= 0;
        };
    };
    public int sliceRemaining() { return this.sliceStop - this.request.position(); };
    public byte readReqByte() { return this.request.get(); };
    protected short readReqShort() { return this.request.getShort(); };
    public int readReqInt() { return this.request.getInt(); };
    public long readReqLong() { return this.request.getLong(); };
    public void writeResByte(byte value) { this.response.put(value); };
    public void writeResShort(short value) { this.response.putShort(value); };
    public void writeResInt(int value) { this.response.putInt(value); };
    public void writeResLong(long value) { this.response.putLong(value); };
    public byte[] getReq() {
        int size= this.request.limit() - this.request.position();
        byte[] result= new byte[size];
        this.request.get(result);
        return result;
    };
    public byte[] getReq(int size) {
        int _size= Math.min(this.request.limit() - this.request.position(), size);
        byte[] result= new byte[size];
        int i;
        for(i=0;i<_size;i++)
            result[i]= this.request.get();
        return result;
    };
    protected static boolean between(int start, int value, int stop) {
        if(value < start)return false;
        if(value >= stop)return false;
        return true;
    };
    public void readReq(byte[] data) {
        if((this.request.limit() - this.request.position()) < data.length)log.critical("Can't read outside the buffer!  ");
        try {
            this.request.get(data);
        } catch (BufferOverflowException ex) {
            log.critical("Buffer underflow exception!  ");
        };
    };
    protected void readReq(char[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.request.getChar();
    };
    protected void readReq(short[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.request.getShort();
    };
    protected void readReq(int[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.request.getInt();
    };
    protected void readReq(float[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.request.getFloat();
    };
    protected void readReq(long[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.request.getLong();
    };
    public void readReq(double[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.request.getDouble();
    };
    public void writeRes(byte[] data) {
        this.response.put(data);
    };
    protected void writeRes(char[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.response.putChar(data[i]);
    };
    protected void writeRes(short[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.response.putShort(data[i]);
    };
    protected void writeRes(int[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.response.putInt(data[i]);
    };
    protected void writeRes(float[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.response.putFloat(data[i]);
    };
    protected void writeRes(long[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.response.putLong(data[i]);
    };
    public void writeRes(double[] data) {
        int i,j= data.length;
        for(i=0;i<j;i++)this.response.putDouble(data[i]);
    };
    public ServerBuff(TCP.Client client) {
        super(client);
    };
    public String readReqCStr() {
        int start,stop;
        start= this.request.position();
        stop= start;
        while(stop < this.request.limit() && this.request.get(stop) != 0)
            stop++;
        int size= stop - start;
        byte[] result= new byte[size];
        this.request.get(result);
        return new String(result);
    };
    public abstract void onCall(String ipAddr, int port);
    public byte[] ____processClientRequest(String ipAddr, int port, byte[] request) {
        this.request= ByteBuffer.wrap(request).order(ByteOrder.LITTLE_ENDIAN);
        this.response.position(0);
        this.onCall(ipAddr, port);
        byte[] result= new byte[this.response.position()];
        this.response.position(0);
        this.response.get(result);
        return this.response.array();
    };
};
