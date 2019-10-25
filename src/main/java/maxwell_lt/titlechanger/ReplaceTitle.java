package maxwell_lt.titlechanger;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

public class ReplaceTitle {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void clientTick(TickEvent.ClientTickEvent e) {
        Replace();
    }

    public static void Replace() {
        if (!Config.WINDOW_TITLE.get().equals("")) {
            glfwSetWindowTitle(Minecraft.getInstance().mainWindow.getHandle(), processText(Config.WINDOW_TITLE.get()));
        }
    }

    private static String processText(String formatString) {
        String mcVersion = Minecraft.getInstance().getVersion();
        String modCount = Integer.toString(FMLLoader.getLoadingModList().getMods().size());
        String time = new SimpleDateFormat(Config.TIME_FORMAT.get()).format(new Date()).toString();

        formatString = formatString.replaceAll("%mcver%", mcVersion);
        formatString = formatString.replaceAll("%modcount%", modCount);
        formatString = formatString.replaceAll("%time%", time);

        return formatString;
    }
}
