package urgenda.parser.commandParser;

import urgenda.command.*;
import urgenda.parser.DateTimeParser;
import urgenda.parser.PublicVariables;
import urgenda.parser.PublicVariables.*;
import urgenda.util.Task;
import urgenda.parser.TaskDetailsParser;

public class AddCommandParser {
	private static String _argsString;
	private static int _index;
	
	public AddCommandParser(String argsString, int index) {
		_argsString = argsString;
		_index = index;
	}
	
	public static Command generateAndReturn() {
		if (_argsString == null) {
			return new Invalid();
		} else {
			String reducedArgsString = DateTimeParser.searchTaskTimes(_argsString);
			reducedArgsString = TaskDetailsParser.searchTaskHashtags(reducedArgsString);
			reducedArgsString = TaskDetailsParser.searchTaskLocation(reducedArgsString);
			TaskDetailsParser.searchTaskDescription(reducedArgsString);
			TaskDetailsParser.searchTaskType();
			return generateAddCommandAndReturn();
		}
	}
	
	private static Command generateAddCommandAndReturn() {
		if (PublicVariables.commandType == COMMAND_TYPE.INVALID) {
			return new Invalid();
		} else {
			Task newTask = new Task();
			if (PublicVariables.taskIndex != -10) {
				newTask.setDesc(String.valueOf(PublicVariables.taskIndex + 1));
			}
			if (PublicVariables.taskDescription != "") {
				newTask.setDesc(PublicVariables.taskDescription);
			}
			if (PublicVariables.taskLocation != "") {
				newTask.setLocation(PublicVariables.taskLocation);
			}
			if (PublicVariables.taskStartTime != null) {
				newTask.setStartTime(PublicVariables.taskStartTime);
			}
			if (PublicVariables.taskEndTime != null) {
				newTask.setEndTime(PublicVariables.taskEndTime);
			}
			if (!PublicVariables.taskHashtags.isEmpty()) {
				newTask.setHashtags(PublicVariables.taskHashtags);
			}
			if (PublicVariables.taskSlots != null) {
				newTask.setSlot(PublicVariables.taskSlots);
			}
			switch (PublicVariables.taskType) {
			case EVENT:
				newTask.setTaskType(Task.Type.EVENT);
				break;
			case DEADLINE:
				newTask.setTaskType(Task.Type.DEADLINE);
				break;
			case FLOATING:
				newTask.setTaskType(Task.Type.FLOATING);
				break;
			default:
				return new Invalid();
			}
			return new AddTask(newTask);
		}
	}
}

