package ch.bfh.ti.project1.RPiHSM.IoT;


import ch.bfh.ti.project1.RPiHSM.IoT.Commands.*;
import ch.bfh.ti.project1.RPiHSM.IoT.MockObjects.MockSerialHelper;
import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests if the {@link CommandFactory}ory return the right {@link CommandI} implementation.
 */
public class CommandFactoryTest {

    @Test
    /**
     * Tests if a {@link CreateKeySet} object is returned when the {@link Constants#CREATEKEYSET}command is given.
     */
    public void testCreateKeySet() {
        String[] commands = {Constants.CREATEKEYSET, "userPath", "name", "purpose", "algorithm"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof CreateKeySet);
    }

    @Test
    /**
     * Tests if a {@link DeleteKeySet} object is returned when the {@link Constants#DELETE} command is given.
     */
    public void testDeleteKeySet() {
        String[] commands = {Constants.DELETE, "userPath", "name", "purpose", "algorithm"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof DeleteKeySet);
    }

    @Test
    /**
     * Tests if a {@link CreateKey} object is returned when the {@link Constants#CREATEKEY} command is given.
     */
    public void testCreateKey() {
        String[] commands = {Constants.CREATEKEY, "userPath", "name", "status", "0"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof CreateKey);
    }

    @Test
    /**
     * Tests if a {@link Login} object is returned when the {@link Constants#LOGIN} command is given.
     */
    public void testLogin() {
        String[] commands = {Constants.LOGIN, "username", "password"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof Login);
    }

    @Test
    /**
     * Tests if a {@link KeyExistence} object is returned when the {@link Constants#CHECK} command is given.
     */
    public void testKeyExistence() {
        String[] commands = {Constants.CHECK, "userPath", "name"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof KeyExistence);
    }


    @Test
    /**
     * Tests if a {@link Decrypt} object is returned when the {@link Constants#DECRYPT} command is given.
     */
    public void testDecrypt() {
        String[] commands = {Constants.DECRYPT, "userPath", "name"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof Decrypt);
    }


    @Test
    /**
     * Tests if a {@link Encrypt} object is returned when the {@link Constants#ENCRYPT} command is given.
     */
    public void testEncrypt() {
        String[] commands = {Constants.ENCRYPT, "userPath", "name"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof Encrypt);
    }

    @Test
    /**
     * Tests if a {@link Sign} object is returned when the {@link Constants#SIGN} command is given.
     */
    public void testSign() {
        String[] commands = {Constants.SIGN, "userPath", "name"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof Sign);
    }


    @Test
    /**
     * Tests if a {@link Verify} object is returned when the {@link Constants#VERIFY} command is given.
     */
    public void testVerify() {
        String[] commands = {Constants.VERIFY, "userPath", "name"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof Verify);
    }


    @Test
    /**
     * Tests if a {@link RevokeKey} object is returned when the {@link Constants#REVOKE} command is given.
     */
    public void testRevokeKey() {
        String[] commands = {Constants.REVOKE, "userPath", "name", "1"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof RevokeKey);
    }


    @Test
    /**
     * Tests if a {@link DemoteKey} object is returned when the{@link Constants#DEMOTE} command is given.
     */
    public void testDemoteKey() {
        String[] commands = {Constants.DEMOTE, "userPath", "name", "1"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof DemoteKey);
    }


    @Test
    /**
     * Tests if a {@link PromoteKey} object is returned when the {@link Constants#PROMOTE} command is given.
     */
    public void testPromoteKey() {
        String[] commands = {Constants.PROMOTE, "userPath", "name", "1"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof PromoteKey);
    }


    @Test
    /**
     * Tests if a {@link PublicKey} object is returned when the {@link Constants#PUBLICKEY} command is given.
     */
    public void testPubKey() {
        String[] commands = {Constants.PUBLICKEY, "userPath", "name"};
        CommandFactory commandFactory = new CommandFactory();
        CommandI command = commandFactory.getCommand(commands, new MockSerialHelper());
        assertTrue(command instanceof PublicKey);
    }

}
