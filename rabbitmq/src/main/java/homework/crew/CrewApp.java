package homework.crew;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CrewApp {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("CREW NAME: ");
        String crewName = reader.readLine();

        Crew crew = new Crew(crewName);
        crew.start();
    }

}
