package Classes;

/**
 * This class contains information of an entry in the routing table.
 *
 */
public class RoutingTableEntry {
    byte RouterId;
    String Address;
    byte NextHopRouterId;
    String NextHopRouterIPAddress;
    byte Cost;

    public RoutingTableEntry(byte routerId, String address, String nextHopRouterIPAddress) {
        this.RouterId = routerId;
        this.Address = address;
        this.NextHopRouterId = routerId;
        this.NextHopRouterIPAddress = nextHopRouterIPAddress;
    }

    @Override
    public String toString() {
        return this.Address + "\t" +
                this.NextHopRouterIPAddress + "(" + this.NextHopRouterId + ")\t" +
                this.Cost;
    }
}
