//This class holds objects of class Player to form a team.
import java.util.*;

public class Team {

    //Attributes
    private String teamName;
    private Player[] forwards = new Player[13];
    private Player[] defense = new Player[8];
    private Player[] goalies = new Player[4];
    private int gamesPlayed = 0;
    private int wins = 0;
    private int losses = 0;
    private int overtimeLosses = 0;
    private int points = 0;
    private int goalsFor = 0;
    private int goalsAgainst = 0;
    private int difference= 0;
    private static Random generator = new Random(System.currentTimeMillis());

    //Constructors Maple Leafs and another for other teams
    Team(String name, int[] skillRanges){
        //name holds the team name, skillRanges is an array of 6 integers representing skill ranges
        //first pair is forwards range, second pair defense, third goalies
        this.teamName = name;

        //generate 25 distinct numbers from 1 to 99
        int[] numbers = generateNumbers();
        int index = 0; //to keep track of index in numbers
        int skill = 0; // to generate skill

        //build the forwards
        for (int i = 0; i < this.forwards.length; i++) {
            //generate skill level using skillRanges, assign number from numbers, assign as forward
            skill = generator.nextInt(skillRanges[1]- skillRanges[0] + 1) + skillRanges[0];
            this.forwards[i] = new Player(numbers[index], skill, "Forward");
            index++;
        }

        //build the defense
        for (int i = 0; i < this.defense.length; i++) {
            //generate skill level using skillRanges, assign number from numbers, assign as defense
            skill = generator.nextInt(skillRanges[3] - skillRanges[2] + 1) + skillRanges[2];
            this.defense[i] = new Player(numbers[index], skill, "Defense");
            index++;
        }

        for (int i = 0; i < this.goalies.length; i++) {
            //generate skill level using skillRanges, assign number from numbers, assign as goalie
            skill = generator.nextInt(skillRanges[5] - skillRanges[4] + 1) + skillRanges[4];
            this.goalies[i] = new Player(numbers[index], skill, "Goalie");
            index++;

        }
    }
    Team(String teamName, int[][] leafsSkillsNumbers) {
        //separate constructor for Toronto leafs team to give specific numbers and skills
        this.teamName = teamName;

        int index = 0; //keep track of index in leafsSkillNumbers

        //builds forwards
        for (int i = 0; i < this.forwards.length; i++){
            forwards[i] = new Player(leafsSkillsNumbers[index][0], leafsSkillsNumbers[index][1], "Forward");
            index++;
        }

        //build defense
        for (int i = 0; i < this.defense.length; i++){
            defense[i] = new Player(leafsSkillsNumbers[index][0], leafsSkillsNumbers[index][1], "Defense");
            index++;
        }

        //build goalies
        for (int i = 0; i < this.goalies.length; i++){
            goalies[i] = new Player(leafsSkillsNumbers[index][0], leafsSkillsNumbers[index][1], "Goalie");
            index++;
        }
    }

    //Methods
    private int[] generateNumbers(){
        //this method generates an array of 25 different ints from 1 to 99
        int[] numbers = new int[25];
        int val = 0;    //temporary value to produce different values

        for (int i = 0; i < numbers.length; ++i){
            //we must calculate a value not yet in the array so far

            val = generator.nextInt(99) + 1;
            while (inArray(numbers, val)){
                val = generator.nextInt(99) + 1;
            }

            //put this new value in numbers[]
            numbers[i] = val;
        }
        return numbers;
    }

    public void printTeamSkillProfile(){
        System.out.println();
        System.out.println("No      Name        Position        Skill level");
        System.out.println("***     *****       **********      ************");

        //print forwards information
        for (int i = 0; i < forwards.length; i++){
            System.out.printf("%-7s %-11s %-20s %-10s %n", forwards[i].getNumber(), forwards[i].getName(),
                    forwards[i].getPosition(), forwards[i].getSkillLevel());

        }
        System.out.println();

        //print defense information
        for (int i = 0; i < defense.length; i++) {
            System.out.printf("%-7s %-11s %-20s %-10s %n", defense[i].getNumber(), defense[i].getName(),
                    defense[i].getPosition(), defense[i].getSkillLevel());
        }
        System.out.println();

        //print goalie information
        for (int i = 0; i < goalies.length; i++) {
            System.out.printf("%-7s %-11s %-20s %-10s %n", goalies[i].getNumber(), goalies[i].getName(),
                    goalies[i].getPosition(), goalies[i].getSkillLevel());
        }
        System.out.println();
    }

    private boolean inArray(int[] arr, int val){
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == val)
                return true;
        }
        return false;

    }
    public Player[] getForwards(){
        return this.forwards;
    }
    public Player[] getDefense(){
        return this.defense;
    }
    public Player[] getGoalies(){
        return this.goalies;
    }
    public String getTeamName(){
        return  this.teamName;
    }
    public int getGoalsFor(){
        return this.goalsFor;
    }
    public int getGoalsAgainst(){
        return this.goalsAgainst;
    }
    public int getPoints(){
        return this.points;
    }
    public int getDifference(){
        return this.difference;
    }
    public int getGamesPlayed(){
        return this.gamesPlayed;
    }
    public int getLosses(){
        return this.losses;
    }
    public int getOvertimeLosses(){
        return this.overtimeLosses;
    }
    public int getWins(){
        return this.wins;
    }
    public void incrementGoalsFor(){
        this.goalsFor++;
    }
    public void incrementGoalsAgainst(){
        this.goalsAgainst++;
    }
    public void addPoints(int p){
        this.points += p;
    }
    public void updateDifference(){
        this.difference = this.goalsFor - this.goalsAgainst;
    }
    public void incrementGamesPlayed (){
        this.gamesPlayed++;
    }
    public void incrementLosses (){
        this.losses++;
    }
    public void incrementOvertimeLosses() {
        this.overtimeLosses++;
    }
    public void incrementWins() {
        this.wins++;
    }

}
