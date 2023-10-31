package game.impl;

public abstract class Packet implements packet_t {
    protected PacketInfo info;
    int clientId;
    byte[] data;   //   Packets all have a field for data in this server implementation.  
};
