package Rover.Router.RIP;

import java.util.Arrays;

/**
 * @author gautamgadipudi
 *
 * RIP Packet Entry class
 */
public class RIPEntry {
    byte[] addressFamilyIdentifier;

    byte destination;
    byte nextHop;
    byte cost;

    /**
     * This constructor is used by the receiver to convert RIP Entry data into
     * its class.
     *
     * @param data RIP entry data of 20 bytes
     */
    public RIPEntry(byte[] data) {
        int i = 0;

        this.addressFamilyIdentifier = new byte[] {data[i++], data[i++]};

        // Must be zero
        i += 2;

        this.destination = data[i++];
        this.nextHop = data[i++];

        // Must be zero
        i += 2;

        // Subnet mask
        i += 4;

        // Must be zero
        i += 4;

        this.cost = data[i];
    }

    /**
     * This class is used by the sender to convert routing table entry to
     * RIPEntry class.
     *
     * @param destination Destination
     * @param nextHop Next Hop
     * @param cost Cost/Metric to get to the destination
     */
    public RIPEntry(byte destination, byte nextHop, byte cost) {
        this.addressFamilyIdentifier = new byte[]{0, 2};
        this.destination = destination;
        this.nextHop = nextHop;
        this.cost = cost;
    }

    /**
     *  Create an RIP packet entry as a byte array as below:
     *
     *          0                                                           4 bytes
     *          +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *          | address family identifier (2) | must be zero (2)              |
     *          +-------------------------------+-------------------------------+
     *          | dst router Id (1) | next hop router id (1) | must be zero (2) |
     *          +-------------------+------------------------+------------------+
     *          | subnet mask (4)                                               |
     *          +---------------------------------------------------------------+
     *          | must be zero (4)                                              |
     *          +---------------------------------------------------------------+
     *          | cost (1) | must be zero (3)                                   |
     *          +----------+----------------------------------------------------+
     */
    public byte[] createRIPEntryData() {
        byte[] data = new byte[20];

        int i = 0;
        // address family identifier
        data[i++] = this.addressFamilyIdentifier[0];
        data[i++] = this.addressFamilyIdentifier[1];

        // must be zero
        data[i++] = 0;
        data[i++] = 0;

        // destination router id
        data[i++] = this.destination;

        // next hop
        data[i++] = this.nextHop;

        // must be zero
        i += 2;

        //subnet mask
        data[i++] = (byte)255;
        data[i++] = (byte)255;
        i += 2;

        // must be zero
        i += 4;

        data[i++] = this.cost;

        return data;
    }

    public byte getCost() {
        return cost;
    }

    public byte getDestination() {
        return destination;
    }

    public byte getNextHop() {
        return nextHop;
    }

    /**
     * Used just to debug. Has no significance otherwise.
     *
     * @return Stringified RIP Packet Entry.
     */
    @Override
    public String toString() {
        return "RIPEntry{" +
                "addressFamilyIdentifier=" + Arrays.toString(addressFamilyIdentifier) +
                ", destination=" + destination +
                ", nextHop=" + nextHop +
                ", cost=" + cost +
                '}';
    }
}
