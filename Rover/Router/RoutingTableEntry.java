package Rover.Router;

/**
 * @author gautamgadipudi
 *
 * This class contains information of an entry in the routing table.
 *
 */
public class RoutingTableEntry {
    byte routerId;
    String address;
    byte nextHop;
    String nextHopIP;
    byte cost;

    /**
     * Initialize a routing table entry.
     *
     * @param routerId Router Id
     * @param nextHop Next Hop Router Id
     * @param nextHopIP Next Hop Router IP
     * @param cost Cost to get to the next hop
     */
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

    public void setNextHop(byte nextHop) {
        this.nextHop = nextHop;
    }

    public byte getNextHop() {
        return nextHop;
    }

    public byte getCost() {
        return cost;
    }

    /**
     * Used to print routing table
     *
     * @return
     */
    @Override
    public String toString() {
        return this.address + "\t | " +
                this.nextHopIP + "(" + this.nextHop + ")\t | " +
                this.cost;
    }
}
