package test;

import impl.io.ClientBuff;
import impl.io.TCP;

public class TestClient extends ClientBuff {
    public static void main(String[] av) {
        TCP.Client client= new TCP.Client("localhost",25600);
        TestClient buff= new TestClient(client);
        buff.onSend(client);   //   Writes data to the buffer.
        client.write(buff);   //   Sends the buffer data in a packet.
        client.read(buff);   //   Recieves a packet and places the data into the buffer.
        buff.onRecv(client);   //   Reads data from the buffer.
    };
    public TestClient(TCP.Client client) { super(client); };
    @Override public void onSend(TCP.Client client) {
        writeReqInt(450000);
        writeReqInt(3750000);
    };
    @Override public void onRecv(TCP.Client client) {
        int result= readResInt();
        System.out.println(String.format("The server added both integers and returned the result %s.  ", result));
    };
};
