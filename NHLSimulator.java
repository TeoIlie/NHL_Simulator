/*
Name:           Teodor Ilie
NetID:          17TI5
Student number: 20100698
Description: This program simulates a season of the NHL by randomly assigning forwards, defense and goalies
    skill levels ranging from 1 to 10, where 10 is best, using the predetermined ranges in teamSkillLevels.
    In the simulation option, it then has each team play every other team twice, and display the first and last
    team. There are two other options: to view a team's skill profile and to print an end of regular season table.
    These options become available after the simulation has been run.
*/
import java.util.Scanner;

public class NHLSimulator {

    //Attributes

    //Store whether a simulation has run yet;
    private static boolean simulationComplete = false;
    //Store the team skill level ranges in a 2d int array
    private static int[][] teamSkillLevels = {
            {5, 9, 4, 9, 5, 7},
            {6, 9, 4, 7, 4, 7},
            {4, 8, 5, 7, 4, 9},
            {4, 9, 5, 8, 7, 10},
            {4, 7, 6, 8, 4, 6},
            {5, 7, 4, 8, 5, 9},
            {4, 7, 4, 7, 4, 9},
            {4, 7, 4, 7, 5, 6},
            {6, 8, 5, 7, 6, 8},
            {5, 7, 4, 6, 5, 7},
            {4, 6, 4, 5, 4, 5},
            {4, 6, 4, 6, 4, 7},
            {6, 10, 4, 7, 5, 7},
            {6, 10, 6, 10, 7, 9},
            {6, 10, 5, 8, 6, 8}
    };
    //Team[] is an array of the teams
    private static Team[] teams = new Team[16];
    //Store the team names in an array of Strings
    private static String[] teamNames = {"Boston", "Buffalo", "Carolina", "Columbus", "Detroit", "Florida", "Montreal",
            "New Jersey", "NY Islanders", "NY Rangers", "Ottawa", "Philadelphia", "Pittsburgh",
            "Tampa Bay", "Washington"};
    private static int[][] leafsSkillsNumbers = {
            //holds number and skill of leafs players in order: 13 forwards, 8 defense, 4 goalies
            {28, 5},
            {63, 4},
            {33, 5},
            {11,7},
            {18,7},
            {43,7},
            {24,8},
            {26,8},
            {12,8},
            {16, 9},
            {34, 9},
            {29, 9},
            {91, 10},
            {23, 8},
            {51, 4},
            {2, 5},
            {3, 6},
            {52, 4},
            {92, 6},
            {44, 9},
            {22, 8},
            {31, 10},
            {30, 7},
            {50, 5},
            {40, 6}
    };
    //kbd used for user input
    private static Scanner kbd = new Scanner(System.in);

