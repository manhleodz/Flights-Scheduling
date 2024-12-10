package flow.flightGraph;

import flow.main.TravelTimeCalculator;

public class Flight {

	private String origin, dest;
	private Timestamp depTime;
	private Timestamp arrTime;

	public Flight(String origin, int depHour, int depMin, String dest, int arrHour, int arrMin) {
		this.origin = origin;
		this.dest = dest;
		depTime = new Timestamp(depHour, depMin);
		arrTime = new Timestamp(arrHour, arrMin);
	}

	public String getOrigin() {
		return origin;
	}

	public String getDest() {
		return dest;
	}

	public Timestamp getDepTime() {
		return depTime;
	}

	public Timestamp getArrTime() {
		return arrTime;
	}

	public boolean isReachableBy(Flight fi) {
		TravelTimeCalculator calculator = new TravelTimeCalculator();
		calculator.loadFromFile();
		// fi.getDest(),fj.getOrigin()
		int time = calculator.getTravelTime(this.origin, fi.getDest());
		if (time != -1) {
			return this.getDepTime().deferByMinutes(fi.getArrTime(), (time + 1) * 60);
		} else {
			return false;
		}
	}

	public boolean isSameAirportAndReachableBy(Flight fi) {
		return this.getOrigin().equals(fi.getDest())
				&& this.getDepTime().deferByMinutes(fi.getArrTime(), 60);
	}

	@Override
	public String toString() {
		return "Flight [origin=" + origin + ", dest=" + dest + ", depTime=" + depTime + ", arrTime=" + arrTime + "]";
	}
}