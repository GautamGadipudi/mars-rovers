package Rover.Router.RIP;

public class RIPEntry {
    byte[] addressFamilyIdentifier;

    byte destination;
    byte nextHop;
    byte cost;

    public RIPEntry(byte[] data) {
        int i = 0;

        this.addressFamilyIdentifier[0] = data[i++];
        this.addressFamilyIdentifier[1] = data[i++];

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

    public RIPEntry(byte destination, byte nextHop, byte cost) {
        this.addressFamilyIdentifier = new byte[]{0, 2};
        this.destination = destination;
        this.nextHop = nextHop;
        this.cost = cost;
    }

    /**
     *
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *  | address family identifier (2) | must be zero (2)              |
     *  +-------------------------------+-------------------------------+
     *  | dst router Id (1) | next hop router id (1) | must be zero (2) |
     *  +---------------------------------------------------------------+
     *  | subnet mask (4)                                               |
     *  +---------------------------------------------------------------+
     *  | must be zero (4)                                              |
     *  +---------------------------------------------------------------+
     *  | metric (1) | must be zero (3)                                 |
     *  +---------------------------------------------------------------+
     * @return
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
}
