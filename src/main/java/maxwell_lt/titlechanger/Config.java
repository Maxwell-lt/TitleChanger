package maxwell_lt.titlechanger;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Config {

    private static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder GENERAL_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.ConfigValue<String> WINDOW_TITLE;
    public static ForgeConfigSpec.ConfigValue<String> TIME_FORMAT;

    public static ForgeConfigSpec GENERAL_CONFIG;

    static {
        GENERAL_BUILDER.comment("General Configuration").push(CATEGORY_GENERAL);
        WINDOW_TITLE = GENERAL_BUILDER
                .comment("The title of the Minecraft window. Leave blank to keep the default window title for your version of Minecraft.\nSome special values that will be inserted at runtime:\n%mcver% -> The current Minecraft version\n%modcount% -> Number of loaded mods\n%time% -> Current system time\n")
                .define("windowtitle", "");
        TIME_FORMAT = GENERAL_BUILDER
                .comment("Format to display time in. See http://www.unicode.org/reports/tr35/tr35-31/tr35-dates.html#Date_Format_Patterns")
                .define("timeformat", "h:mm a");
        GENERAL_BUILDER.pop();

        GENERAL_CONFIG = GENERAL_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.ConfigReloading configEvent) {

    }
}
