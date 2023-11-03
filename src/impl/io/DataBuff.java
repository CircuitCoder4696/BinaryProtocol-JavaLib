package impl.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataBuff implements buffer_t {
    protected static Logger log= new Logger();
    private ByteBuffer data;
    int ro,wo;
    public DataBuff(ServerBuff buff, int size) {
        this.ro= 0;
        this.wo= size;
        byte[] data= new byte[size];
        buff.readReq(data);
        this.data= ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
    };
    public DataBuff(ClientBuff buff, int size) {
        this.ro= 0;
        this.wo= size;
        byte[] data= new byte[size];
        buff.readRes(data);
        this.data= ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
    };
    public DataBuff(int size) {
        this.ro= 0;
        this.wo= size;
        this.data= ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);
    };
    public void resetOffsets() {
        this.ro= 0;
        this.wo= 0;
    };
    public void endian(ByteOrder order) { this.data.order(order); };
    public int reader() { return this.ro; };
    public int writer() { return this.wo; };
    public void reader(int offset) { this.ro= offset; };
    public void writer(int offset) { this.wo= offset; };
    public int remaining() {
        if(this.ro > this.wo)return 0;
        return this.wo - this.ro;
    };
    public byte readByte() { byte[] result= new byte[1]; this.read(result); return result[0]; };
    public short readShort() { short[] result= new short[1]; this.read(result); return result[0]; };
    public int readUShortAsInt() {
        int result= this.readShort();
        if(result < 0)result += 65536;
        return result;
    };
    public int readInt() { int[] result= new int[1]; this.read(result); return result[0]; };
    public long readLong() { long[] result= new long[1]; this.read(result); return result[0]; };
    public void writeByte(byte value) { this.data.position(this.wo); this.data.put(value); this.wo= this.data.position(); };
    public void writeShort(short value) { this.data.position(this.wo); this.data.putShort(value); this.wo= this.data.position(); };
    public void writeInt(int value) { this.data.position(this.wo); this.data.putInt(value); this.wo= this.data.position(); };
    public void writeLong(long value) { this.data.position(this.wo); this.data.putLong(value); this.wo= this.data.position(); };
    public void read(byte[] data) {
        if((this.data.limit() - this.data.position()) < data.length)log.critical("Attempted to read data beyond the buffer!  ");
        this.data.position(this.ro);
        this.data.get(data);
        this.ro= this.data.position();
    };
    protected void read(char[] data) {
        if((this.data.limit() - this.data.position()) < data.length)log.critical("Attempted to read data beyond the buffer!  ");
        this.data.position(this.ro);
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.data.getChar();
        this.ro= this.data.position();
    };
    protected void read(short[] data) {
        if((this.data.limit() - this.data.position()) < (data.length * 2))log.critical("Attempted to read data beyond the buffer!  ");
        this.data.position(this.ro);
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.data.getShort();
        this.ro= this.data.position();
    };
    protected void read(int[] data) {
        if((this.data.limit() - this.data.position()) < (data.length * 4))log.critical("Attempted to read data beyond the buffer!  ");
        this.data.position(this.ro);
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.data.getInt();
        this.ro= this.data.position();
    };
    protected void read(float[] data) {
        if((this.data.limit() - this.data.position()) < (data.length * 4))log.critical("Attempted to read data beyond the buffer!  ");
        this.data.position(this.ro);
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.data.getFloat();
        this.ro= this.data.position();
    };
    protected void read(long[] data) {
        if((this.data.limit() - this.data.position()) < (data.length * 8))log.critical("Attempted to read data beyond the buffer!  ");
        this.data.position(this.ro);
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.data.getLong();
        this.ro= this.data.position();
    };
    public void read(double[] data) {
        if((this.data.limit() - this.data.position()) < (data.length * 8))log.critical("Attempted to read data beyond the buffer!  ");
        this.data.position(this.ro);
        int i,j= data.length;
        for(i=0;i<j;i++)data[i]= this.data.getDouble();
        this.ro= this.data.position();
    };
    public void write(byte[] data) {
        this.data.position(this.wo);
        this.data.put(data);
        this.wo= this.data.position();
    };
    protected void write(char[] data) {
        this.data.position(this.wo);
        int i,j= data.length;
        for(i=0;i<j;i++)this.data.putChar(data[i]);
        this.wo= this.data.position();
    };
    protected void write(short[] data) {
        this.data.position(this.wo);
        int i,j= data.length;
        for(i=0;i<j;i++)this.data.putShort(data[i]);
        this.wo= this.data.position();
    };
    protected void write(int[] data) {
        this.data.position(this.wo);
        int i,j= data.length;
        for(i=0;i<j;i++)this.data.putInt(data[i]);
        this.wo= this.data.position();
    };
    protected void write(float[] data) {
        this.data.position(this.wo);
        int i,j= data.length;
        for(i=0;i<j;i++)this.data.putFloat(data[i]);
        this.wo= this.data.position();
    };
    protected void write(long[] data) {
        this.data.position(this.wo);
        int i,j= data.length;
        for(i=0;i<j;i++)this.data.putLong(data[i]);
        this.wo= this.data.position();
    };
    public void write(double[] data) {
        this.data.position(this.wo);
        int i,j= data.length;
        for(i=0;i<j;i++)this.data.putDouble(data[i]);
        this.wo= this.data.position();
    };
    public String readReqCStr() {
        this.data.position(this.ro);
        int start,stop;
        start= this.data.position();
        stop= start;
        while(stop < this.data.limit() && this.data.get(stop) != 0)
            stop++;
        int size= stop - start;
        byte[] result= new byte[size];
        this.data.get(result);
        this.ro= this.data.position();
        return new String(result);
    };
};
