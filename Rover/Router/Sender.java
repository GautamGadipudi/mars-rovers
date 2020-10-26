package Rover.Router;

import Rover.Router.RIP.RIPPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.TimerTask;

/**
 * @author gautamgadipudi
 *
 * Sender class
 *
 * Thread class that iterates over routing table, creates RIP packet and
 * multicasts it.
 */
public class Sender extends TimerTask implements Runnable {
    MulticastSocket socket;
    Router router;

    public Sender(Router router) {
        this.router = router;
        this.socket = router.multicastSocket;
    }

    /**
     * Create a datagram packet from routing table.
     */
    public DatagramPacket createDatagramPacket() {
        RIPPacket ripPacket = new RIPPacket(this.router.id, this.router.routingTable);

        byte[] packet = ripPacket.createRIPPacketData();
        DatagramPacket datagramPacket = null;
        try {
            datagramPacket = new DatagramPacket(packet, packet.length, InetAddress.getByName(this.router.routerConfig.MulticastIP), this.router.routerConfig.getPortNumber());
        } catch (UnknownHostException e) {
            System.out.println("Unable to create datagram packet!");
            e.printStackTrace();
        }

        return datagramPacket;
    }

    @Override
    public void run() {
        DatagramPacket packet = this.createDatagramPacket();
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Unable to send RIP packet!");
            e.printStackTrace();
        }
    }
}
