package test;

import impl.io.ServerBuff;
import impl.io.TCP;

public class TestServer extends ServerBuff {
    public static void main(String[] av) {
        TCP.Server server= new TCP.Server(true, 25600);
        while(server.running) {
            TCP.Client client= server.__accept();
            log.info("Server accepted client.  ");
            TestServer buff= new TestServer(client);
            client.read(buff);   //   Reads data from the packet into a buffer.
            buff.onCall(client.ipAddress, client.port);
            client.write(buff);   //   Sends the data back to the client, by writing the response into the packet.
        };
    };
    public TestServer(TCP.Client client) { super(client); };
    @Override public void onCall(String ipAddr, int port) {   //   This method should read data from the client, and process that data.  The buffer the server reads from is separate from the buffer the server writes to.
        int a= readReqInt();
        int b= readReqInt();
        log.info(String.format("a= %s;", a));
        log.info(String.format("b= %s;", b));
        response.position(0);
        writeResInt(a + b);
        int reqPos= request.position();
        int resPos= response.position();
        response.position(0);
        log.info(String.valueOf(response.getInt()));
        request.position(reqPos);
        response.position(resPos);
    };
};
