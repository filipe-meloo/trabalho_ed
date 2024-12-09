package Collections.Exceptions;

/**
 * This exception is thrown when a bot is missing a movement algorithm.
 *
 * @author Your Name
 */
public class MissingMovementAlgorithmException extends RuntimeException {

    /**
     * Constructs a new MissingMovementAlgorithmException with the given bot name.
     *
     * @param botName the name of the bot that is missing a movement algorithm
     */
    public MissingMovementAlgorithmException(String botName) {
        super("Bot " + botName + " does not have a movement algorithm.");
    }

}
