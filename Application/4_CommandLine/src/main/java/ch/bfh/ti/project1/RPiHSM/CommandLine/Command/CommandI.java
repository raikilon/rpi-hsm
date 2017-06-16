package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

/**
 * <h1>CommandI</h1>
 * Interface to use the Command pattern.
 */
public interface CommandI {
    /**
     * Executes the Command specific instructions.
     *
     * @return a message for the user
     */
    String execute();

    /**
     * Returns the right syntax for the command.
     *
     * @return the right command syntax.
     */
    String print();
}
