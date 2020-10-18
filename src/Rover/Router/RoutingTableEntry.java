package Rover.Router;

/**
 * This class contains information of an entry in the routing table.
 *
 */
public class RoutingTableEntry {
    byte routerId;
    String address;
    byte nextHop;
    String nextHopIP;
    byte cost;

    public RoutingTableEntry(RouterConfig routerConfig) {
        this.routerId = routerConfig.getRouterId();
        this.address = routerConfig.getAddress();
        this.nextHop = routerConfig.getRouterId();
        this.nextHopIP = "127.0.0.1";
        this.cost = 0;
    }

    public RoutingTableEntry(byte routerId, byte nextHop, String nextHopIP, byte cost) {
        this.routerId = routerId;
        this.address = "10.0." + routerId + ".0";
        this.nextHop = nextHop;
        this.nextHopIP = nextHopIP;
        this.cost = cost;
    }

    public byte getRouterId() {
        return routerId;
    }

    public String getNextHopIP() {
        return nextHopIP;
    }

    public byte getNextHop() {
        return nextHop;
    }

    public byte getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return this.address + "\t" +
                this.nextHopIP + "(" + this.nextHop + ")\t" +
                this.cost;
    }
}
