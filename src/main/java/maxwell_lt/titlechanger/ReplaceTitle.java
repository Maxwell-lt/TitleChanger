package maxwell_lt.titlechanger;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.lwjgl.glfw.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

public class ReplaceTitle {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void clientTick(TickEvent.ClientTickEvent e) {
        Replace();
    }

    public static void Replace() {
        if (!Config.WINDOW_TITLE.equals("")) {
            glfwSetWindowTitle(Minecraft.getInstance().mainWindow.getHandle(), processText(Config.WINDOW_TITLE.toString()));
        }
    }

    private static String processText(String formatString) {
        String modCount = Integer.toString(FMLLoader.getLoadingModList().getMods().size());
        String time = new SimpleDateFormat(Config.TIME_FORMAT.toString()).format(new Date()).toString();

        formatString = formatString.replaceAll("%modCount%", modCount);
        formatString = formatString.replaceAll("%time%", time);

        return formatString;
    }
}
