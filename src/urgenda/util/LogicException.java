//@@author A0080436
package urgenda.util;

/**
 * LogicException is the Exception used in the Logic component in Urgenda for
 * exceptions that occur due to invalid tasks or commands on the current state
 * of Urgenda.
 * 
 */
public class LogicException extends Exception {

	/**
	 * System Generated serial number for LogicException.
	 */
	private static final long serialVersionUID = 1L;
	private String _message;

	/**
	 * Default constructor which contains the message for feedback to the user.
	 * 
	 * @param message
	 *            Error message generated by method.
	 */
	public LogicException(String message) {
		_message = message;
	}

	/**
	 * Retrieves the error message generated.
	 * 
	 * @return String of the error message.
	 */
	@Override
	public String getMessage() {
		return _message;
	}

}
