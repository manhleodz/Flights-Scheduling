package flow.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TravelTimeCalculator {
    private Map<String, Map<String, Integer>> travelTimes = new HashMap<>();

    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("/home/manhleo/Code/School/TURR/Flights Scheduling/travel_times.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String city1 = parts[0];
                String city2 = parts[1];
                int time = Integer.parseInt(parts[2]);

                travelTimes.computeIfAbsent(city1, k -> new HashMap<>()).put(city2, time);
                travelTimes.computeIfAbsent(city2, k -> new HashMap<>()).put(city1, time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTravelTime(String city1, String city2) {
        Map<String, Integer> timesFromCity1 = travelTimes.get(city1);
        if (timesFromCity1 != null) {
            return timesFromCity1.getOrDefault(city2, -1);
        }
        return -1;
    }
}