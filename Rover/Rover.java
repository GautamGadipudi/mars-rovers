package Rover;

import Rover.Router.Receiver;
import Rover.Router.Router;
import Rover.Router.Sender;

import java.util.Timer;

/**
 * @author gautamgadipudi
 *
 * Rover class
 */
public class Rover extends Thread{
    int RoverId;
    Router Router;

    public Rover(byte roverId) {
        this.RoverId = roverId;
        this.Router = new Router(roverId);
    }

    @Override
    public void run() {
        this.Router.printRouterTable();

        Timer timer = new Timer();
        timer.schedule(new Sender(this.Router), 1000, 5000);

        Receiver receiver = new Receiver(this.Router);
        receiver.start();
    }
}
