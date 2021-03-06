//@@author A0127358Y
package test.testUtils;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import urgenda.util.MultipleSlot;
import urgenda.util.Task;

public class TaskTest {

	/*
	 * This method is for checking whether a task has been marked as completed.
	 * test cases partition into 2 main types: yes or no, 6 subtypes: floating
	 * yes,no ; event yes,no; deadline yes,no. Also test on empty task.
	 */
	@Test
	public void testIsCompleted() {
		LocalDateTime notime = null;
		MultipleSlot slot = null;
		Task obj = new Task(1, "Buy milk", "floating", "", false, false, false, notime, notime,
				LocalDateTime.now(), notime, slot);
		Task obj2 = new Task(2, "Submit ie2150 draft", "deadline", "", true, false, true, notime,
				LocalDateTime.of(2016, Month.FEBRUARY, 24, 23, 59), LocalDateTime.now(), notime, slot);
		Task obj3 = new Task(3, "Submit ie2100 hw3", "deadline", "", false, false, false, notime,
				LocalDateTime.now(), LocalDateTime.now(), notime, slot);
		Task obj4 = new Task(4, "Dental Appointment", "event", " ", true, false, false,
				LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(1), LocalDateTime.now(),
				notime, slot);
		Task obj5 = new Task(5, "Travel to Sweden", "event", " ", false, false, false,
				LocalDateTime.of(2016, Month.JULY, 26, 00, 00),
				LocalDateTime.of(2016, Month.AUGUST, 17, 23, 59), LocalDateTime.now(), notime, slot);
		Task obj6 = new Task(1, "Mop floor", "floating", "", true, false, false, notime, notime,
				LocalDateTime.now(), notime, slot);
		Task obj7 = new Task();

		assertFalse(obj.isCompleted()); // test floating no
		assertTrue(obj2.isCompleted()); // test deadline yes
		assertFalse(obj3.isCompleted()); // test deadline no
		assertTrue(obj4.isCompleted()); // test event yes
		assertFalse(obj5.isCompleted()); // test event no
		assertTrue(obj6.isCompleted()); // test floating yes
		assertFalse(obj7.isCompleted()); // test empty task

	}

	/*
	 * This method is for checking whether a task has been marked as important.
	 * Test cases partition into 2 main types: yes or no, 6 subtypes: floating
	 * yes,no ; event yes,no; deadline yes,no. Also test on empty task
	 */
	@Test
	public void testIsImportant() {
		LocalDateTime notime = null;
		MultipleSlot slot = null;
		Task obj = new Task(1, "Buy milk", "floating", "", false, false, false, notime, notime,
				LocalDateTime.now(), notime, slot);
		Task obj2 = new Task(2, "Submit ie2150 draft", "deadline", "", true, true, true, notime,
				LocalDateTime.of(2016, Month.FEBRUARY, 24, 23, 59), LocalDateTime.now(), notime, slot);
		Task obj3 = new Task(3, "Submit ie2100 hw3", "deadline", "", false, false, false, notime,
				LocalDateTime.now(), LocalDateTime.now(), notime, slot);
		Task obj4 = new Task(4, "Dental Appointment", "event", " ", true, true, false,
				LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(1), LocalDateTime.now(),
				notime, slot);
		Task obj5 = new Task(5, "Travel to Sweden", "event", " ", false, false, false,
				LocalDateTime.of(2016, Month.JULY, 26, 00, 00),
				LocalDateTime.of(2016, Month.AUGUST, 17, 23, 59), LocalDateTime.now(), notime, slot);
		Task obj6 = new Task(1, "Renew pastport", "floating", "", true, true, false, notime, notime,
				LocalDateTime.now(), notime, slot);
		Task obj7 = new Task();

		assertFalse(obj.isImportant()); // test floating no
		assertTrue(obj2.isImportant()); // test deadline yes
		assertFalse(obj3.isImportant()); // test deadline no
		assertTrue(obj4.isImportant()); // test event yes
		assertFalse(obj5.isImportant()); // test event no
		assertTrue(obj6.isImportant()); // test floating yes
		assertFalse(obj7.isImportant()); // test empty task
	}

	/*
	 * This method is for checking whether a task is overdue.Test cases
	 * partition into 2 main types: yes or no; deadline can be yes or no,
	 * floating, event and empty task will always be no.
	 */
	@Test
	public void testIsOverdue() {
		LocalDateTime notime = null;
		MultipleSlot slot = null;
		Task obj = new Task(1, "Buy milk", "floating", "", false, false, false, notime, notime,
				LocalDateTime.now(), notime, slot);
		Task obj2 = new Task(2, "Submit ie2150 draft", "deadline", "", true, false, true, notime,
				LocalDateTime.of(2016, Month.FEBRUARY, 24, 23, 59), LocalDateTime.now(), notime, slot);
		Task obj3 = new Task(3, "Submit ie2100 hw3", "deadline", "", false, true, false, notime,
				LocalDateTime.now(), LocalDateTime.now(), notime, slot);
		Task obj4 = new Task(4, "Dental Appointment", "event", " ", true, false, false,
				LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(1), LocalDateTime.now(),
				notime, slot);
		Task obj5 = new Task();

		assertFalse(obj.isOverdue()); // test floating
		assertTrue(obj2.isOverdue()); // test deadline yes
		assertFalse(obj3.isOverdue()); // test deadline no
		assertFalse(obj4.isOverdue()); // test event
		assertFalse(obj5.isOverdue()); // test empty task
	}

