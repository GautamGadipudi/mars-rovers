package Rover.Router;

import Rover.Router.RIP.RIPPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.StandardSocketOptions;

public class Router {
    byte id;
    RouterConfig routerConfig;
    MulticastSocket multicastSocket;
    RoutingTable routingTable;

    public Router(byte roverId) {
        this.id = roverId;
        this.routerConfig = new RouterConfig(roverId);

        try {
            this.multicastSocket = new MulticastSocket(routerConfig.getPortNumber());
            multicastSocket.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false);
            multicastSocket.joinGroup(multicastSocket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Unable to create multicast socket!");
            e.printStackTrace();
        }

        this.routingTable = new RoutingTable(roverId);
    }

    public void printRouterTable() {
        System.out.println(this.routingTable);
    }

    public DatagramPacket createDatagramPacket() {
        byte[] packet = new RIPPacket(this.id, this.routingTable).createRIPPacketData();
        return new DatagramPacket(packet, packet.length, multicastSocket.getInetAddress(), routerConfig.getPortNumber());
    }
}
