package maxwell_lt.titlechanger;

import net.minecraftforge.fml.common.Loader;

public class TextProcessor {
    public static String replace(String input) {
        String mcVersion = Loader.instance().getMinecraftModContainer().getVersion();

        input.replaceAll("%mcver%", mcVersion);

        return input;
    }
}