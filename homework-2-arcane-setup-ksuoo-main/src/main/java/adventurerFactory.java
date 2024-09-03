public class adventurerFactory {
    public adventurer create (String name, String type) {
        return switch (type) {
            case "glutton" -> (new glutton(name));
            case "knight" -> (new knight(name));
            case "coward" -> (new coward(name));
            case "default" -> (new adventurer(name, 5, ""));
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
