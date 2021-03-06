package test.testCommand;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import urgenda.command.Postpone;
import urgenda.logic.LogicData;
import urgenda.util.LogicException;
import urgenda.util.Task;

public class PostponeTest {

	@Test
	public void testExecute() {
		LogicData data = LogicData.getInstance(true);
		

		// test for event task
		ArrayList<Task> expectedTasks = new ArrayList<Task>();
		LocalDateTime start = LocalDateTime.of(2016, 7, 5, 0, 0);
		LocalDateTime end = LocalDateTime.of(2016, 7, 6, 0, 0);
		Task eventTask = new Task("event task", null, start, end, false);
		data.addTask(eventTask);
		data.setDisplays(data.getTaskList());
		LocalDateTime newStart = LocalDateTime.of(2016, 7, 6, 0, 0);
		LocalDateTime newEnd = LocalDateTime.of(2016, 7, 7, 0, 0);
		Task expected = new Task("event task", null, newStart, newEnd, false);
		expectedTasks.add(expected);
		Postpone cmd = new Postpone();
		cmd.setDay(1);
		cmd.setId(0);
		try {
			cmd.execute();
		} catch (LogicException e) {
			e.printStackTrace();
		}
		checkArrayList(expectedTasks, data.getTaskList());
		
	}

	private void checkArrayList(ArrayList<Task> expectedTasks, ArrayList<Task> actualTasks) {
		for (int i = 0; i < actualTasks.size(); i++) {
			Task exTask = expectedTasks.get(i);
			Task actlTask = actualTasks.get(i);
			assertEquals(exTask.getDesc(), actlTask.getDesc());
			assertEquals(exTask.getLocation(), actlTask.getLocation());
			assertEquals(exTask.isCompleted(), actlTask.isCompleted());
			assertEquals(exTask.isImportant(), actlTask.isImportant());
			assertEquals(exTask.isOverdue(), actlTask.isOverdue());
			assertEquals(exTask.getStartTime(), actlTask.getStartTime());
			assertEquals(exTask.getEndTime(), actlTask.getEndTime());
			assertEquals(exTask.getTaskType(), actlTask.getTaskType());			
		}
		
	}

}
