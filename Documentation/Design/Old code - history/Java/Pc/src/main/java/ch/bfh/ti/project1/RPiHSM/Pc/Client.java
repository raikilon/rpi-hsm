package ch.bfh.ti.project1.RPiHSM.Pc;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {

    static SerialPort serialPort;
    static InputStream in;
    static OutputStream out;
    static byte buffer[];


    public static void main(String[] args) throws IOException, InterruptedException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        init();
        sendOk();

        Thread.sleep(2000);

        if (checkOk()) {
            sendFile("C:\\Users\\tiago\\Desktop\\17884382_10154760825614051_6732660880293310110_n.jpg");

            System.out.println("Risposta: " + readLine());
        } else {
            System.err.println("Error 500 - ACK not received2!");
        }
        serialPort.close();
        System.exit(0);
    }

    public final static String readLine() throws IOException {
        //if(!isActive) throw new OperationNotSupportedException("Serial connection not active!");
        int tempByte = Integer.MIN_VALUE;
        String tempRead = "";
        while (tempByte != (int) '\n') {
            tempByte = in.read();
            tempRead += (char) tempByte;
        }
        return tempRead;
    }


    private static void sendFile(String path) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Paths.get(path));
        out.write((fileBytes.length + "<").getBytes());
        out.write(fileBytes);
    }

    private static void init() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
        serialPort = Connection.connect("COM3");
        buffer = new byte[1024];
        in = serialPort.getInputStream();
        out = serialPort.getOutputStream();

        //(new Thread(new SerialReader(in))).start();
        //(new Thread(new SerialWriter(out))).start();
    }


    private static boolean checkOk() throws IOException {
        in.read(buffer, 0, 7);
        System.out.println((new String(buffer)));
        return (new String(buffer)).contains("<<200>>");
    }


    private static void sendOk() throws IOException {
        out.write("<<200>>".getBytes());
    }


    private static void sendError() throws IOException {
        out.write("<<500>>".getBytes());
    }
}