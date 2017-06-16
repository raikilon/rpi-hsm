package ch.bfh.ti.project1.RPiHSM.CommandLine.Utils;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <h1>StatusValidator</h1>
 * A JCommander validator that check if the given key status is valid.
 *
 * @see <a href="http://jcommander.org/#_parameter_validation">JCommander parameter validation</a>
 */
public final class StatusValidator implements IParameterValidator {

    /**
     * Validates the given value.
     *
     * @param name  The name of the parameter {@link Constants#STATUS_COMPLETE_VALUE} or {@link Constants#STATUS_SHORT_VALUE}.
     * @param value The value of the parameter that needs to be validated.
     * @throws ParameterException Thrown if the value of the parameter is invalid.
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!value.equals(Constants.PRIMARY) && !value.equals(Constants.ACTIVE) && !value.equals(Constants.INACTIVE))
            throw new ParameterException(ResourceBundle.getBundle("language", Locale.getDefault()).getString("error.illegal.argument"));
    }

}