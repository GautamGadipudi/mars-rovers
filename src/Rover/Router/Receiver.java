package Rover.Router;

import Rover.Router.RIP.RIPPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Receiver implements Runnable {
    Router Router;
    MulticastSocket socket;

    public Receiver(Router Router) {
        this.Router = Router;
        this.socket = Router.multicastSocket;
    }

    @Override
    public void run() {
        while (true) {
            byte[] buff = new byte[512];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.out.println("Unable to receive datagram packet!");
                e.printStackTrace();
            }
        }
    }

    public void ProcessPacket(DatagramPacket packet) {
        byte[] data = packet.getData();
        String ipAddress = packet.getAddress().toString();

        RIPPacket ripPacket = new RIPPacket(data, ipAddress);

        // TO-DO: FURTHER PROCESSING
    }
}
