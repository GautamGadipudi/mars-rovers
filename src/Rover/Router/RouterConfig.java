package Rover.Router;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class RouterConfig {

    String MulticastIP = "224.0.0.9";
    int PortNumber = 1337;

    List<String> AddressTemplate = Arrays.asList("10", "0", "-1", "0");

    byte RoverId;
    String Address;

    public RouterConfig(byte roverId) {
        this.RoverId = roverId;
        this.Address = getAddress();
    }

    public int getPortNumber() {
        return PortNumber;
    }

    public String getMulticastIP() {
        return MulticastIP;
    }

    public byte getRoverId() {
        return RoverId;
    }

    public String getAddress () {
        Collections.replaceAll(AddressTemplate, "-1", Byte.toString(this.RoverId));

        return String.join(".", AddressTemplate);
    }
}
