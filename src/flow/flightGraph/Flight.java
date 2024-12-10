package flow.flightGraph;

import flow.main.TravelTimeCalculator;

/**
 * Represents a single flight.
 * Contains: origin, destination, departure and arrival time.
 */
public class Flight {

	private String origin, dest;
	private Timestamp depTime;
	private Timestamp arrTime;

	/**
	 * Flight constructor.
	 * 
	 * @param origin  - Origin Airport
	 * @param depHour - Departure Hours
	 * @param depMin  - Departure Minutes
	 * @param dest    - Destination Airport
	 * @param arrHour - Arrival Hours
	 * @param arrMin  - Arrival Minutes
	 */
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

	/**
	 * Checks if the flight is reachable by flight fi.
	 * A flight is reachable by another if there is a gap of 180 minutes in between.
	 * 
	 * @param fi - the flight to check reachability
	 * @return true if there is a gap of 180 minutes
	 */
	public boolean isReachableBy(Flight fi) {
		TravelTimeCalculator calculator = new TravelTimeCalculator();
		calculator.loadFromFile();
		int time = calculator.getTravelTime(this.dest, fi.getOrigin());
		if (time != -1) {
			return this.getDepTime().deferByMinutes(fi.getArrTime(), (time + 1) * 60);
		} else {
			return false;
		}
	}

	/**
	 * Checks if the flight is reachable by flight fi, given that it's origin is the
	 * same as
	 * fi's destination and there is a gap of 60 minutes in between.
	 * 
	 * @param fi - the flight to check reachability
	 * @return true if there is a gap of 60 minutes and airports match
	 */
	public boolean isSameAirportAndReachableBy(Flight fi) {
		return this.getOrigin().equals(fi.getDest())
				&& this.getDepTime().deferByMinutes(fi.getArrTime(), 60);
	}

	@Override
	public String toString() {
		return "Flight [origin=" + origin + ", dest=" + dest + ", depTime=" + depTime + ", arrTime=" + arrTime + "]";
	}
}