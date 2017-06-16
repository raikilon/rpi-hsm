package ch.bfh.ti.project1.RPiHSM.IoT;

import ch.bfh.ti.project1.RPiHSM.IoT.Commands.SerialHelperI;
import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.io.serial.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * <h1>SerialHelper</h1>
 * Helper class that implement all {@link SerialHelperI} functions for the serial port.
 */
public final class SerialHelper implements SerialHelperI {

    private Serial serialPort;
    private InputStream in;
    private OutputStream out;
    private GpioController gpio; //GPIO controller
    private GpioPinDigitalOutput red; //red led
    /**
     * Creates a console object and tries to connect to the serial port.
     * If a error occurs the program is terminated.
     */
    public SerialHelper() {
        gpio = GpioFactory.getInstance();
        red = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "Red", PinState.LOW);
        connect();
    }


    /**
     * Detects the port that is connected with the serial cable and
     * it establishes the connection with a speed of 115200 bits per seconds.
     * If some errors occur the application is closed.
     */
    private void connect() {
        serialPort = SerialFactory.createInstance();

        try {
            // create serial config object
            SerialConfig config = new SerialConfig();

            config.device(SerialPort.getDefaultPort())
                    .baud(Baud._115200)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);

            serialPort.open(config);
            in = serialPort.getInputStream();
            out = serialPort.getOutputStream();
        } catch (IOException | UnsupportedBoardType | InterruptedException ex) {
            red.high();
            System.exit(-1); // the led stays turned on
        }
    }

    @Override
    /**
     * Sends a code 200 over the serial port.
     * This message means all has been correctly executed.
     */
    public void ready() {
        writeLine(Constants.READY);
    }

    @Override
    /**
     * Sends a code 600 over the serial port.
     * The message means an error is occurred.
     */
    public void error() {
        red.blink(500,5000);//blink red for 10 s every 0.5 s
        writeLine(Constants.ERROR);
    }

    @Override
    /**
     * Sends a file over the serial port.
     * Uses the {@link SerialHelper} {@link SerialHelper#writeLine()} and {@link SerialHelper#write()} methods.
     */
    public void sendFile(byte[] data) {
        writeLine(Integer.toString(data.length));
        write(data);
    }

    @Override
    /**
     * Sends a file over the serial port.
     * Uses the {@link SerialHelper} writeLine and write methods.
     * If an error occurs the {@link SerialHelper#error()} method is used.
     */
    public void sendFile(String path) {

        byte[] fileBytes = new byte[0];

        try {
            fileBytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            error();
        }

        writeLine(Integer.toString(fileBytes.length));

        write(fileBytes);
    }

    @Override
    /**
     * Reads a file from the serial port.
     * If an error occurs the {@link SerialHelper#error()} method is used.
     */
    public byte[] readFile() {

        String input = readLine();

        int length = Integer.parseInt(input);

        byte[] temp = new byte[length];

        try {

            for (int i = 0; i < length; i++) {

                temp[i] = (byte) in.read();

            }

        } catch (IOException e) {
            error();
        }

        return temp;

    }

    @Override
    /**
     * Writes a string to the serial port.
     * If an error occurs the {@link SerialHelper#error()} method is used.
     *(Precondition: message &#033; &#061; &quot;&quot; &amp;&amp; message &#033;&#061; null)
     */
    public void writeLine(String message) {
        assert message != null && !message.equals("");
        write(new String(message + "\n").getBytes());
    }

    /**
     * Write the given byte array in the serial console
     * If an error occurs the application is closed.
     */
    private void write(byte[] bytes) {
        try {
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            System.exit(-1); // abort this must not be possible
        }
    }


    @Override
    /**
     * Read a string from the serial port.
     * Wait until something is written in the serial port.
     * It reads until it find the eol character.
     * If an error occurs the {@link SerialHelper#error()} method is used.
     */
    public String readLine() {

        int tempByte = 0;

        String tempRead = "";

        while (true) { //until eol

            try {
                tempByte = in.read();
            } catch (IOException e) {
                error();
            }

            if (tempByte == -1) continue; //InputStream receive strange characters

            if (tempByte == (int) '\n') break; //end of string

            tempRead += (char) tempByte;
        }
        return tempRead;
    }
}