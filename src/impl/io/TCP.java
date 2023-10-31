package impl.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class TCP {
    public static class Server {
        private ServerSocket ss;
        public String ipAddr= "127.0.0.1";
        public int port= 8080;
        private ArrayList<Socket> clients= new ArrayList();
        public Server(int port) {
            this.port= port;
            try {
                this.ss= new ServerSocket(port);
                this.ss.setReuseAddress(true);
            } catch(IOException ex) {};
        };
        public TCP.Client __accept() {
            try {
                Socket client= this.ss.accept();
                this.clients.add(client);
                return new TCP.Client(this.ipAddr, this.port, client);
            } catch (IOException ex) {
                ex.printStackTrace();
            };
            return null;
        };
    };
    public static class Client {
        static byte[] reqD= new byte[65536];
        static Logger log= new Logger();
        InputStream iStream;
        OutputStream oStream;
        public String ipAddress;
        public int port;
        public Socket sock;
        public Client(String ipAddress, int port, Socket sock) {
            this.ipAddress= ipAddress;
            this.port= port;
            this.sock= sock;
        };
        public Client(String ipAddress, int port) {
            this.ipAddress= ipAddress;
            this.port= port;
            try {
                this.sock= new Socket(ipAddress, port);
            } catch (IOException ex) {
                ex.printStackTrace();
            };
        };
        public byte[] call(byte[] data) {
            try {
                this.iStream= this.sock.getInputStream();
                this.oStream= this.sock.getOutputStream();
                this.oStream.write(data);
                int dsSize= 65536;
                DataInputStream dataInputStream= new DataInputStream(new BufferedInputStream(this.sock.getInputStream()));
                byte[] dsData= new byte[dsSize];
                int size= dataInputStream.read(dsData);
                System.out.println(size);
                return dsData;
            } catch (IOException ex) {
                log.error("Could not establish connection, to process an inet-onSend.  ");
                ex.printStackTrace();
            };
            return null;
        };
        public void read(ServerBuff buff) {   /**
         Provides input for the server, all the client needs to do is read the packet and taking in the packet-buffer to write to.
         **/
            try {
                DataInputStream dataInputStream= new DataInputStream(new BufferedInputStream(this.sock.getInputStream()));
                int size= dataInputStream.read(reqD);
                buff.request= ByteBuffer.allocate(size).put(reqD, 0, size).position(0).order(ByteOrder.LITTLE_ENDIAN);
                buff.request.position(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            };
        };
        public void write(ClientBuff buff) {
            try {
                OutputStream oStream= this.sock.getOutputStream();
                oStream.write(buff.request.array());
            } catch(IOException ex) {};
        };
    };
};
