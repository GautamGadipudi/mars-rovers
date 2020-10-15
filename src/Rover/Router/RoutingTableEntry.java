package Rover.Router;

/**
 * This class contains information of an entry in the routing table.
 *
 */
public class RoutingTableEntry {
    RouterConfig RouterConfig;
    byte NextHopRouterId;
    String NextHopRouterIPAddress;
    byte Cost;

    public RoutingTableEntry(RouterConfig routerConfig) {
        this.RouterConfig = routerConfig;
        this.NextHopRouterId = routerConfig.getRoverId();
        this.NextHopRouterIPAddress = "127.0.0.1";
        this.Cost = 0;
    }

    @Override
    public String toString() {
        return this.RouterConfig.getAddress() + "\t" +
                this.NextHopRouterIPAddress + "(" + this.NextHopRouterId + ")\t" +
                this.Cost;
    }
}
