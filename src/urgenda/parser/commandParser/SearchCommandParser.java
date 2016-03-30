package urgenda.parser.commandParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import urgenda.command.*;
import urgenda.parser.DateTimeParser;
import urgenda.parser.PublicFunctions;
import urgenda.parser.PublicVariables;
import urgenda.parser.TaskDetailsParser;

public class SearchCommandParser {
	private static String _argsString;
	private static int _index;
	
	public SearchCommandParser(String argsString, int index) {
		_argsString = argsString;
		_index = index;
	}

	public static Command generateAndReturn() {
		if (_argsString == null) {
			return new Invalid();
		} else {
			_argsString = PublicFunctions.reformatArgsString(_argsString);
			Search searchCommand = new Search();
			Month testMonth = DateTimeParser.tryParseMonth(_argsString);
			LocalDateTime testTime = DateTimeParser.tryParseTime(_argsString);
			LocalDate testDate = DateTimeParser.tryParseDate(_argsString);
			if (testMonth != null) {
				searchCommand.setSearchMonth(testMonth);
			} else if (testTime != null) {
				searchCommand.setSearchDateTime(testTime);
			} else if (testDate != null) {
				searchCommand.setSearchDate(testDate);
			} else {
				TaskDetailsParser.searchTaskDescription(_argsString);
				searchCommand.setSearchInput(PublicVariables.taskDescription);
			}
			return searchCommand;
		}
	}
}
