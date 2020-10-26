package Rover.Router;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author gautamgadipudi
 *
 * This class has all the config details for the router.
 */
public final class RouterConfig {

    String MulticastIP = "224.0.0.9";
    int PortNumber = 1337;

    List<String> AddressTemplate = Arrays.asList("10", "0", "-1", "0");

    byte RouterId;
    String Address;

    public RouterConfig(byte routerId) {
        this.RouterId = routerId;
        this.Address = getAddress();
    }

    public int getPortNumber() {
        return PortNumber;
    }

    /**
     * Replace -1 in AddressTemplate with router id.
     */
    public String getAddress () {
        Collections.replaceAll(AddressTemplate, "-1", Byte.toString(this.RouterId));

        return String.join(".", AddressTemplate);
    }
}