    public static void main(String[] args){
        //THIS IS WHERE THE PROGRAM IS RUN.

        int option;

        while (true) {
            //print menu options
            System.out.println("NHL Simulator (Version 0.1). Author: Teo Ilie");
            System.out.println("1 – Simulate NHL Season (Eastern Conference)");
            System.out.println("2 – View Team Skill Level Profile");
            System.out.println("3 – Display End of Regular Season Table");

            //Check the input is valid
            option = getUserIntInput();

            //Quit if user inputs 9
            if(option == 9){
                System.out.println("\nThank you for using the simulator.");
                break;
            }

            //Execute the option picked by the user
            if (!(option == 1 || option == 2  || option == 3)){
                System.out.println("Incorrect entry. Try again.");
            }
            else {
                switch (option) {
                    case 1: {
                        //simulate NHL Season (Eastern Conference)
                        simulateNHLSeason();
                        break;
                    }
                    case 2: {
                        //view team skill profile
                        if (simulationComplete){

                            kbd.nextLine(); //clear previous garbage

                            System.out.print("\nEnter Team Name: ");
                            String teamChoice = kbd.nextLine();

                            int index = findTeamInTeams(teamChoice);

                            if(index != -1) {
                                //if user input is valid, this case is over
                                teams[index].printTeamSkillProfile();
                            }

                            else {
                                //if user choice is invalid, prompt for input until it is valid, or input empty
                                while ((!(teamChoice.isEmpty()) && (index == -1))) {
                                    System.out.println("\n" + teamChoice + " is invalid! " +
                                            "Please re-enter or press [Enter]");
                                    teamChoice = kbd.nextLine();
                                    index = findTeamInTeams(teamChoice);
                                }
                                if (!(index == -1))
                                    //make sure it isn't the case where user entered 'enter' and index is -1
                                    teams[index].printTeamSkillProfile();
                            }
                        }
                        else
                            System.out.println("\nMust run NHL Eastern Conference " +
                                    "Simulation before accessing this option!\n");
                        break;
                    }
                    case 3: {
                        //display end of regular season table
                        if (simulationComplete){
                            printSeasonTable();
                            System.out.println("\nPress any key to continue");
                            kbd.nextLine();
                            kbd.nextLine();
                            //IS THIS OK DO WE CHANGE IT??????
                        }
                        else
                            System.out.println("\nMust run NHL Eastern Conference " +
                                    "Simulation before accessing this option!\n");
                        break;
                    }
                }
            }
        }
    }
    private static void simulateNHLSeason(){
        //This is where the NHL Simulation is done
        simulationComplete = true; // let the program know the simulation has run

        //generate the teams with random skills in the skill range, skipping index 14
        //first will be used for Maple Leafs, handled separately
        for (int i = 0; i < teams.length; i++) {
            if (i == 14) {
                i++;
            }
            if (i == 15){
                teams[i] = new Team(teamNames[i-1], teamSkillLevels[i-1]);
            }
            else {
                teams[i] = new Team(teamNames[i], teamSkillLevels[i]);
            }
        }

        //generate Leafs team with separate constructor and place at index 14 for alphabetical order
        teams[14] = new Team("Toronto", leafsSkillsNumbers);

        //have each team play every other team twice (16 choose 2) x 2 and update team stats as they play
        Game game = new Game(); // game object will play games with Team objects

        for (int i = 0; i < teams.length - 1; i++){
            for (int j = i+1; j < teams.length; j++){
                for (int count = 0; count < 2; count++){
                    game.playGame(teams[i], teams[j]);
                }
            }
        }

        //find team with most points, team with least
        int maxPoints = 0;  //certainly there is a team with more than 0 points
        int maxIndex = 0;

        int minPoints = Integer.MAX_VALUE;  //certainly theres is a team with less than this points
        int minIndex = 0;

        for (int i = 0; i < teams.length; i++){
            if (teams[i].getPoints() > maxPoints) {
                maxIndex = i;
                maxPoints = teams[i].getPoints();
            }
            if (teams[i].getPoints() < minPoints){
                minIndex = i;
                minPoints = teams[i].getPoints();
            }
        }

        //print out the best and worst team by points scored
        System.out.println("\nNHL Regular Season – Eastern Conference – 2018/2019");

        System.out.printf("%-30s %-20s %n", "First Team: " + teams[maxIndex].getTeamName(), "Points: " +
                teams[maxIndex].getPoints());
        System.out.printf("%-30s %-20s %n", "Last Team: " + teams[minIndex].getTeamName(), "Points: " +
                teams[minIndex].getPoints());

        System.out.println("Simulation completed!\n");
    }

    private static void printSeasonTable(){
        System.out.println("\nTOTAL SCORES AND STATISTICS REPORT");
        System.out.println("***********************************");
        System.out.printf("%-20s %-5s %-6s %-5s %-5s %-5s %-5s %-5s %-5s %n", "Team Name",
                "GP", "W", "L", "OTL", "Pts", "GF", "GA", "Diff");
        System.out.println("******************* ***** ***** ***** ****** ***** ***** ***** ******");

        for (int i = 0; i < teams.length; i++){
            System.out.printf("%-20s %-5s %-6s %-5s %-5s %-5s %-5s %-5s %-5s %n", teams[i].getTeamName(),
                    teams[i].getGamesPlayed(), teams[i].getWins(), teams[i].getLosses(),
                    teams[i].getOvertimeLosses(), teams[i].getPoints(), teams[i].getGoalsFor(),
                    teams[i].getGoalsAgainst(), teams[i].getDifference());
        }
    }

    private static int findTeamInTeams(String team){
        //finds a team name 'team' in teams, returns index. returns -1 if not found
        for(int i = 0; i < teams.length; i++){
            if (teams[i].getTeamName().equals(team))
                return i;
        }

        return -1;
    }

    private static int getUserIntInput(){
        //prompts the user until they enter an integer 1,2,3 or 9, telling them what is wrong with their input
        System.out.printf("Select Option [1, 2 or 3] (9 to Quit):");
        String input = kbd.next();
        int intInput = -1;  //-1 represents the user has not yet made a valid entry

        while(intInput == -1){
            try{
                intInput = Integer.parseInt(input);
                if (!(intInput == 1 || intInput == 2 || intInput == 3 || intInput == 9)){
                    intInput = -1;
                    System.out.printf("\nNot a valid choice. Select Option [1, 2 or 3] (9 to Quit):");
                    input = kbd.next();
                }
            }
            catch (NumberFormatException ex){ // handle your exception
                System.out.printf("\nNot an integer. Select Option [1, 2 or 3] (9 to Quit):");
                input = kbd.next();
            }
        }

        return intInput;
    }
}