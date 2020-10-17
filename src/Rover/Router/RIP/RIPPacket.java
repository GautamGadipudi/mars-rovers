package Rover.Router.RIP;

import Rover.Router.RouterConfig;
import Rover.Router.RoutingTable;
import Rover.Router.RoutingTableEntry;

import java.util.ArrayList;
import java.util.List;

public class RIPPacket {
    byte Command;
    byte Version;
    byte RouterId;

    List<RIPEntry> RIPEntries;

    public RIPPacket(RouterConfig routerConfig, RoutingTable routingTable) {
        this.Command = 2;
        this.Version = 2;

        this.RouterId = routerConfig.getRoverId();
        this.RIPEntries = new ArrayList<>();

        List<RoutingTableEntry> routingTableEntries = routingTable.getEntries();
        for (RoutingTableEntry routingTableEntry : routingTableEntries) {
            this.RIPEntries.add(new RIPEntry(routingTableEntry));
        }
    }

    public RIPPacket(byte[] data, String senderIP) {
        this.Command = data[0];
        this.Version = data[1];
        this.RouterId = data[2];

        this.RIPEntries = new ArrayList<>();

        int entryCount = (data.length - 4) / 20;

        for (int i = 1; i <= entryCount; i++) {
            int j = i * 20 + 4;
            byte[] ripEntryData = new byte[20];
            System.arraycopy(data, j, ripEntryData, 0, 20);
            this.RIPEntries.add(new RIPEntry(ripEntryData, senderIP));
        }
    }

    public byte[] createRIPPacketData() {
        byte[] buff = new byte[512];

        buff[0] = this.Command;
        buff[1] = this.Version;
        buff[2] = this.RouterId;
        buff[3] = 0;

        for (int i = 4; i < this.RIPEntries.size() + 4; i++) {
            // To-do: CREATE RIP and append to buffer
            byte[] ripEntryData = this.RIPEntries.get(i).createRIPEntryData();
            System.arraycopy(ripEntryData, 0, buff, i * ripEntryData.length, ripEntryData.length);
        }

        return buff;
    }
}
