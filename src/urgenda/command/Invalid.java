package urgenda.command;

import urgenda.command.Command;
import urgenda.logic.LogicData;

public class Invalid implements Command {
	
	private static final String MESSAGE_INVALID_COMMAND = "\"%1$s\" is not a valid command";
	
	private String _command;
	
	public Invalid() {
		
	}
	
	public Invalid(String command) {
		_command = command;
	}

	@Override
	public String execute(LogicData data) {
		data.setCurrState(LogicData.DisplayState.INVALID_COMMAND);
		return String.format(MESSAGE_INVALID_COMMAND, _command);
	}
	
	public void setCommand(String command) {
		_command = command;
	}

}
