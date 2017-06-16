package ch.bfh.ti.project1.RPiHSM.IoT;


import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CommandFactory;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CommandI;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.SerialHelperI;
import com.pi4j.io.gpio.*;

/**
 * <h1>IoT</h1>
 * HSM simulation on a Raspberry Pi 3.
 * The crypto functions are implemented with the help of <a href="https://github.com/google/keyczar"> Keyczar </a>.
 *
 * @author Noli Manzoni, Sandro Tiago Carlao
 * @version 0.1
 * @since 13.04.2017
 */
public class IoT {
    /**
     * The serial helper read the parameters (divided by a space character) and split them.
     * The first parameter is given to the {@link CommandFactory} that return a {@link CommandI} object.
     * The the relative command is executed.
     * (The serial helper is created here and it is moved in all the application
     * so that we must not use the Singleton design pattern).
     * @see <a href="https://www.michaelsafyan.com/tech/design/patterns/singleton"> Signleton Anti-Pattern </a>
     *
     * @param args no parameters are given to the application
     */
    public static void main(String[] args) {



        // create gpio controller
        GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        GpioPinDigitalOutput green = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Green", PinState.HIGH);
        GpioPinDigitalOutput yellow =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "Yellow", PinState.LOW);
        // set shutdown state for this pin
        green.setShutdownOptions(true, PinState.LOW);
        yellow.setShutdownOptions(true, PinState.LOW);

        SerialHelperI serialHelper = new SerialHelper(); //initialisation of the serial helper

        while (true) { // this loop is not a busy one -> it does not use resources. It waits (readLine) for a command and when it is executed it waits for another one.

            String commandRead = serialHelper.readLine(); //read the command from the command line (wait for  input)
            yellow.blink(500);//blink every 0.5 s
            String[] commands = commandRead.split(" ");

            //Create new convenient factory to generate a instance of ICommand
            CommandFactory cmFactory = new CommandFactory();

            //Ask for a command giving the cmd arg
            CommandI cm = cmFactory.getCommand(commands, serialHelper);

            //Execute command
            cm.execute();
            yellow.blink(0);//stop blink
            yellow.low(); // turn off
        }
    }
}
