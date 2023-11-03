package impl.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

public class Logger {   //   Provides the logging-capability for the server.
    private static enum MessageType {
        none,
        DEBUG,INFO,WARN,ERROR,CRITICAL,
    };
    public static class impl {
        private static FileOutputStream fos;
        static {
            try {
                fos= new FileOutputStream(Paths.get("../server.log").toFile(), true);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            };
        };
        public static void append(MessageType type, String object, int line, boolean printToScreen, String message) {
            StringBuilder sb= new StringBuilder();
            sb.append(new Date());
            sb.append("   ");
            sb.append(System.nanoTime());
            int i00= sb.length() +1;
            switch(type) {
                case DEBUG:
                    sb.append(" [debug] ");
                    break;
                case INFO:
                    sb.append(" [info] ");
                    break;
                case WARN:
                    sb.append(" [warn] ");
                    break;
                case ERROR:
                    sb.append(" [error] ");
                    break;
                case CRITICAL:
                    sb.append(" [critical] ");
                    break;
                default:
            };
            sb.append(object);
            sb.append(" @");
            sb.append(line);
            sb.append(":   ");
            sb.append(message.replaceAll("\n","\n\t"));
            if(printToScreen)System.out.println(sb.toString().substring(i00));
            sb.append('\n');
            try {
                impl.fos.write(sb.toString().getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(-1);
            };
        };
    };
    public static void __append(MessageType type, int jumpForward, boolean printToScreen, String message) {
        var ste= Thread.currentThread().getStackTrace()[3];
        impl.append(type, ste.getClassName(), ste.getLineNumber(), printToScreen, message);
    };
    public void info(String info) { __append(MessageType.INFO, 0, true, info); };
    public void debug(String info) { __append(MessageType.DEBUG, 0, true, info); };
    public void warn(String info) { __append(MessageType.WARN, 0, true, info); };
    public void error(String info) { __append(MessageType.ERROR, 0, true, info); };
    public void critical(String info) { __append(MessageType.CRITICAL, 0, true, info); };
    public void quietInfo(String info) { __append(MessageType.INFO, 0, false, info); };
    public void quietDebug(String info) { __append(MessageType.DEBUG, 0, false, info); };
    public void quietWarn(String info) { __append(MessageType.WARN, 0, false, info); };
    public void quietError(String info) { __append(MessageType.ERROR, 0, false, info); };
    public void quietCritical(String info) { __append(MessageType.CRITICAL, 0, false, info); };
};
