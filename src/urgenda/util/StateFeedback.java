package urgenda.util;

public class StateFeedback {
	
	private TaskList _overdueTasks;
	private TaskList _urgentTasks;
	private TaskList _todayTasks;
	private TaskList _otherTasks;
	private TaskList _archiveTasks;
	private String _feedback;
	
	public StateFeedback() {
		_overdueTasks = new TaskList();
	}
	
	public StateFeedback(String feedback) {
		_feedback = feedback;
	}
	
	public TaskList getOverdueTasks() {
		return _overdueTasks;
	}

	public TaskList getUrgentTasks() {
		return _urgentTasks;
	}

	public TaskList getTodayTasks() {
		return _todayTasks;
	}

	public TaskList getOtherTasks() {
		return _otherTasks;
	}
	
	public TaskList getArchiveTasks() {
		return _archiveTasks;
	}

	public String getFeedback() {
		return _feedback;
	}

	public void setOverdueTasks(TaskList overdueTasks) {
		_overdueTasks = overdueTasks;
	}

	public void setUrgentTasks(TaskList urgentTasks) {
		_urgentTasks = urgentTasks;
	}

	public void setTodayTasks(TaskList todayTasks) {
		_todayTasks = todayTasks;
	}

	public void setOtherTasks(TaskList otherTasks) {
		_otherTasks = otherTasks;
	}
	
	public void setArchiveTasks(TaskList archiveTasks) {
		_archiveTasks = archiveTasks;
	}

	public void setFeedback(String feedback) {
		_feedback = feedback;
	}

}