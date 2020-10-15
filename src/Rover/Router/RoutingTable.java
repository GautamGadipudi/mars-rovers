package Rover.Router;

import java.util.Arrays;
import java.util.List;

public class RoutingTable {
    List<RoutingTableEntry> Entries;

    public RoutingTable(RouterConfig routerConfig) {
        Entries = Arrays.asList(new RoutingTableEntry(routerConfig));
    }

    public int getSize() {
        return this.Entries.size();
    }

    public List<RoutingTableEntry> getEntries() {
        return Entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address\tNext Hop\tCost\n");
        sb.append("===============================================\n");
        for(RoutingTableEntry entry : Entries) {
            sb.append(entry + "\n");
        }

        return sb.toString();
    }
}
