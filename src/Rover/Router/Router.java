package Rover.Router;

import Rover.Router.RIP.RIPPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.StandardSocketOptions;

public class Router {

    RouterConfig routerConfig;
    MulticastSocket multicastSocket;
    RoutingTable routingTable;
    Sender sender;

    public Router(byte roverId) {
        this.routerConfig = new RouterConfig(roverId);

        try {
            this.multicastSocket = new MulticastSocket(routerConfig.getPortNumber());
            multicastSocket.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false);
            multicastSocket.joinGroup(multicastSocket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Unable to create multicast socket!");
            e.printStackTrace();
        }

        this.routingTable = new RoutingTable(routerConfig);
    }

    public DatagramPacket getDatagramPacket() {
        byte[] packet = new RIPPacket(this.routerConfig, this.routingTable).createRIPPacketData();
        return new DatagramPacket(packet, packet.length, multicastSocket.getInetAddress(), routerConfig.getPortNumber());
    }
}
