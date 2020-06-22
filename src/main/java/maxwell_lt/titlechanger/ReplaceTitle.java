package maxwell_lt.titlechanger;

import maxwell_lt.titlechanger.config.Config;
import maxwell_lt.titlechanger.util.InfoRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.lwjgl.glfw.GLFW.*;

public class ReplaceTitle {
    private static final Logger LOGGER = LogManager.getLogger();
    private final InfoRetriever infoRetriever;
    private final String mcVersion;
    private final String modCount;

    public ReplaceTitle() {
        this.infoRetriever = new InfoRetriever(Config.PLACEHOLDER_TEXT.get());
        this.mcVersion = Minecraft.getInstance().getVersion();
        this.modCount = Integer.toString(FMLLoader.getLoadingModList().getMods().size());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void clientTick(TickEvent.ClientTickEvent e) {
        replace();
    }

    public void replace() {
        if (!Config.WINDOW_TITLE.get().equals("")) {
            glfwSetWindowTitle(Minecraft.getInstance().getMainWindow().getHandle(), processText(Config.WINDOW_TITLE.get()));
        }
    }

    private String processText(String formatString) {
        PlayerEntity playerEntity = null;
        World world = null;
        try {
            playerEntity = TitleChanger.proxy.getClientPlayer();
            world = TitleChanger.proxy.getClientWorld();
        } catch (IllegalStateException e) {
            LOGGER.debug("Attempted to call proxy.getClientPlayer() in serverside code.");
        }

        String time = new SimpleDateFormat(Config.TIME_FORMAT.get()).format(new Date());
        String location = infoRetriever.getLocation(playerEntity);
        String score = infoRetriever.getScore(playerEntity);
        String biome = infoRetriever.getBiome(playerEntity, world);
        String chunk = infoRetriever.getChunk(playerEntity);

        formatString = formatString.replace("%mcver%", mcVersion);
        formatString = formatString.replace("%modcount%", modCount);
        formatString = formatString.replace("%time%", time);
        formatString = formatString.replace("%playerloc%", location);
        formatString = formatString.replace("%score%", score);
        formatString = formatString.replace("%biome%", biome);
        formatString = formatString.replace("%chunk%", chunk);

        return formatString;
    }

}
