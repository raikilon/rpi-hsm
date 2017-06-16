package ch.bfh.ti.project1.RPiHSM.IoT;

import com.pi4j.io.serial.*;
import com.pi4j.util.Console;

import java.io.FileOutputStream;
import java.io.IOException;

public class Server {


    public static void main(String args[]) throws InterruptedException, IOException {
        final Console console = new Console();
        console.title("RPiHSM - Project", "Test", "Berner Fachhochschule");
        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

        // create and register the serial data listener
        /*serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {

                // NOTE! - It is extremely important to read the data received from the
                // serial port.  If it does not get read from the receive buffer, the
                // buffer will continue to grow and consume memory.

                // print out the data received to the console
                try {
                    console.println("[HEX DATA]   " + event.getHexByteString());
                    console.println("[ASCII DATA] " + event.getAsciiString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

        try {
            // create serial config object
            SerialConfig config = new SerialConfig();
            config.device(SerialPort.getDefaultPort())
                    .baud(Baud._115200)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);

            // display connection details
            console.box(" Connecting to: " + config.toString());


            // open the default serial device/port with the configuration settings
            serial.open(config);

            // continuous loop to keep the program running until the user terminates the program
            while (console.isRunning()) {
                try {

                    String code = new String(serial.read(7)); //wait for starting code (<<200>>)
                    if (code.equals("<<200>>")) {

                        serial.write("<<200>>".getBytes());
                        String input = "";

                        while (true) {
                            String temp = new String(serial.read(1));
                            if (temp.equals("<")) break;
                            input += temp;
                        }

                        Long lenght = Long.parseLong(input);
                        console.box("" + lenght);
                        try (FileOutputStream fileOuputStream = new FileOutputStream("test.jpg")) {
                            for (long i = 0; i < lenght; i++) {
                                fileOuputStream.write(serial.read(1));
                            }
                            serial.write("<<200>>\n");
                        }
                    } else {
                        serial.write("<<500>>");
                    }
                } catch (IllegalStateException ex) {
                    ex.printStackTrace();
                }

                // wait 1 second before continuing
                Thread.sleep(1000);
            }

        } catch (IOException ex) {
            console.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }
    }
}