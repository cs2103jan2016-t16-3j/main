package urgenda.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import urgenda.util.Task;

public class JsonCipher {
	private static final String HASHMAP_KEY_ID = "id";
	private static final String HASHMAP_KEY_DESC = "desc";
	private static final String HASHMAP_KEY_TYPE = "type";
	private static final String HASHMAP_KEY_LOCATION = "location";
	private static final String HASHMAP_KEY_STARTTIME = "startTime";
	private static final String HASHMAP_KEY_ENDTIME = "endTime";
	private static final String HASHMAP_KEY_TAGS = "tags";
	private static final String HASHMAP_KEY_DATEADDED = "dateAdded";
	private static final String HASHMAP_KEY_DATEMODIFIED = "dateModified";
	private static final String HASHMAP_KEY_COMPLETED = "completed";
	private static final String HASHMAP_KEY_IMPORTANT = "important";
	private static final String HASHMAP_KEY_OVERDUE = "overdue";
	private static final String HASHMAP_KEY_FILE_DIRECTORY = "directory";
	private static final String HASHMAP_KEY_FILE_NAME = "name";

	private static final String DELIMITER_HASHTAG = ",";

	private static final String TASKTYPE_EVENT = "EVENT";
	private static final String TASKTYPE_DEADLINE = "DEADLINE";
	private static final String TASKTYPE_FLOATING = "FLOATING";

	protected Gson _gson;
	protected LinkedHashMap<String, String> _detailsMap;
	protected String _detailsString;

	public JsonCipher() {
		_gson = new Gson();
		_detailsMap = new LinkedHashMap<String, String>();
	}
	
	public JsonCipher(String detailsString) {
		_gson = new Gson();
		_detailsMap = new LinkedHashMap<String, String>();
		_detailsString = detailsString;
	}

	public void convertToMap() {
		_detailsMap = _gson.fromJson(_detailsString, new TypeToken<LinkedHashMap<String, String>>() {
		}.getType());
	}
	
	public void convertToString(){
		_detailsString = _gson.toJson(_detailsMap);
	}
	
	public void setOverdue(Task task) {
		_detailsMap.put(HASHMAP_KEY_OVERDUE, String.valueOf(task.isOverdue()));
	}

	public void setUrgent(Task task) {
		_detailsMap.put(HASHMAP_KEY_IMPORTANT, String.valueOf(task.isImportant()));
	}

	public void setCompleted(Task task) {
		_detailsMap.put(HASHMAP_KEY_COMPLETED, String.valueOf(task.isCompleted()));
	}

	public void setDateModified(Task task) {
		if (task.getDateModified() == null) {
			_detailsMap.put(HASHMAP_KEY_DATEMODIFIED, null);
		} else {
			_detailsMap.put(HASHMAP_KEY_DATEMODIFIED, task.getDateModified().toString());
		}
	}

	public void setDateAdded(Task task) {
		if (task.getDateAdded() == null) {
			_detailsMap.put(HASHMAP_KEY_DATEADDED, null);
		} else {
			_detailsMap.put(HASHMAP_KEY_DATEADDED, task.getDateAdded().toString());
		}
	}

	public void setHashTags(Task task) {
		if (task.getHashtags() == null) {
			_detailsMap.put(HASHMAP_KEY_TAGS, null);
		} else {
			_detailsMap.put(HASHMAP_KEY_TAGS, String.join(DELIMITER_HASHTAG, task.getHashtags()));
		}
	}

	public void setEndTime(Task task) {
		if (task.getEndTime() == null) {
			_detailsMap.put(HASHMAP_KEY_ENDTIME, null);
		} else {
			_detailsMap.put(HASHMAP_KEY_ENDTIME, task.getEndTime().toString());
		}
	}

	public void setStartTime(Task task) {
		if (task.getStartTime() == null) {
			_detailsMap.put(HASHMAP_KEY_STARTTIME, null);
		} else {
			_detailsMap.put(HASHMAP_KEY_STARTTIME, task.getStartTime().toString());
		}
	}

