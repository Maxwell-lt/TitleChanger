package maxwell_lt.titlechanger.config;

import maxwell_lt.titlechanger.TitleChanger;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = TitleChanger.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ClientConfig CLIENT_CONFIG;
    public static final ForgeConfigSpec CLIENT_SPEC;
    private static String windowTitle;
    private static String timeFormat;
    private static String placeholderText;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT_CONFIG = specPair.getLeft();
    }

    public static void bakeConfig() {
        windowTitle = CLIENT_CONFIG.WINDOW_TITLE.get();
        timeFormat = CLIENT_CONFIG.TIME_FORMAT.get();
        placeholderText = CLIENT_CONFIG.PLACEHOLDER_TEXT.get();
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == Config.CLIENT_SPEC) {
            bakeConfig();
        }
    }

    public static String getWindowTitle() {
        return windowTitle;
    }

    public static String getTimeFormat() {
        return timeFormat;
    }

    public static String getPlaceholderText() {
        return placeholderText;
    }
}
