package impl.notch;

import impl.io.PacketBuff;

public class Types {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;
    private final PacketBuff buff;
    public Types(PacketBuff buff) {
        this.buff= buff;
    };
    public int readVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;
        while (true) {
            currentByte = buff.readReqByte();
            value |= (currentByte & SEGMENT_BITS) << position;
            if ((currentByte & CONTINUE_BIT) == 0) break;
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
            currentByte = this.buff.readReqByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;
            if ((currentByte & CONTINUE_BIT) == 0) break;
            position += 7;
            if (position >= 64) throw new RuntimeException("VarLong is too big");
        };
        return value;
    };
    public void writeVarInt(int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                this.buff.writeResByte((byte) value);
                break;
            };
            this.buff.writeResByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
        };
    };
    public void writeVarLong(long value) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                this.buff.writeResByte((byte) value);
                break;
            };
            this.buff.writeResByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
        };
    };
};
