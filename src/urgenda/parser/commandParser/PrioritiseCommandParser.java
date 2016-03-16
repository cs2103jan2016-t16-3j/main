package urgenda.parser.commandParser;

import urgenda.command.*;
import urgenda.parser.PublicVariables;
import urgenda.parser.TaskDetailsParser;

public class PrioritiseCommandParser {
	private static String _argsString;
	private static int _index;
	
	public PrioritiseCommandParser(String argsString, int index) {
		_argsString = argsString;
		_index = index;
	}
	
	public static Command generateAndReturn() {
		if (_argsString.equals("")) {
			PublicVariables.taskIndex = _index;
		} else {
			TaskDetailsParser.searchTaskIndex(_argsString);
			TaskDetailsParser.searchTaskDescription(_argsString);
		}
		
		Prioritise prioritiseCommand = new Prioritise();
		if (PublicVariables.taskIndex != -10) {
			prioritiseCommand.setId(PublicVariables.taskIndex);
		} else if (PublicVariables.taskDescription.equals("")){
			prioritiseCommand.setId(_index);
		}
		
		if (PublicVariables.taskDescription.equals("")) {
			prioritiseCommand.setDesc(PublicVariables.taskDescription);
		}
		
		return prioritiseCommand;
	}
}
