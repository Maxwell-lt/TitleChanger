package maxwell_lt.titlechanger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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
        replace();
    }

    public static void replace() {
        if (!Config.WINDOW_TITLE.get().equals("")) {
            glfwSetWindowTitle(Minecraft.getInstance().getMainWindow().getHandle(), processText(Config.WINDOW_TITLE.get()));
        }
    }

    private static String processText(String formatString) {
        String mcVersion = Minecraft.getInstance().getVersion();
        String modCount = Integer.toString(FMLLoader.getLoadingModList().getMods().size());
        String time = new SimpleDateFormat(Config.TIME_FORMAT.get()).format(new Date());
        String location = getPlayerLocationOrPlaceholder();
        String score = getPlayerScoreOrPlaceholder();
        String biome = getPlayerBiomeOrPlaceholder();
        String chunk = getPlayerChunkOrPlaceholder();

        formatString = formatString.replaceAll("%mcver%", mcVersion);
        formatString = formatString.replaceAll("%modcount%", modCount);
        formatString = formatString.replaceAll("%time%", time);
        formatString = formatString.replaceAll("%playerloc%", location);
        formatString = formatString.replaceAll("%score%", score);
        formatString = formatString.replaceAll("%biome%", biome);
        formatString = formatString.replaceAll("%chunk%", chunk);

        return formatString;
    }

    private static String getPlayerBiomeOrPlaceholder() {
        PlayerEntity playerEntity = null;
        World world = null;
        try {
            playerEntity = TitleChanger.proxy.getClientPlayer();
            world = TitleChanger.proxy.getClientWorld();
        } catch (IllegalStateException e) {
            LOGGER.debug("Attempted to call proxy.getClientPlayer() in serverside code.");
        }
        if (playerEntity != null && world != null) {
            Vec3d pos = playerEntity.getPositionVec();
            Biome biome = world.getBiome(new BlockPos(pos.x, pos.y, pos.z));
            return biome.getDisplayName().getString();
        } else {
            return Config.PLACEHOLDER_TEXT.get();
        }
    }

    private static String getPlayerScoreOrPlaceholder() {
        PlayerEntity playerEntity = null;
        try {
            playerEntity = TitleChanger.proxy.getClientPlayer();
        } catch (IllegalStateException e) {
            LOGGER.debug("Attempted to call proxy.getClientPlayer() in serverside code.");
        }
        if (playerEntity != null) {
            return Integer.toString(playerEntity.getScore());
        } else {
            return Config.PLACEHOLDER_TEXT.get();
        }
    }

    private static String getPlayerLocationOrPlaceholder() {
        PlayerEntity playerEntity = null;
        String posX, posY, posZ;
        try {
            playerEntity = TitleChanger.proxy.getClientPlayer();
        } catch (IllegalStateException e) {
            LOGGER.debug("Attempted to call proxy.getClientPlayer() in serverside code.");
        }
        if (playerEntity != null) {
            Vec3d pos = playerEntity.getPositionVector();
            posX = String.format("%.0f", pos.x);
            posY = String.format("%.0f", pos.y);
            posZ = String.format("%.0f", pos.z);
        } else {
            posX = posY = posZ = Config.PLACEHOLDER_TEXT.get();
        }
        return String.format("%s %s %s", posX, posY, posZ);
    }

    private static String getPlayerChunkOrPlaceholder() {
        PlayerEntity playerEntity = null;
        String chunkX, chunkY, chunkZ;
        try {
            playerEntity = TitleChanger.proxy.getClientPlayer();
        } catch (IllegalStateException e) {
            LOGGER.debug("Attempted to call proxy.getClientPlayer() in serverside code.");
        }
        if (playerEntity != null) {
            chunkX = String.format("%d", playerEntity.chunkCoordX);
            chunkY = String.format("%d", playerEntity.chunkCoordY);
            chunkZ = String.format("%d", playerEntity.chunkCoordZ);
        } else {
            chunkX = chunkY = chunkZ = Config.PLACEHOLDER_TEXT.get();
        }
        return String.format("%s %s %s", chunkX, chunkY, chunkZ);
    }
}
