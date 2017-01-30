package maxwell_lt.titlechanger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		MinecraftForge.EVENT_BUS.register(new ReplaceTitle());

		ReplaceTitle.Replace();
	}

}
