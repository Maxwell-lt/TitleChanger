package maxwell_lt.titlechanger;

import org.lwjgl.opengl.Display;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		
		if (Config.windowTitle != "") Display.setTitle(Config.windowTitle);
	}

}
