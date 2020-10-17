package Rover.Router.RIP;

import Rover.Router.RouterConfig;
import Rover.Router.RoutingTableEntry;

public class RIPEntry {
    byte[] AddressFamilyIdentifier;
    RoutingTableEntry RoutingTableEntry;

    public RIPEntry(RoutingTableEntry routingTableEntry) {
        this.AddressFamilyIdentifier = new byte[]{0, 2};
        this.RoutingTableEntry = routingTableEntry;
    }

    public RIPEntry(byte[] data, String senderIP) {
        int i = 0;

        this.AddressFamilyIdentifier[0] = data[i++];
        this.AddressFamilyIdentifier[1] = data[i++];

        // Must be zero
        i += 2;

        byte destination = data[i++];
        byte nextHop = data[i++];

        // Must be zero
        i += 2;

        // Subnet mask
        i += 4;

        // Must be zero
        i += 4;

        byte cost = data[i];

        this.RoutingTableEntry = new RoutingTableEntry(destination, nextHop, senderIP, cost);
    }

    /**
     *
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *  | address family identifier (2) | must be zero (2)              |
     *  +-------------------------------+-------------------------------+
     *  | dst router Id (1) | next hop router id (1) | must be zero (2) |
     *  +---------------------------------------------------------------+
     *  | subnet mask (4)                                               |
     *  +---------------------------------------------------------------+
     *  | must be zero (4)                                              |
     *  +---------------------------------------------------------------+
     *  | metric (1) | must be zero (3)                                 |
     *  +---------------------------------------------------------------+
     * @return
     */
    public byte[] createRIPEntryData() {
        byte[] data = new byte[20];

        int i = 0;
        // address family identifier
        data[i++] = this.AddressFamilyIdentifier[0];
        data[i++] = this.AddressFamilyIdentifier[1];

        // must be zero
        data[i++] = 0;
        data[i++] = 0;

        // destination router id
        data[i++] = this.RoutingTableEntry.getRouterId();

        // next hop
        data[i++] = this.RoutingTableEntry.getNextHop();

        // must be zero
        i += 2;

        //subnet mask
        data[i++] = (byte)255;
        data[i++] = (byte)255;
        i += 2;

        // must be zero
        i += 4;

        data[i++] = this.RoutingTableEntry.getCost();

        return data;
    }
}
