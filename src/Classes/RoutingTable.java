package Classes;

import java.util.ArrayList;

public class RoutingTable {
    ArrayList<RoutingTableEntry> Entries;

    public RoutingTable() {
        this.Entries = new ArrayList<>();
    }

    public int getSize() {
        return this.Entries.size();
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
