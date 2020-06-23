package maxwell_lt.titlechanger.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    private static final String CATEGORY_GENERAL = "general";

    public final ForgeConfigSpec.ConfigValue<String> WINDOW_TITLE;
    public final ForgeConfigSpec.ConfigValue<String> TIME_FORMAT;
    public final ForgeConfigSpec.ConfigValue<String> PLACEHOLDER_TEXT;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General Configuration").push(CATEGORY_GENERAL);
        WINDOW_TITLE = builder
                .comment("The title of the Minecraft window. Leave blank to keep the default window title for your version of Minecraft." +
                        "\nSome special values that will be inserted at runtime:" +
                        "\n%mcver% -> The current Minecraft version" +
                        "\n%modcount% -> Number of loaded mods" +
                        "\n%time% -> Current system time" +
                        "\n%playerloc% -> Location of the player, if available" +
                        "\n%chunk% -> Current chunk, if available" +
                        "\n%biome% -> Current biome, if available" +
                        "\n%score% -> Current score of the player, if available\n")
                .define("windowtitle", "");
        TIME_FORMAT = builder
                .comment("Format to display time in. See http://www.unicode.org/reports/tr35/tr35-31/tr35-dates.html#Date_Format_Patterns")
                .define("timeformat", "h:mm a");
        PLACEHOLDER_TEXT = builder
                .comment("String to use as placeholder when a value is unavailable.")
                .define("placeholdertext", "--");
        builder.pop();
    }
}
