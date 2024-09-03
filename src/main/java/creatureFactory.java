public class creatureFactory {
    public creature create(String name, String type) {
        if (type.equals("demon")) {
            return (new demon(name));
        }
        return (new creature(name, 3, "default"));
    }
}
