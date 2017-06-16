package ch.bfh.ti.project1.RPiHSM.Pc;

import gnu.io.*;

public class Connection {

    static SerialPort serialPort;


    public static SerialPort connect(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(Client.class.getName(), 2000); //wait for port open

            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
        return serialPort;
    }


}
