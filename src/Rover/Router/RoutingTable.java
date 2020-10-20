package Rover.Router;

import java.util.HashMap;

public class RoutingTable {
    HashMap<Byte, RoutingTableEntry> entries;

    public RoutingTable(byte routerId) {
        this.entries = new HashMap<>();
        this.entries.put(routerId, new RoutingTableEntry(routerId, routerId, "localhost", (byte)0));
    }

    public int getSize() {
        return this.entries.size();
    }

    public HashMap<Byte, RoutingTableEntry> getEntries() {
        return this.entries;
    }

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
