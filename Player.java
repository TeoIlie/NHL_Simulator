//This class holds the information for one of the hockey players
public class Player {

    //Attributes: name, number, skillLevel, position
    private String name;
    private int number;
    private int skillLevel;
    private String position;

    //Constructor: assign the attributes
    Player(int number, int skillLevel, String position){
        char firstLetter = position.charAt(0);
        firstLetter = Character.toUpperCase(firstLetter);
        this.name = firstLetter + Integer.toString(number);

        this.number = number;
        this.skillLevel = skillLevel;
        this.position = position;
    }

    //Methods, all getter methods
    public String getName(){
        return this.name;
    }
    public int getNumber(){
        return this.number;
    }
    public int getSkillLevel(){
        return this.skillLevel;
    }
    public String getPosition(){
        return this.position;
    }
}
