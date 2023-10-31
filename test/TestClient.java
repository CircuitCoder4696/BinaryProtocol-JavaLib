package test;

import impl.io.PacketBuff;
import impl.io.TCP;

public class TestClient extends PacketBuff {
    public static void main(String[] av) {
        TCP.Client client= new TCP.Client("localhost",25600);
        TestClient buff= new TestClient(client);
        buff.request.putInt(450000);
        buff.request.putFloat(3.75f);
        client.read(buff);   //   The client crashes because the server forces it to crash due to the server being broken in the way that it is.
    };
    public TestClient(TCP.Client client) { super(client); };
    @Override public void onCall(String ipAddr, int port) {
        log.info("Listen for request on the server-side.  ");
    };
};
