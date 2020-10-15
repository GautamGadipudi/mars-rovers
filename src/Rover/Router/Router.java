package Rover.Router;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.StandardSocketOptions;

public class Router {

    RouterConfig routerConfig;
    MulticastSocket multicastSocket;
    RoutingTable routingTable;

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

    public byte getId() {
        return routerConfig.getRoverId();
    }

    public String getAddress() {
        return routerConfig.getAddress();
    }

}
