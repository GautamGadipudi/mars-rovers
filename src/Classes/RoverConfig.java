package Classes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class RoverConfig {

    String MulticastIP = "224.0.0.9";
    int PortNumber = 1337;

    List<String> AddressTemplate = Arrays.asList("10", "0", "-1", "0");

    byte RoverId;
    String Address;

    public RoverConfig(byte roverId) {
        this.RoverId = roverId;
        this.Address = getAddress(roverId);
    }

    public int getPortNumber() {
        return PortNumber;
    }

    public String getMulticastIP() {
        return MulticastIP;
    }

    public String getAddress (byte roverId) {
        Collections.replaceAll(AddressTemplate, "-1", Byte.toString(roverId));

        return String.join(".", AddressTemplate);
    }
}
