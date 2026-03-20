package enzosdev.bjjtrack.enums;

public enum Belt {
    WHITE("White"),
    BLUE("Blue"),
    PURPLE("Purple"),
    BROWN("Brown"),
    BLACK("Black"),
    CORAL("Coral"),
    RED_WHITE("Red White"),
    RED("Red");


    private final String label;

    Belt(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
