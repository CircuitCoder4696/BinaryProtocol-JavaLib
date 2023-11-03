package impl.util;

import java.util.Base64;

public class Mime64 {
    public static Base64.Decoder decoder= Base64.getDecoder();
    public static Base64.Encoder encoder= Base64.getEncoder();
    public static String encode(byte[] readAllBytes) {
        return encoder.encodeToString(readAllBytes);
    };
    public static byte[] decode(String data) {
        return decoder.decode(data);
    };
};
