package Rover.Router;

import java.util.HashMap;

/**
 * @author gautamgadipudi
 *
 * This class represents the Routing table for a router.
 */
public class RoutingTable {

    /**
     * Hash map with key as router id and value as the routing table entry.
     */
    HashMap<Byte, RoutingTableEntry> entries;

    /**
     * Initialize routing table using router Id.
     *
     * @param routerId Router Id
     */
    public RoutingTable(byte routerId) {
        this.entries = new HashMap<>();
        this.entries.put(routerId, new RoutingTableEntry(routerId, routerId, "localhost", (byte)0));
    }

    /**
     * Get size of routing table.
     */
    public int getSize() {
        return this.entries.size();
    }

    public HashMap<Byte, RoutingTableEntry> getEntries() {
        return this.entries;
    }

    /**
     * Used to print routing table.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address\t | Next Hop\t | Cost\n");
        sb.append("===============================================\n");
        for(byte key : entries.keySet()) {
            sb.append(entries.get(key) + "\n");
        }

        return sb.toString();
    }
}