	/*
	 * This method is for checking whether two task overlaps. Equivalence
	 * Partition: Dont overlap (task tested lies totally to the left of compared
	 * task) Dont overlap(task tested lies totally to the right of compared
	 * task. Overlap: End of task tested overlaps w compared task. Overlap:
	 * start of task tested overlaps w compared task. Overlap: task tested same
	 * as compared task. Overlap: task tested lies within compared task.
	 * Overlap: task compared is subset of task tested. Boundary value analysis:
	 * used 00000 , 235959 as timings
	 */
	@Test
	public void testHasOverlap() {
		Task test = new Task();
		LocalDateTime start = LocalDateTime.of(2016, Month.MARCH, 1, 9, 00, 00);
		LocalDateTime end = LocalDateTime.of(2016, Month.MARCH, 1, 22, 00, 00);
		LocalDateTime compareStart1 = LocalDateTime.of(2016, Month.MARCH, 1, 9, 00, 00);
		LocalDateTime compareEnd1 = LocalDateTime.of(2016, Month.MARCH, 1, 22, 00, 00);
		LocalDateTime compareStart2 = LocalDateTime.of(2016, Month.MARCH, 1, 19, 30, 59);
		LocalDateTime compareEnd2 = LocalDateTime.of(2016, Month.MARCH, 1, 20, 45, 01);
		// Boundary value for time 00,00
		LocalDateTime compareStart3 = LocalDateTime.of(2016, Month.MARCH, 1, 00, 00, 00);
		LocalDateTime compareEnd3 = LocalDateTime.of(2016, Month.MARCH, 1, 11, 17, 00);
		LocalDateTime compareStart4 = LocalDateTime.of(2016, Month.MARCH, 1, 12, 35, 00);
		// The other Boundary value for time 23,59
		LocalDateTime compareEnd4 = LocalDateTime.of(2016, Month.MARCH, 1, 23, 59, 59);
		LocalDateTime compareStart5 = LocalDateTime.of(2016, Month.MARCH, 1, 7, 00, 00);
		LocalDateTime compareEnd5 = LocalDateTime.of(2016, Month.MARCH, 1, 9, 00, 00);
		LocalDateTime compareStart6 = LocalDateTime.of(2016, Month.MARCH, 1, 22, 00, 00);
		LocalDateTime compareEnd6 = LocalDateTime.of(2016, Month.MARCH, 1, 23, 00, 00);
		LocalDateTime compareStart7 = LocalDateTime.of(2016, Month.MARCH, 1, 8, 00, 34);
		LocalDateTime compareEnd7 = LocalDateTime.of(2016, Month.MARCH, 1, 23, 54, 00);

		// overlap: task tested same as compared task.
		assertTrue(test.hasOverlap(start, end, compareStart1, compareEnd1));
		// overlap: task tested lies within compared task.
		assertTrue(test.hasOverlap(start, end, compareStart2, compareEnd2));
		// Overlap: End of task tested overlaps w compared task.
		assertTrue(test.hasOverlap(start, end, compareStart3, compareEnd3));
		// Overlap: Start of task tested overlaps w compared task.
		assertTrue(test.hasOverlap(start, end, compareStart4, compareEnd4));
		// Dont overlap (task tested lies totally to the left of compared task)
		assertFalse(test.hasOverlap(start, end, compareStart5, compareEnd5));
		// Dont overlap (task tested lies totally to the right of compared task)
		assertFalse(test.hasOverlap(start, end, compareStart6, compareEnd6));
		// Overlap: task compared is subset of task tested.
		assertTrue(test.hasOverlap(start, end, compareStart7, compareEnd7));

	}

	/*
	 * This method test whether a task is overlapping w other task. Equivalence
	 * partition: yes, no, no(not an event), no(empty task)
	 */
	@Test
	public void testIsOverlapping() {
		LocalDateTime notime = null;
		MultipleSlot slot = null;
		Task compare = new Task(4, "Dental Appointment", "EVENT", " ", true, false, false,
				LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(1), LocalDateTime.now(),
				notime, slot);
		Task test1 = new Task(1, "Buy milk", "FLOATING", "", false, false, false, notime, notime,
				LocalDateTime.now(), notime, slot);
		Task test2 = new Task(2, "Dental Appointment", "EVENT", " ", true, false, false,
				LocalDateTime.now().minusHours(2), LocalDateTime.now(), LocalDateTime.now(), notime, slot);
		Task test3 = new Task(3, "Dental Appointment", "EVENT", " ", true, false, false,
				LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5), LocalDateTime.now(),
				notime, slot);
		Task test4 = new Task();

		assertFalse(compare.isOverlapping(test1)); // no (non-event)
		assertTrue(compare.isOverlapping(test2)); // yes
		assertFalse(compare.isOverlapping(test3)); // no
		assertFalse(compare.isOverlapping(test4)); // no (empty task)
	}

}
