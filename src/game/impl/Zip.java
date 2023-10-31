package game.impl;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Zip {
    static Deflater deflater= new Deflater();
    static Inflater inflater= new Inflater();
    public static byte[] decode(byte[] data) {
        inflater.setInput(data);
        // deflater.finish();
        try {
            int compressedDataLength= inflater.inflate(data);
            return data;
        } catch (DataFormatException ex) {
            ex.printStackTrace();
        };
        return null;
    };
    public static byte[] encode(byte[] data) {
        deflater.setInput(data);
        deflater.finish();
        int compressedDataLength= deflater.deflate(data);
        return data;
    };
};
