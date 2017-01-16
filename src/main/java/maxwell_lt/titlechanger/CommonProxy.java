package maxwell_lt.titlechanger;

import java.io.File;

import maxwell_lt.titlechanger.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public static Configuration config;
	
	public void preInit(FMLPreInitializationEvent e) {
		File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "titlechanger.cfg"));
        Config.readConfig();
	}

}
