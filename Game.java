//This class plays games and returns the results
import java.util.*;

public class Game {
    //No Attributes
    //Constructor
    Game() {
        //There is nothing to construct
    }

    //Methods
    public void playGame(Team t1, Team t2){
        //This method has two teams play a game and update their stat attributes
        //First get array of players of both teams
        Player[] t1Forwards = t1.getForwards();
        Player[] t1Defense = t1.getDefense();
        Player[] t1Goalies = t1.getGoalies();

        Player[] t2Forwards = t2.getForwards();
        Player[] t2Defense = t2.getDefense();
        Player[] t2Goalies = t2.getGoalies();

        //1. sum skill levels of defense and forward in t1 and t2, store in p1 and p2 respectively
        int p1 = 0;
        int p2 = 0;

        for (int i = 0; i < t1Forwards.length; i++){
            p1 += t1Forwards[i].getSkillLevel();
            p2 += t2Forwards[i].getSkillLevel();
        }
        for (int i = 0; i < t1Defense.length; i++){
            p1 += t1Defense[i].getSkillLevel();
            p2 += t2Defense[i].getSkillLevel();
        }


        //2. Randomly select one of the 4 goalies in each team.
        //Let us call the skill levels of the goalies selected from teams 1 and 2 “G1” and “G2”, respectively.

        Random generator = new Random(System.currentTimeMillis());

        int i = generator.nextInt(t1Goalies.length);
        int g1 = t1Goalies[i].getSkillLevel();

        i = generator.nextInt(t2Goalies.length);
        int g2 = t2Goalies[i].getSkillLevel();

        //3.Compute sums
        int total1 = p1 + g1;
        int total2 = p2 + g2;


        //4. Decide players quality of play in a game
        int f1quality = generator.nextInt(3) + 1; //1 means poor, 2 average, 3 well
        int d1quality = generator.nextInt(3) + 1;
        int g1quality = generator.nextInt(3) + 1;

        int f2quality = generator.nextInt(3) + 1;
        int d2quality = generator.nextInt(3) + 1;
        int g2quality = generator.nextInt(3) + 1;

        //5. Add or subtract points based on play quality

        //adjust for forwards
        if (f1quality == 1){
            total1 -= 25;
        }
        if (f1quality == 3){
            total1 += 25;
        }
        if (f2quality == 1){
            total2 -= 25;
        }
        if (f1quality == 3){
           total2 += 25;
        }


        //adjust for defense
        if (d1quality == 1){
            total1 -= 40;
        }
        if (d1quality == 3){
            total1 += 40;
        }
        if (d2quality == 1){
            total2 -= 40;
        }
        if (d2quality == 3){
            total2 += 40;
        }


        //adjust for goalies
        if (g1quality == 1){
            total1 -= 60;
        }
        if (g1quality == 3){
            total1 += 60;
        }
        if (g2quality == 1){
            total2 -= 60;
        }
        if (g2quality == 3){
            total2 += 60;
        }


        //6. Calculate goals: 0, 1 or 2 goals for each 50 points computed in its total skill level
        //score 0 or 1 goals for a remainder fraction of 50 points

        //generate quotient and remainder of t1, t2, after division by 50
        int t1Goals = 0;
        int t2Goals = 0;

        int t1TotalQuotient = total1 / 50;
        for (int j = 0; j < t1TotalQuotient; j++){
            i = generator.nextInt(3);
            for (int k = 0; k < i; k++){
                t1.incrementGoalsFor();
                t2.incrementGoalsAgainst();
                t1Goals++;
            }
        }

        int t2TotalQuotient = total2 / 50;
        for (int j = 0; j < t2TotalQuotient; j++){
            i = generator.nextInt(3);
            for (int k = 0; k < i; k++){
                t2.incrementGoalsFor();
                t1.incrementGoalsAgainst();
                t2Goals++;
            }
        }

        int t1TotalRemainder = total1 % 50;
        if (!(t1TotalRemainder == 0)) {
            i = generator.nextInt(2);
            for (int k = 0; k < i; k++) {
                t1.incrementGoalsFor();
                t2.incrementGoalsAgainst();
                t1Goals++;
            }
        }

        int t2TotalRemainder = total2 % 50;
        if (!(t2TotalRemainder == 0)) {
            i = generator.nextInt(2);
            for (int k = 0; k < i; k++) {
                t2.incrementGoalsFor();
                t1.incrementGoalsAgainst();
                t2Goals++;
            }
        }
;

        //7. Overtime
        // During overtime, each team will randomly select 3 players among forwards and defensemen
        // and will randomly select one of the four goalies. The total skill level for all the 4 players
        // will be computed in each team. The team whose total skill level, computed in this manner,
        // is greater, will be the first to score one goal in overtime (and terminate the game).
        // If both totals are equal, your program will randomly decide which team scored the first overtime goal.
        boolean overtimeReached = false;
        if (t1Goals == t2Goals){
            //select index from 0 to sum of defense and forward array lengths
            overtimeReached = true;

            int t1SumOfFour = 0;
            int[] t1indices = new int[3];
            int t2SumOfFour = 0;
            int[] t2indices = new int[3];

            for (int j = 0; j < 3; j++){
                i = generator.nextInt(t1Forwards.length + t1Defense.length);
                while (inArray(t1indices, i)){
                    i = generator.nextInt(t1Forwards.length + t1Defense.length);
                }
                t1indices[j] = i;
                if (i < t1Forwards.length){
                    t1SumOfFour += t1Forwards[i].getSkillLevel();
                }
                else {
                    t1SumOfFour += t1Defense[i-t1Forwards.length].getSkillLevel();
                }
            }

            for (int j = 0; j < 3; j++){
                i = generator.nextInt(t2Forwards.length + t2Defense.length);
                while (inArray(t2indices, i)){
                    i = generator.nextInt(t2Forwards.length + t2Defense.length);
                }
                t2indices[j] = i;
                if (i < t2Forwards.length){
                    t2SumOfFour += t2Forwards[i].getSkillLevel();
                }
                else {
                    t2SumOfFour += t2Defense[i-t2Forwards.length].getSkillLevel();
                }
            }

            t1SumOfFour += t1Goalies[generator.nextInt(4)].getSkillLevel();
            t2SumOfFour += t2Goalies[generator.nextInt(4)].getSkillLevel();

            if (t1SumOfFour > t2SumOfFour){
                t1.incrementGoalsFor();
                t2.incrementGoalsAgainst();
                t1Goals++;
            }
            if (t2SumOfFour > t1SumOfFour){
                t2.incrementGoalsFor();
                t1.incrementGoalsAgainst();
                t2Goals++;
            }
            if (t2SumOfFour == t1SumOfFour){
                if(generator.nextInt(2) == 0){
                    t1.incrementGoalsFor();
                    t2.incrementGoalsAgainst();
                    t1Goals++;

                }
                else {
                    t2.incrementGoalsFor();
                    t1.incrementGoalsAgainst();
                    t2Goals++;
                }
            }
        }
        //GAME IS FINISHED.

        //update stats using t1Goals, t2Goals, and overtimeReached boolean
        //update gamesPlayed, losses, wins, overtime losses, points, difference
        //goals for and against have already been taken care of along the way

        if (!(overtimeReached)){
            //if game ended in regulation
            if (t1Goals > t2Goals){
                t1.incrementWins();
                t1.addPoints(2);
                t2.incrementLosses();
            }
            else {
                t2.incrementWins();
                t2.addPoints(2);
                t1.incrementLosses();
            }
        }
        else{
            if (t1Goals > t2Goals){
                t1.addPoints(1);
                t2.incrementOvertimeLosses();
            }
            else{
                t2.addPoints(1);
                t1.incrementOvertimeLosses();
            }
            //if overtime reached

        }
        //increment games played, update difference
        t1.incrementGamesPlayed();
        t2.incrementGamesPlayed();

        t1.updateDifference();
        t2.updateDifference();

    }
    private boolean inArray(int[] arr, int val){
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == val)
                return true;
        }
        return false;
    }
}
