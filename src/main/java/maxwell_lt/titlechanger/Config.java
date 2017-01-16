package maxwell_lt.titlechanger;

import org.apache.logging.log4j.Level;
import net.minecraftforge.common.config.Configuration;

public class Config {

	private static final String CATEGORY_GENERAL = "general";
	
	public static String windowTitle = "";
	
	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception exception) {
			TitleChanger.logger.log(Level.ERROR, "Problem loading config file!", exception);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
		windowTitle = cfg.getString("windowtitle", CATEGORY_GENERAL, "", "The title of the Minecraft window. Leave blank to keep the default window title for your version of Minecraft.");
	}

}
