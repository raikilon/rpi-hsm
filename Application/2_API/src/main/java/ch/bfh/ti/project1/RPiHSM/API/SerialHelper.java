package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.Exception.SerialPortException;
import com.jamierf.rxtx.RXTXLoader;
import gnu.io.*;

import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <h1>SerialHelper</h1>
 * Helper class that implement all {@link SerialHelperI} functions for the serial port.
 */
public final class SerialHelper implements SerialHelperI {

    private SerialPort serialPort;
    private InputStream in;
    private OutputStream out;

    /**
     * Tries to connect to the serial port.
     *
     * @throws PortInUseException                when the port is already in use
     * @throws UnsupportedCommOperationException when there is any serial port connected
     * @throws SerialPortException               when problems occur with the serial port
     */
    public SerialHelper() throws PortInUseException, UnsupportedCommOperationException, SerialPortException {

        try {
            //  System.setProperty("gnu.io.rxtx.NoVersionOutput","true"); for the future version of the loader (rxtx 2.2pre2 or greater) this will hide the load message
            RXTXLoader.load();
        } catch (IOException e) {
            throw new SerialPortException();
        }

        CommPortIdentifier portIdentifier = (CommPortIdentifier) CommPortIdentifier.getPortIdentifiers().nextElement();

        try {
            CommPort commPort = portIdentifier.open(Constants.NAME, Constants.TIME_TO_WAIT);//new owner and time to wait
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;

                serialPort.setSerialPortParams(Constants.CONNECTION_SPEED, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            } else {
                throw new SerialPortException();
            }
        }catch (UnsatisfiedLinkError e){
            throw new SerialPortException();
        }
        try {
            in = serialPort.getInputStream();
            out = serialPort.getOutputStream();
        } catch (IOException e) {
            throw new SerialPortException();
        }
    }


    @Override
    /**
     * Reads a string from the serial port.
     * @return true if the read string contains the code {@link Constants#SUCCESS}.
     * @throws OperationNotSupportedException if an error occurs.
     */
    public boolean status() throws OperationNotSupportedException {
        return readLine().contains(Constants.SUCCESS);
    }


    @Override
    /**
     * Sends a file over the serial port.
     * Uses the {@link SerialHelper} writeLine and write methods.
     * @throws FileNotFoundException if the given file path do not exist.
     * @throws OperationNotSupportedException if an error occurs.
     */
    public void sendFile(String path) throws FileNotFoundException, OperationNotSupportedException {
        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        writeLine(Integer.toString(fileBytes.length));

        write(fileBytes);
    }


    @Override
    /**
     * Reads a file from the serial port.
     * @throws FileNotFoundException if the given file path do not exist.
     * @throws OperationNotSupportedException if an error occurs.
     */
    public void readFile(String path) throws FileNotFoundException, OperationNotSupportedException {

        String input = readLine();
        Long length = Long.parseLong(input);
        int temp;
        try (FileOutputStream fileOutputStream = new FileOutputStream(path, false)) {
            for (long i = 0; i < length; i++) {
                temp = in.read();
                fileOutputStream.write(temp);
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

    }


    @Override
    /**
     * Close the serial port connection.
     * @throws SerialPortException if an error occurs in the closing operation.
     */
    public void closeConnection() throws SerialPortException {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            throw new SerialPortException();
        }
        serialPort.close();
    }


    @Override
    /**
     * Writes a string to the serial port.
     * @throws OperationNotSupportedException if an error occurs.
     *(Precondition: message &#033; &#061; &quot;&quot; &amp;&amp; message &#033;&#061; null)
     */
    public void writeLine(String message) throws OperationNotSupportedException {
        assert message != null && !message.equals("");
        write(new String(message + "\n").getBytes());
    }

    /**
     * Write the given byte array in the serial console
     *
     * @throws OperationNotSupportedException if an error occurs.
     */
    private void write(byte[] bytes) throws OperationNotSupportedException {
        try {
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            throw new OperationNotSupportedException();
        }
    }


    @Override
    /**
     * Reads a string from the serial port.
     * Wait until something is written in the serial port.
     * It reads until it find the eol character.
     * If an error occurs an {@link OperationNotSupportedException} is thrown.
     */
    public String readLine() throws OperationNotSupportedException {
        int tempByte;
        String tempRead = "";
        while (true) {
            try {
                tempByte = in.read();
            } catch (IOException e) {
                throw new OperationNotSupportedException();
            }

            if (tempByte == -1) continue; //InputStream receive strange characters
            if (tempByte == (int) '\n') break; //end of string
            tempRead += (char) tempByte;
        }
        return tempRead;
    }


}