package impl.notch;

import impl.io.ClientBuff;
import impl.io.DataBuff;
import impl.io.ServerBuff;
import impl.io.buffer_t;

public class ServerTypes {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;
    private final buffer_t buff;
    public ServerTypes(buffer_t buff) {
        this.buff= buff;
    };
    public int readVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;
        while (true) {
            if(this.buff instanceof ClientBuff) {
                currentByte= ((ClientBuff)buff).readResByte();
                value |= (currentByte & SEGMENT_BITS) << position;
                if ((currentByte & CONTINUE_BIT) == 0) break;
            };
            if(this.buff instanceof DataBuff) {
                currentByte= ((DataBuff)buff).readByte();
                value |= (currentByte & SEGMENT_BITS) << position;
                if ((currentByte & CONTINUE_BIT) == 0) break;
            };
            if(this.buff instanceof ServerBuff) {
                currentByte= ((ServerBuff)buff).readReqByte();
                value |= (currentByte & SEGMENT_BITS) << position;
                if ((currentByte & CONTINUE_BIT) == 0) break;
            };
            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt is too big");
        };
        return value;
    };
    public long readVarLong() {
        long value = 0;
        int position = 0;
        byte currentByte;
        while (true) {
            if(this.buff instanceof ClientBuff) {
                currentByte= ((ClientBuff)this.buff).readResByte();
                value |= (long) (currentByte & SEGMENT_BITS) << position;
                if ((currentByte & CONTINUE_BIT) == 0) break;
            };
            if(this.buff instanceof DataBuff) {
                currentByte= ((DataBuff)this.buff).readByte();
                value |= (long) (currentByte & SEGMENT_BITS) << position;
                if ((currentByte & CONTINUE_BIT) == 0) break;
            };
            if(this.buff instanceof ServerBuff) {
                currentByte= ((ServerBuff)this.buff).readReqByte();
                value |= (long) (currentByte & SEGMENT_BITS) << position;
                if ((currentByte & CONTINUE_BIT) == 0) break;
            };
            position += 7;
            if (position >= 64) throw new RuntimeException("VarLong is too big");
        };
        return value;
    };
    public void writeVarInt(int value) {
        while (true) {
            if(this.buff instanceof ClientBuff) {
                if ((value & ~SEGMENT_BITS) == 0) {
                    ((ClientBuff)this.buff).writeReqByte((byte) value);
                    break;
                };
                ((ClientBuff)this.buff).writeReqByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            };
            if(this.buff instanceof DataBuff) {
                if ((value & ~SEGMENT_BITS) == 0) {
                    ((DataBuff)this.buff).writeByte((byte) value);
                    break;
                };
                ((DataBuff)this.buff).writeByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            };
            if(this.buff instanceof ServerBuff) {
                if ((value & ~SEGMENT_BITS) == 0) {
                    ((ServerBuff)this.buff).writeResByte((byte) value);
                    break;
                };
                ((ServerBuff)this.buff).writeResByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            };
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
        };
    };
    public void writeVarLong(long value) {
        while (true) {
            if(this.buff instanceof ClientBuff) {
                if ((value & ~((long) SEGMENT_BITS)) == 0) {
                    ((ClientBuff)this.buff).writeReqByte((byte) value);
                    break;
                };
                ((ClientBuff)this.buff).writeReqByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            };
            if(this.buff instanceof DataBuff) {
                if ((value & ~((long) SEGMENT_BITS)) == 0) {
                    ((DataBuff)this.buff).writeByte((byte) value);
                    break;
                };
                ((DataBuff)this.buff).writeByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            };
            if(this.buff instanceof ServerBuff) {
                if ((value & ~((long) SEGMENT_BITS)) == 0) {
                    ((ServerBuff)this.buff).writeResByte((byte) value);
                    break;
                };
                ((ServerBuff)this.buff).writeResByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            };
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
        };
    };
    public String readString() {
        int size= readVarInt();
        byte[] data= new byte[size];
        if(this.buff instanceof ClientBuff) ((ClientBuff)this.buff).readRes(data);
        if(this.buff instanceof DataBuff) ((DataBuff)this.buff).read(data);
        if(this.buff instanceof ServerBuff) ((ServerBuff)this.buff).readReq(data);
        return new String(data);
    };
    public void writeString(String data) {
        int size= data.length();
        writeVarInt(size);
        if(this.buff instanceof ClientBuff) ((ClientBuff)this.buff).writeReq(data.getBytes());
        if(this.buff instanceof DataBuff) ((DataBuff)this.buff).write(data.getBytes());
        if(this.buff instanceof ServerBuff) ((ServerBuff)this.buff).writeRes(data.getBytes());
    };
};
