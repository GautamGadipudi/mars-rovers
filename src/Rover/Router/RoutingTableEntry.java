package Rover.Router;

/**
 * This class contains information of an entry in the routing table.
 *
 */
public class RoutingTableEntry {
    byte RouterId;
    String Address;
    byte NextHop;
    String NextHopIP;
    byte Cost;

    public RoutingTableEntry(RouterConfig routerConfig) {
        this.RouterId = routerConfig.getRoverId();
        this.Address = routerConfig.getAddress();
        this.NextHop = routerConfig.getRoverId();
        this.NextHopIP = "127.0.0.1";
        this.Cost = 0;
    }

    public RoutingTableEntry(byte routerId, byte nextHop, String nextHopIP, byte cost) {
        this.RouterId = routerId;
        this.Address = "10.0." + routerId + ".0";
        this.NextHop = nextHop;
        this.NextHopIP = nextHopIP;
        this.Cost = cost;
    }

    public byte getRouterId() {
        return RouterId;
    }

    public String getNextHopIP() {
        return NextHopIP;
    }

    public byte getNextHop() {
        return NextHop;
    }

    public byte getCost() {
        return Cost;
    }

    @Override
    public String toString() {
        return this.Address + "\t" +
                this.NextHopIP + "(" + this.NextHop + ")\t" +
                this.Cost;
    }
}
