package tfar.helios;

import net.minecraftforge.common.config.Config;

@Config(modid = Helios.MODID)
public class HeliosConfig {

    @Config.RangeInt(min = 0)
    public static int NOTHING_WEIGHT = 1;
    @Config.RangeInt(min = 0)
    public static int SOLAR_ECLIPSE_WEIGHT = 1;
    @Config.RangeInt(min = 0)
    public static int BAD_SUN_WEIGHT = 1;
    @Config.RangeInt(min = 0)
    public static int COOL_SUN_WEIGHT = 1;
}
