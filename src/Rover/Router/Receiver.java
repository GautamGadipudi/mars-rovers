package Rover.Router;

import Rover.Router.RIP.RIPEntry;
import Rover.Router.RIP.RIPPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.HashMap;

public class Receiver extends Thread {
    Router router;
    MulticastSocket socket;

    public Receiver(Router Router) {
        this.router = Router;
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

        RIPPacket ripPacket = new RIPPacket(data);
        HashMap<Byte, RIPEntry> ripEntries = ripPacket.getRipEntries();

        boolean change = false;
        for (byte key : ripEntries.keySet()) {
            RIPEntry ripEntry = ripEntries.get(key);

            byte cost = ripEntry.getCost();
            if (ripEntries.get(key).getNextHop() == this.router.id) {
                cost = (byte)16;
            }

            if (this.router.routingTable.entries.containsKey(ripEntry.getDestination())) {
                RoutingTableEntry routingTableEntry = this.router.routingTable.entries.get(ripEntry.getDestination());
                if (routingTableEntry.cost == 0) {
                    continue;
                }
                else {
                    if (routingTableEntry.cost > cost + 1) {
                        routingTableEntry.cost = (byte) (cost + 1);
                        this.router.routingTable.entries.put(routingTableEntry.routerId, routingTableEntry);
                        change = true;
                    }
                    else {
                        if (routingTableEntry.nextHop == ripEntry.getNextHop() && cost == 16) {
                            routingTableEntry.cost = 16;
                            this.router.routingTable.entries.put(routingTableEntry.routerId, routingTableEntry);
                        }
                        else if (routingTableEntry.nextHop == ripEntry.getNextHop() && cost != 16){
                            routingTableEntry.cost = (byte) (cost + 1);
                            this.router.routingTable.entries.put(routingTableEntry.routerId, routingTableEntry);
                        }
                    }
                }


            }
            else {
                this.router.routingTable.entries.put(ripEntry.getDestination(), new RoutingTableEntry(ripEntry.getDestination(), ripEntry.getNextHop(), ipAddress, ripEntry.getCost()));
                change = true;
            }
        }

        if (change) {
            this.router.printRouterTable();
            triggerUpdate();
        }
    }

    public void triggerUpdate(){
        new Thread(new Sender(this.router)).start();
    }
}
