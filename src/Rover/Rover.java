package Rover;

import Rover.Router.Router;

public class Rover {
    int RoverId;
    Router Router;

    public Rover(byte roverId) {
        this.RoverId = roverId;
        this.Router = new Router(roverId);
    }
}
