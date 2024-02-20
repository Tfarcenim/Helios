package tfar.helios;

public class HeliosEvent {

    public static final WeightedList<HeliosEvent> EVENTS = new WeightedList<>();

    public static final HeliosEvent SOLAR_ECLIPSE = new HeliosEvent("solar_eclipse");
    public static final HeliosEvent BAD_SUN = new HeliosEvent("bad_sun");
    public static final HeliosEvent COOL_SUN = new HeliosEvent("cool_sun");
    public static final HeliosEvent NOTHING = new HeliosEvent("nothing");
    private final String name;

    public HeliosEvent(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public static void setupConfig() {
        EVENTS.clear();
        EVENTS.addEntry(SOLAR_ECLIPSE,HeliosConfig.SOLAR_ECLIPSE_WEIGHT);
        EVENTS.addEntry(BAD_SUN,HeliosConfig.BAD_SUN_WEIGHT);
        EVENTS.addEntry(COOL_SUN,HeliosConfig.COOL_SUN_WEIGHT);
        EVENTS.addEntry(NOTHING,HeliosConfig.NOTHING_WEIGHT);
    }
}
