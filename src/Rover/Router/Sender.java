package Rover.Router;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.TimerTask;

public class Sender extends TimerTask implements Runnable {
    MulticastSocket socket;
    Router Router;

    public Sender(Router router) {
        this.Router = router;
        this.socket = router.multicastSocket;
    }
    @Override
    public void run() {
        DatagramPacket packet = this.Router.createDatagramPacket();
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Unable to send RIP packet!");
            e.printStackTrace();
        }
    }
}
