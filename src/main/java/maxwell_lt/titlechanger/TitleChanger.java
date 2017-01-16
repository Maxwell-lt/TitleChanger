package maxwell_lt.titlechanger;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TitleChanger.MODID, version = TitleChanger.VERSION, name = TitleChanger.MODNAME, useMetadata = true, acceptedMinecraftVersions = "[1.10,1.11.2]")
public class TitleChanger
{
    public static final String MODID = "titlechanger";
    public static final String MODNAME = "Title Changer";
    public static final String VERSION = "1.0";
    
    @SidedProxy(clientSide="maxwell_lt.titlechanger.ClientProxy")
    public static CommonProxy proxy;
    
    @Mod.Instance
    public static TitleChanger instance;
    public static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }
}
