package Rover.Router;

import java.io.IOException;
import java.net.*;

/**
 * @author gautamgadipudi
 *
 * This class is the router class.
 */
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
            multicastSocket.joinGroup(InetAddress.getByName(routerConfig.MulticastIP));
        } catch (IOException e) {
            System.out.println("Unable to create multicast socket!");
            e.printStackTrace();
        }

        this.routingTable = new RoutingTable(roverId);
    }

    /**
     * Print routing table of router.
     */
    public void printRouterTable() {
        System.out.println(this.routingTable);
    }
}
