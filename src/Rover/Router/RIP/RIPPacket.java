package Rover.Router.RIP;

import Rover.Router.RouterConfig;
import Rover.Router.RoutingTable;
import Rover.Router.RoutingTableEntry;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class RIPPacket {
    byte Command;
    byte Version;
    byte RouterId;

    List<RIPEntry> RIPEntries;

    public RIPPacket(RouterConfig routerConfig, RoutingTable routingTable) {
        this.Command = 2;
        this.Version = 2;

        this.RouterId = routerConfig.getRoverId();
        this.RIPEntries = new ArrayList<>();

        List<RoutingTableEntry> routingTableEntries = routingTable.getEntries();
        for (RoutingTableEntry routingTableEntry : routingTableEntries) {
            this.RIPEntries.add(new RIPEntry(routingTableEntry));
        }
    }

    public DatagramPacket getRIPPacket() {
        byte[] buff = new byte[512];

        buff[0] = this.Command;
        buff[1] = this.Version;
        buff[3] = this.RouterId;
        buff[4] = 0;

        for (int i=5; i < this.RIPEntries.size() + 5; i++) {
            // To-do: CREATE RIP and append to buffer
        }
    }
}
