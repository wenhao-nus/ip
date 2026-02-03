package aeolian;

/**
 * Represents an exception specific to the Aeolian chatbot.
 */
public class AeolianException extends Exception {
    /**
     * Constructs an AeolianException with the specified error message.
     *
     * @param message The error message.
     */
    public AeolianException(String message) {
        super(message);
    }
}
