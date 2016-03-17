package urgenda.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import urgenda.parser.PublicVariables.COMMAND_TYPE;
import urgenda.parser.PublicVariables.TASK_TYPE;

public class TaskDetailsParser {
	public static String searchTaskLocation(String argsString) {
		String temp = argsString.trim();
		try {
			temp = temp.split("@")[1];
			PublicVariables.taskLocation = temp.trim();
			return argsString.replace("@" + temp.trim(), "").trim();
		} catch (Exception e) {
			try {
				if (temp.substring(0, 3).equals("at ")) {
					PublicVariables.taskLocation = temp.substring(3);
					return argsString.replace("at " + temp.substring(3), "");
				} else {
					temp = temp.split(" at ")[1];
					PublicVariables.taskLocation = temp.trim();
					return argsString.replace(" at " + temp.trim(), "");
				}
			} catch (Exception ex) {
				return argsString.trim();
			}
		}
	}

	public static String searchTaskHashtags(String argsString) {
		if (argsString != null) {
			String temp = argsString;
			Matcher matcher = Pattern.compile("#\\S+").matcher(temp);
			while (matcher.find()) {
				PublicVariables.taskHashtags.add(matcher.group());
				argsString = argsString.replace(matcher.group(), "");
			}
		}
		return argsString.trim();
	}

	public static void searchTaskDescription(String argsString) {
		if (argsString == null || argsString.equals("")) {
			PublicVariables.taskDescription = "";
		} else {
			PublicVariables.taskDescription = argsString.trim();
		}
	}

	public static void searchTaskType() {
		if (PublicVariables.taskStartTime == null && PublicVariables.taskEndTime == null) {
			PublicVariables.taskType = TASK_TYPE.FLOATING;
		} else if (PublicVariables.taskStartTime != null && PublicVariables.taskEndTime != null) {
			PublicVariables.taskType = TASK_TYPE.EVENT;
		} else if (PublicVariables.taskStartTime == null && PublicVariables.taskEndTime != null) {
			PublicVariables.taskType = TASK_TYPE.DEADLINE;
		} else {
			PublicVariables.taskType = TASK_TYPE.INVALID;
		}
	}

	public static String searchTaskIndex(String argsString) {
		try {
			String firstWord = PublicFunctions.getFirstWord(argsString);
			String restOfString = PublicFunctions.removeFirstWord(argsString);
			PublicVariables.taskIndex = Integer.parseInt(firstWord) - 1;
			return restOfString;
		} catch (Exception e) {
			return argsString;
		}
	}

}
