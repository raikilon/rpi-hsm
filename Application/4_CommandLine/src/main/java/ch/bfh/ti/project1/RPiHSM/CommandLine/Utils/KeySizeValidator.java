package ch.bfh.ti.project1.RPiHSM.CommandLine.Utils;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * <h1>KeySetSizeValidator</h1>
 * A JCommander validator that check if the given key size has a valid value.
 *
 * @see <a href="http://jcommander.org/#_parameter_validation">JCommander parameter validation</a>
 */
public class KeySizeValidator implements IParameterValidator {

    /**
     * Validates the given value.
     *
     * @param name  The name of the parameter {@link Constants#SIZE_COMPLETE_VALUE}.
     * @param value The value of the parameter that needs to be validated.
     * @throws ParameterException Thrown if the value of the parameter is invalid.
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (Integer.parseInt(value) < 0 && Integer.parseInt(value) % 2 != 0) { //the key must be even and grater than 0
            throw new ParameterException(ResourceBundle.getBundle("language", Locale.getDefault()).getString("error.illegal.argument"));
        }
    }
}
