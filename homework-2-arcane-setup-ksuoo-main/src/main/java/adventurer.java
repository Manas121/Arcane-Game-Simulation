public class adventurer extends entity {
    String type;
    adventurer(String name, double health, String type) {
        super(name,health);
        this.type = type;

    }

    public String printStatus() {
        if (this.health > 0 ){
            return (this.name + "(health: " + this.health + ")");
        } else {
            return (this.name + "(health: " + this.health + "); DEAD");
        }
    }



}
