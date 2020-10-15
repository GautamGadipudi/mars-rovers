package Classes;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.StandardSocketOptions;

public class Rover {

    RoverConfig roverConfig;
    MulticastSocket multicastSocket;

    public Rover(byte roverId) {
        this.roverConfig = new RoverConfig(roverId);
        try {
            this.multicastSocket = new MulticastSocket(roverConfig.getPortNumber());
            multicastSocket.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false);
            multicastSocket.joinGroup(multicastSocket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Unable to create multicast socket!");
            e.printStackTrace();
        }
    }

}
