package test.testUtils;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import urgenda.util.DateTimePair;

public class DateTimePairTest {
	
	private LocalDateTime referenceDateTime = LocalDateTime.now();
	
	@Test
	public void testRelativeDifference() {
		DateTimePair t1 = new DateTimePair(referenceDateTime, referenceDateTime);
		assertEquals(t1.firstIsBefore(), false);
		DateTimePair t2 = new DateTimePair(referenceDateTime, referenceDateTime.plusSeconds(1));
		assertEquals(t2.firstIsBefore(), true);
		DateTimePair t3 = new DateTimePair(referenceDateTime, referenceDateTime.minusSeconds(1));
		assertEquals(t3.firstIsBefore(), false);
		DateTimePair t4 = new DateTimePair(referenceDateTime.plusSeconds(1), referenceDateTime);
		assertEquals(t4.firstIsBefore(), false);
		DateTimePair t5 = new DateTimePair(referenceDateTime.minusSeconds(1), referenceDateTime);
		assertEquals(t5.firstIsBefore(), true);
		assertEquals(t5.getEarlierDateTime(), referenceDateTime.minusSeconds(1));
		assertEquals(t5.getLaterDateTime(), referenceDateTime);
	}
	
	@Test
	public void testAddMinusDateTimes() {
		DateTimePair t1 = new DateTimePair(referenceDateTime, referenceDateTime);
		t1.addToEarlierDateTime(1, ChronoUnit.SECONDS);
		assertEquals(t1.getEarlierDateTime(), referenceDateTime);
		assertEquals(t1.getLaterDateTime(), referenceDateTime.plusSeconds(1));
		t1.addToEarlierDateTime(1, ChronoUnit.SECONDS);
		assertEquals(t1.getEarlierDateTime(), referenceDateTime.plusSeconds(1));
		DateTimePair t2 = new DateTimePair(referenceDateTime, referenceDateTime);
		t2.addToLaterDateTime(1, ChronoUnit.SECONDS);
		t2.addToEarlierDateTime(2, ChronoUnit.SECONDS);
		assertEquals(t2.getEarlierDateTime(), referenceDateTime.plusSeconds(1));
		assertEquals(t2.getLaterDateTime(), referenceDateTime.plusSeconds(2));
	}
	
	@Test
	public void testEquals() {
		DateTimePair t1 = new DateTimePair(referenceDateTime, referenceDateTime);
		DateTimePair t2 = new DateTimePair(referenceDateTime, referenceDateTime);
		DateTimePair t3 = new DateTimePair(referenceDateTime, referenceDateTime.plusSeconds(1));
		DateTimePair t4 = new DateTimePair(referenceDateTime.plusSeconds(1), referenceDateTime);
		assertEquals(t1.equals(t2), true);
		assertEquals(t1.equals(t3), false);
		assertEquals(t1.equals(t4), false);
		assertEquals(t3.equals(t4), true);
	}
	
	@Test
	public void testRoundedDays() {
		LocalDateTime dt1 = LocalDateTime.of(1999, 12, 31, 23, 59);
		LocalDateTime dt2 = LocalDateTime.of(2000, 1, 1, 0, 0);
		LocalDateTime dt3 = LocalDateTime.of(2001, 1, 1, 0, 0);
		DateTimePair t1 = new DateTimePair(dt1, dt1);
		assertEquals(t1.getRoundedDays(), 0);
		DateTimePair t2 = new DateTimePair(dt1, dt2);
		assertEquals(t2.getRoundedDays(), 1);
		DateTimePair t3 = new DateTimePair(dt2, dt3);
		assertEquals(t3.getRoundedDays(), 366);
	}
}
