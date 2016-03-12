package urgenda.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;

import urgenda.util.MultipleSlot;
import urgenda.util.Task;

public class Decryptor extends JsonCipher {

	public Decryptor() {
		super();
	}

	public void decryptTaskList(ArrayList<Task> taskList, ArrayList<String> taskStrList) {
		for (int i = 0; i < taskStrList.size(); i++) {
			_detailsString = taskStrList.get(i);
			convertToMap();
			Task newTask = generateTask(i + 1);
			taskList.add(newTask);
		}
	}
	
	public void decryptArchiveList(ArrayList<Task> archiveList, ArrayList<String> archiveStrList) {
		for (int i = 0, j = -1; i < archiveStrList.size(); i++, j--) {
			_detailsString = archiveStrList.get(i);
			convertToMap();
			Task newTask = generateTask(j);
			archiveList.add(newTask);
		}
	}

	private Task generateTask(int i) {
		int id = i;
		String desc = getDesc();
		String type = getType();
		String location = getLocation();
		boolean isCompleted = checkCompleted();
		boolean isImportant = checkImportant();
		boolean isOverdue = checkOverdue();
		LocalDateTime startTime = getStartTime();
		LocalDateTime endTime = getEndTime();
		LocalDateTime dateAdded = getDateAdded();
		LocalDateTime dateModified = getDateModified();
		ArrayList<String> hashTags = getHashTags();
		String multipleDesc = getMultipleDesc();
		String multipleId = getMultipleId();
		MultipleSlot newSlot = new MultipleSlot(multipleDesc, multipleId);
		Task newTask = new Task(id, desc, type, location, isCompleted, isImportant, isOverdue, startTime, endTime,
				dateAdded, dateModified, hashTags, newSlot);
		return newTask;
	}

}
