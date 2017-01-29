package maxwell_lt.titlechanger;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReplaceTitle {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void clientTick(TickEvent.ClientTickEvent e) {
        Replace();
    }

    public static void Replace() {
        if (Config.windowTitle != "") {
            Display.setTitle(processText(Config.windowTitle));
        }
    }

    public static String processText(String formatString) {
        String mcVersion = Loader.instance().getMinecraftModContainer().getVersion();
        String modCount = Integer.toString(Loader.instance().getModList().size());
        String time = new SimpleDateFormat(Config.timeFormat).format(new Date()).toString();

        formatString = formatString.replaceAll("%mcver%", mcVersion);
        formatString = formatString.replaceAll("%modcount%", modCount);
        formatString = formatString.replaceAll("%time%", time);

        return formatString;
    }
}