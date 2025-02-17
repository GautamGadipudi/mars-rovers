package Rover.Router.RIP;

import Rover.Router.RoutingTable;
import Rover.Router.RoutingTableEntry;

import java.util.HashMap;

/**
 * @author gautamgadipudi
 *
 * RIP Packet class
 */
public class RIPPacket {
    byte command;
    byte version;
    byte ripEntriesCount;
    byte routerId;

    HashMap<Byte, RIPEntry> ripEntries;

    /**
     * This constructor is used by receiver to convert byte array data to
     * RIPPacket class.
     *
     * @param data RIP Packet data
     */
    public RIPPacket(byte[] data) {
        this.command = data[0];
        this.version = data[1];
        this.routerId = data[2];
        this.ripEntriesCount = data[3];

        this.ripEntries = new HashMap<>();


        for (int i = 0; i < this.ripEntriesCount; i++) {
            int j = i * 20 + 4;
            byte[] ripEntryData = new byte[20];
            System.arraycopy(data, j, ripEntryData, 0, 20);
            this.ripEntries.put(ripEntryData[4], new RIPEntry(ripEntryData));
        }
    }

    /**
     * This constructor is used by sender to create an RIP Packet object from
     * routing table.
     *
     * @param routerId Router Id
     * @param routingTable Routing Table
     */
    public RIPPacket(byte routerId, RoutingTable routingTable) {
        HashMap<Byte, RoutingTableEntry> routingTableEntries = routingTable.getEntries();

        this.command = 2;
        this.version = 2;
        this.routerId = routerId;
        this.ripEntriesCount = (byte)routingTable.getSize();

        this.ripEntries = new HashMap<>();
        for (byte key : routingTableEntries.keySet()) {
            RoutingTableEntry routingTableEntry = routingTableEntries.get(key);
            ripEntries.put(key, new RIPEntry(routingTableEntry.getRouterId(), routingTableEntry.getNextHop(), routingTableEntry.getCost()));
        }
    }

    /**
     *  Create an RIP packet as a byte array as below:
     *
     *        0                                                                     4 bytes
     *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *       |  command (1)  |  version (1)  |  router id (1)   | rip entries count (1) |
     *       +---------------+---------------+------------------+-----------------------+
     *       |                                                                          |
     *       ~                         RIP Entry (20)                                   ~
     *       |                                                                          |
     *       +---------------+---------------+---------------+--------------------------+
     */
    public byte[] createRIPPacketData() {
        byte[] buff = new byte[512];

        buff[0] = this.command;
        buff[1] = this.version;
        buff[2] = this.routerId;
        buff[3] = this.ripEntriesCount;

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

    public byte getRouterId() {
        return routerId;
    }

    /**
     * Used just to debug. Has no significance otherwise.
     *
     * @return Stringified RIP Packet
     */
    @Override
    public String toString() {
        StringBuilder ripEntriesString = new StringBuilder();

        for (byte key : this.ripEntries.keySet()) {
            ripEntriesString.append(ripEntries.get(key));
        }
        return "RIPPacket{" +
                "command=" + command +
                ", version=" + version +
                ", ripEntriesCount=" + ripEntriesCount +
                ", routerId=" + routerId +
                ", ripEntries=" + ripEntriesString.toString() +
                '}';
    }
}
