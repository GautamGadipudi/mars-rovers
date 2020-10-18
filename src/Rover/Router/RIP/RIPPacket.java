package Rover.Router.RIP;

import Rover.Router.RoutingTable;
import Rover.Router.RoutingTableEntry;

import java.util.HashMap;

public class RIPPacket {
    byte command;
    byte version;
    byte routerId;

    HashMap<Byte, RIPEntry> ripEntries;

    public RIPPacket(byte[] data) {
        this.command = data[0];
        this.version = data[1];
        this.routerId = data[2];

        this.ripEntries = new HashMap<>();

        int entryCount = (data.length - 4) / 20;

        for (int i = 1; i <= entryCount; i++) {
            int j = i * 20 + 4;
            byte[] ripEntryData = new byte[20];
            System.arraycopy(data, j, ripEntryData, 0, 20);
            this.ripEntries.put(this.routerId, new RIPEntry(ripEntryData));
        }
    }

    public RIPPacket(byte routerId, RoutingTable routingTable) {
        HashMap<Byte, RoutingTableEntry> routingTableEntries = routingTable.getEntries();
        this.command = 1;
        this.version = 2;
        this.routerId = routerId;

        this.ripEntries = new HashMap<>();
        for (byte key : routingTableEntries.keySet()) {
            RoutingTableEntry routingTableEntry = routingTableEntries.get(key);
            ripEntries.put(key, new RIPEntry(routingTableEntry.getRouterId(), routingTableEntry.getNextHop(), routingTableEntry.getCost()));
        }
    }

    public byte[] createRIPPacketData() {
        byte[] buff = new byte[512];

        buff[0] = this.command;
        buff[1] = this.version;
        buff[2] = this.routerId;
        buff[3] = 0;

        int i = 0;
        for (byte key : this.ripEntries.keySet()) {
            byte[] ripEntryData = this.ripEntries.get(key).createRIPEntryData();
            System.arraycopy(ripEntryData, 0, buff, i * ripEntryData.length + 4, ripEntryData.length);
            i++;
        }

        return buff;
    }

    public HashMap<Byte, RIPEntry> getRipEntries() {
        return ripEntries;
    }
}
