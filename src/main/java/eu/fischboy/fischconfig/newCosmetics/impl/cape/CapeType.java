package eu.fischboy.fischconfig.newCosmetics.impl.cape;

public enum CapeType {
    CLASSIC("Classic"),
    MODERN("Modern"),
    CUSTOM_COLOR("CustomColor");

    private final String name;

    CapeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
