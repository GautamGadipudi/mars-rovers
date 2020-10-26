import Rover.Rover;

/**
 * @author gautamgadipudi
 *
 * Main class / entrypoint.
 */
public class Main {
    public static void main(String[] args) {
        byte roverId = Byte.parseByte(args[0]);

        Rover rover = new Rover(roverId);

        rover.start();
    }
}
