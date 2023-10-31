package test;

import impl.io.PacketBuff;
import impl.io.TCP;

public class TestServer extends PacketBuff {
    public static void main(String[] av) {
        TCP.Server server= new TCP.Server(25600);
        TCP.Client client= server.__accept();
        TestServer buff= new TestServer(client);
        client.read(buff);   //   This is where the server being broken becomes obvious, the question is how is it broken and how can the problem be resolved.  it forces the client's connection to be reset immediately on connection, causing problems.  
    };
    public TestServer(TCP.Client client) { super(client); };
    @Override public void onCall(String ipAddr, int port) {
        log.info(String.format("%s;", readReqLong()));
    };
};
