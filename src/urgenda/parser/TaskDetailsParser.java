//@@author A0127764X
package urgenda.parser;

import urgenda.parser.PublicVariables.TASK_TYPE;

public class TaskDetailsParser {
	private static String locationKeyWord1 = "@";
	private static String locationKeyWord2 = "at";
	private static String emptyString = "";
	private static String whiteSpace = " ";
	private static String rangeRegex = "((\\d+)( )?-( )?(\\d+))";
	private static String numberRegex = "(\\d+)";
	private static String indexDelimiterRegex = ",";
	private static String indexRangeDelimiterRegex = "-";

	/**
	 * function that search for a location in the passed in string and update
	 * the global variable taskLocation in PublicVariables after that the
	 * substring that contains the location is removed from the passed in
	 * string, and the passed in string in returned
	 * 
	 * @param argsString
	 *            passed in string
	 * @return passed in string with location string removed
	 */
	public static String searchTaskLocation(String argsString) {
		try {
			return tryParseLocationByFirstKeyWord(argsString);
		} catch (Exception e) {
			return tryParseLocationBySecondKeyWord(argsString);
		}
	}

	/**
	 * function that search for a description in the passed in string and update
	 * the global variable taskDescription in PublicVariables
	 * 
	 * @param argsString
	 *            passed in string
	 */
	public static void searchTaskDescription(String argsString) {
		if (isEmptyDesc(argsString)) {
			PublicVariables.taskDescription = emptyString;
		} else {
			PublicVariables.taskDescription = argsString.trim();
		}
	}

	/**
	 * function that determines the task type from the global variables
	 * taskStartTime and taskEndTime in PublicVariable the global variable
	 * taskType is set accordingly
	 */
	public static void searchTaskType() {
		if (noTaskStartTime() && noTaskEndTime()) {
			PublicVariables.taskType = TASK_TYPE.FLOATING;
		} else if (!noTaskStartTime() && !noTaskEndTime()) {
			PublicVariables.taskType = TASK_TYPE.EVENT;
		} else if (noTaskStartTime() && !noTaskEndTime()) {
			PublicVariables.taskType = TASK_TYPE.DEADLINE;
		} else {
			PublicVariables.taskType = TASK_TYPE.INVALID;
		}
	}

	/**
	 * function that searches for task index in the parsed in string and update
	 * the global variable taskIndex
	 * 
	 * @param argsString
	 *            passed in string
	 * @return the passed in string with the parsed index removed
	 */
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

	/**
	 * function that searches for task indexes in the parsed in string and
	 * update the global variable positions
	 * 
	 * @param argsString
	 *            passed in string
	 * @return passed in string with the parsed index removed
	 */
	public static String searchTaskIndexRange(String argsString) {
		String[] indexRanges = argsString.split(indexDelimiterRegex);

		for (int i = 0; i < indexRanges.length; i++) {
			try {
				if (isIndexRange(indexRanges, i)) {
					parseIndexRange(indexRanges, i);
				} else if (isSingleIndex(indexRanges, i)) {
					parseSingleIndex(indexRanges, i);
				} else {
					return argsString;
				}
			} catch (Exception e) {
				return argsString;
			}
		}
		return null;
	}

	private static void parseSingleIndex(String[] indexRanges, int i) {
		int Index = Integer.parseInt(indexRanges[i].trim()) - 1;
		if (isNotRepeatedIndex(Index)) {
			PublicVariables.positions.add(Index);
		}
	}

	private static void parseIndexRange(String[] indexRanges, int i) {
		int index1 = Integer.parseInt(indexRanges[i].split(indexRangeDelimiterRegex)[0].trim());
		int index2 = Integer.parseInt(indexRanges[i].split(indexRangeDelimiterRegex)[1].trim());
		for (int j = Integer.min(index1, index2); j <= Integer.max(index1, index2); j++) {
			if (isNotRepeatedIndex(j - 1)) {
				PublicVariables.positions.add(j - 1);
			}
		}
	}

	private static boolean isSingleIndex(String[] indexRanges, int i) {
		return indexRanges[i].trim().matches(numberRegex);
	}

	private static boolean isIndexRange(String[] indexRanges, int i) {
		return indexRanges[i].trim().matches(rangeRegex);
	}

	private static String tryParseLocationByFirstKeyWord(String argsString) {
		String temp = argsString.trim();
		temp = temp.split(locationKeyWord1)[1];
		PublicVariables.taskLocation = temp.trim();
		return argsString.replace(locationKeyWord1 + temp.trim(), emptyString).trim();
	}

	private static String tryParseLocationBySecondKeyWord(String argsString) {
		String temp = argsString.trim();
		try {
			if (temp.substring(0, 3).equals(locationKeyWord2 + whiteSpace)) {
				PublicVariables.taskLocation = temp.substring(3);
				return argsString.replace(locationKeyWord2 + whiteSpace + temp.substring(3), emptyString);
			} else {
				temp = temp.split(whiteSpace + locationKeyWord2 + whiteSpace)[1];
				PublicVariables.taskLocation = temp.trim();
				return argsString.replace(whiteSpace + locationKeyWord2 + whiteSpace + temp.trim(), emptyString);
			}
		} catch (Exception ex) {
			return argsString.trim();
		}
	}

	private static Boolean isEmptyDesc(String argsString) {
		return argsString == null || argsString.equals(emptyString);
	}

	private static Boolean noTaskStartTime() {
		return PublicVariables.taskStartTime == null;
	}

	private static Boolean noTaskEndTime() {
		return PublicVariables.taskEndTime == null;
	}

	private static Boolean isNotRepeatedIndex(int index) {
		return !PublicVariables.positions.contains(index);
	}
}
