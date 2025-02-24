package Rover.Router;

import Rover.Router.RIP.RIPEntry;
import Rover.Router.RIP.RIPPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gautamgadipudi
 *
 * Receiver class.
 *
 * Thread class that recieves RIP packets, processes them and updates routing
 * table accordingly.
 */
public class Receiver extends Thread {
    Router router;
    MulticastSocket socket;

    ConcurrentHashMap<Byte, Long> neighborTimestamps = new ConcurrentHashMap<>();
    ConcurrentHashMap<Byte, RoutingTableEntry> deletedEntries = new ConcurrentHashMap<>();

    public Receiver(Router router) {
        this.router = router;
        this.socket = router.multicastSocket;
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
            checkOffline();
            System.out.println(this.neighborTimestamps);
            processPacket(packet);
        }
    }

    /**
     * Process any offline routers and delete them.
     */
    public void checkOffline() {
        boolean changed = false;

        for (byte key : this.neighborTimestamps.keySet()) {
            long timeOfflineInSeconds = (System.nanoTime() - this.neighborTimestamps.get(key)) / 1000000000;

            if (timeOfflineInSeconds > 10) {
                boolean isDeleted = deleteEntry(key);

                if (isDeleted) {
                    changed = true;
                    neighborTimestamps.remove(key);
                    System.out.println("DELETED router #" + key + " from routing table.");
                }
            }
        }

        if (changed) {
            this.router.printRouterTable();
            triggerUpdate();
        }
    }

    /**
     * Delete a routing table entry of given id from routing table.
     *
     * @param routerId
     *
     * @return isDeleted Confirmation of deletion.
     */
    public boolean deleteEntry(byte routerId) {
        try {
            RoutingTableEntry entry = this.router.routingTable.entries.get(routerId);
            this.deletedEntries.put(routerId, entry);
            this.router.routingTable.entries.remove(routerId);

            for (byte key : this.router.routingTable.entries.keySet()) {
                RoutingTableEntry rte = this.router.routingTable.entries.get(key);
                if (rte.nextHop == routerId) {
                    rte.cost = (byte) 16;
                    this.router.routingTable.entries.put(rte.routerId, rte);
                }
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Process the packet received from other or same router.
     *
     * @param packet
     */
    public void processPacket(DatagramPacket packet) {
        byte[] data = packet.getData();
        String ipAddress = packet.getAddress().toString();

        RIPPacket ripPacket = new RIPPacket(data);
        byte neighborId = ripPacket.getRouterId();
        HashMap<Byte, RIPEntry> ripEntries = ripPacket.getRipEntries();

        if (neighborId == this.router.id)
            return;

        this.neighborTimestamps.put(ripPacket.getRouterId(), System.nanoTime());

        boolean change = false;
        for (byte key : ripEntries.keySet()) {
            RIPEntry ripEntry = ripEntries.get(key);

            byte sentCost = ripEntry.getCost();
            if (ripEntry.getNextHop() == this.router.id) {
                sentCost = (byte)16;
            }

            // If routing table has an entry for destination
            if (this.router.routingTable.entries.containsKey(ripEntry.getDestination())) {
                RoutingTableEntry routingTableEntry = this.router.routingTable.entries.get(ripEntry.getDestination());

                // Routing table entry for same router
                if (routingTableEntry.cost == 0) {
                    continue;
                }
                // Routing table entry of different destination router
                else {

                    // If better cost in reaching destination found
                    if (routingTableEntry.cost > sentCost + 1) {
                        routingTableEntry.setNextHop(ripEntry.getNextHop());
                        routingTableEntry.cost = (byte) (sentCost + 1);
                        this.router.routingTable.entries.put(routingTableEntry.routerId, routingTableEntry);
                        change = true;
                    }
                    // If better cost in reaching destination NOT found
                    else {
                        if (sentCost == 16) {
                            routingTableEntry.cost = 16;
                            this.router.routingTable.entries.put(routingTableEntry.routerId, routingTableEntry);
                            change = true;
                        }
                    }
                }
            }
            // If routing table DOES NOT have an entry for destination
            else {
                this.router.routingTable.entries.put(ripEntry.getDestination(), new RoutingTableEntry(ripEntry.getDestination(), ripEntry.getNextHop(), ipAddress, (byte)(sentCost + 1)));
                change = true;
            }
        }

        // If any change, print router table and trigger and update.
        if (change) {
            this.router.printRouterTable();
            triggerUpdate();
        }
    }

    /**
     * Create a new Sender thread.
     */
    public void triggerUpdate(){
        new Thread(new Sender(this.router)).start();
    }
}
