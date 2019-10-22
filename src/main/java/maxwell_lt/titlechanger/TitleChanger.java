package maxwell_lt.titlechanger;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TitleChanger.MODID, useMetadata = true, acceptedMinecraftVersions = "[1.13.2]", updateJSON = "https://raw.githubusercontent.com/Maxwell-lt/TitleChanger/master/update.json")
public class TitleChanger
{
    public static final String MODID = "titlechanger";
    
    @SidedProxy(clientSide="maxwell_lt.titlechanger.ClientProxy", serverSide="maxwell_lt.titlechanger.ServerProxy")
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
