package flow.flightGraph;

public class Timestamp implements Comparable<Timestamp> {

	private int hour, minutes;
	private int totalMins;

	public Timestamp(int hour, int minutes) throws ArithmeticException {
		if ((hour < 0 || hour > 23) || minutes < 0 || minutes > 59) {
			throw new ArithmeticException();
		} else {
			this.hour = hour;
			this.minutes = minutes;
			totalMins = hour * 60 + minutes;
		}
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public boolean deferByMinutes(Timestamp t, int min) {
		return this.totalMins - t.totalMins >= min;
	}

	@Override
	public int compareTo(Timestamp t) {
		if (this.totalMins > t.totalMins) {
			return 1;
		} else if (this.totalMins < t.totalMins) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return String.format("%02d", hour) + ":"
				+ String.format("%02d", minutes);
	}
}
