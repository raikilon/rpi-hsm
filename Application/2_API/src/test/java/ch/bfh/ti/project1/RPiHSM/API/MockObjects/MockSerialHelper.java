package ch.bfh.ti.project1.RPiHSM.API.MockObjects;

import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelperI;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

/**
 * Mock object for test purpose ( the real {@link SerialHelper} can not be test because it need a valid serial connection).
 */
public class MockSerialHelper implements SerialHelperI {

    private boolean operationNotSupported; //know if an exception must be thrown.
    private boolean fileNotFound; //know if an exception must be thrown.

    private int times; // after how many times false must be returned.
    private int count = 0; // how many times the method has been called

    /**
     * Sets the parameters.
     *
     * @param operationNotSupported true if an OperationNotSupportedException must be thrown
     * @param fileNotFound          true if an FileNotFoundException must be thrown
     * @param times                 after how many times a false must be returned.
     */
    public MockSerialHelper(boolean operationNotSupported, boolean fileNotFound, int times) {
        this.operationNotSupported = operationNotSupported;
        this.fileNotFound = fileNotFound;
        this.times = times;
    }

    @Override
    /**
     * Return a fake status.
     * @return always true except when the method is called {@link MockSerialHelper#times} times.
     * @throws OperationNotSupportedException if the {@link MockSerialHelper#operationNotSupported} is true.
     */
    public boolean status() throws OperationNotSupportedException {
        if (operationNotSupported)
            throw new OperationNotSupportedException();

        if (count == times) {
            return false;
        } else {
            count++;
            return true;
        }
    }

    @Override
    /**
     * Do nothing except when {@link MockSerialHelper#operationNotSupported} or {@link MockSerialHelper#fileNotFound} is true.
     * @throws OperationNotSupportedException if the {@link MockSerialHelper#operationNotSupported} is true.
     * @throws FileNotFoundException if the {@link MockSerialHelper#fileNotFound} is true.
     */
    public void sendFile(String path) throws FileNotFoundException, OperationNotSupportedException {
        if (operationNotSupported)
            throw new OperationNotSupportedException();


        if (fileNotFound)
            throw new FileNotFoundException();
    }

    @Override
    /**
     * Do nothing except when {@link MockSerialHelper#operationNotSupported} or {@link MockSerialHelper#fileNotFound} is true.
     * @throws OperationNotSupportedException if the {@link MockSerialHelper#operationNotSupported} is true.
     * @throws FileNotFoundException if the {@link MockSerialHelper#fileNotFound} is true.
     */
    public void readFile(String path) throws FileNotFoundException, OperationNotSupportedException {
        if (operationNotSupported)
            throw new OperationNotSupportedException();


        if (fileNotFound)
            throw new FileNotFoundException();
    }

    @Override
    /**
     * Do nothing.
     */
    public void closeConnection() {
    }


    @Override
    /**
     * Do nothing except when {@link MockSerialHelper#operationNotSupported} is true.
     * @throws OperationNotSupportedException if the {@link MockSerialHelper#operationNotSupported} is true.
     */
    public void writeLine(String message) throws OperationNotSupportedException {
        if (operationNotSupported)
            throw new OperationNotSupportedException();
    }

    @Override
    /**
     * Do nothing except when {@link MockSerialHelper#operationNotSupported} is true.
     * @return a predefined string.
     * @throws OperationNotSupportedException if the {@link MockSerialHelper#operationNotSupported} is true.
     */
    public String readLine() throws OperationNotSupportedException {
        if (operationNotSupported)
            throw new OperationNotSupportedException();
        return "1";
    }
}