	public void setLocation(Task task) {
		if (task.getLocation() == null) {
			_detailsMap.put(HASHMAP_KEY_LOCATION, null);
		} else {
			_detailsMap.put(HASHMAP_KEY_LOCATION, task.getLocation());
		}
	}

	public void setType(Task task) {
		if (task.getTaskType().toString().equals(TASKTYPE_EVENT)) {
			_detailsMap.put(HASHMAP_KEY_TYPE, TASKTYPE_EVENT);
		} else if (task.getTaskType().toString().equals(TASKTYPE_DEADLINE)) {
			_detailsMap.put(HASHMAP_KEY_TYPE, TASKTYPE_DEADLINE);
		} else {
			_detailsMap.put(HASHMAP_KEY_TYPE, TASKTYPE_FLOATING);
		}
	}

	public void setDesc(Task task) {
		_detailsMap.put(HASHMAP_KEY_DESC, task.getDesc());
	}

	public ArrayList<String> getHashTags() {
		ArrayList<String> hashTags;
		if (_detailsMap.get(HASHMAP_KEY_TAGS) == null) {
			hashTags = null;
		} else {
			String tagstring = _detailsMap.get(HASHMAP_KEY_TAGS);
			String[] tagstrray = tagstring.split(DELIMITER_HASHTAG);
			hashTags = new ArrayList<String>(Arrays.asList(tagstrray));
		}
		return hashTags;
	}

	public LocalDateTime getDateModified() {
		if (_detailsMap.get(HASHMAP_KEY_DATEMODIFIED) == null) {
			return null;
		} else {
			return LocalDateTime.parse(_detailsMap.get(HASHMAP_KEY_DATEMODIFIED));
		}
	}

	public LocalDateTime getDateAdded() {
		if (_detailsMap.get(HASHMAP_KEY_DATEADDED) == null) {
			return null;
		} else {
			return LocalDateTime.parse(_detailsMap.get(HASHMAP_KEY_DATEADDED));
		}
	}

	public LocalDateTime getEndTime() {
		if (_detailsMap.get(HASHMAP_KEY_ENDTIME) == null) {
			return null;
		} else {
			return LocalDateTime.parse(_detailsMap.get(HASHMAP_KEY_ENDTIME));
		}
	}

	public LocalDateTime getStartTime() {
		if (_detailsMap.get(HASHMAP_KEY_STARTTIME) == null) {
			return null;
		} else {
			return LocalDateTime.parse(_detailsMap.get(HASHMAP_KEY_STARTTIME));
		}
	}

	public boolean checkOverdue() {
		return Boolean.parseBoolean(_detailsMap.get(HASHMAP_KEY_OVERDUE));
	}

	public boolean checkUrgent() {
		return Boolean.parseBoolean(_detailsMap.get(HASHMAP_KEY_IMPORTANT));
	}

	public boolean checkCompleted() {
		return Boolean.parseBoolean(_detailsMap.get(HASHMAP_KEY_COMPLETED));
	}

	public String getLocation() {
		return _detailsMap.get(HASHMAP_KEY_LOCATION);
	}

	public String getType() {
		return _detailsMap.get(HASHMAP_KEY_TYPE);
	}

	public String getDesc() {
		return _detailsMap.get(HASHMAP_KEY_DESC);
	}

	public String getDirectory() {
		return _detailsMap.get(HASHMAP_KEY_FILE_DIRECTORY);
	}

	public String getFileName() {
		return _detailsMap.get(HASHMAP_KEY_FILE_NAME);
	}
	
	public String getDetailsString(){
		return _detailsString;
	}
	
	public LinkedHashMap<String, String> getDetailsMap(){
		return _detailsMap;
	}
	
	public void setDirectory(String path) {
		System.out.println(path);
		_detailsMap.put(HASHMAP_KEY_FILE_DIRECTORY, path);
	}

	public void setFileName(String name) {
		_detailsMap.put(HASHMAP_KEY_FILE_NAME, name);
	}

}