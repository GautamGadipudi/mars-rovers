package Rover.Router;

import java.util.HashMap;
import java.util.Iterator;

public class RoutingTable {
    HashMap<Byte, RoutingTableEntry> entries;

    public RoutingTable(byte routerId) {
        entries = new HashMap<>();
        entries.put(routerId, new RoutingTableEntry(routerId, routerId, "localhost", (byte)0));
    }

    public int getSize() {
        return this.entries.size();
    }

    public HashMap<Byte, RoutingTableEntry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address\tNext Hop\tCost\n");
        sb.append("===============================================\n");
        for(byte key : entries.keySet()) {
            sb.append(entries.get(key) + "\n");
        }

        return sb.toString();
    }
}
