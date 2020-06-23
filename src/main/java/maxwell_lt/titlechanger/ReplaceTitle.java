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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.lwjgl.glfw.GLFW.*;

public class ReplaceTitle {
    public static final String VERSION = "%mcver%";
    public static final String MOD_COUNT = "%modcount%";
    public static final String TIME = "%time%";
    public static final String PLAYER_LOCATION = "%playerloc%";
    public static final String SCORE = "%score%";
    public static final String BIOME = "%biome%";
    public static final String CHUNK = "%chunk%";

    private static final Logger LOGGER = LogManager.getLogger();
    private final InfoRetriever infoRetriever;

    private final String mcVersion;
    private final String modCount;
    private final DateFormat timeFormatter;
    private final Map<String, Supplier<String>> transformations;

    private PlayerEntity playerEntity;
    private World world;

    public ReplaceTitle() {
        this.infoRetriever = new InfoRetriever(Config.getPlaceholderText());
        this.mcVersion = Minecraft.getInstance().getVersion();
        this.modCount = Integer.toString(FMLLoader.getLoadingModList().getMods().size());
        this.timeFormatter = new SimpleDateFormat(Config.getTimeFormat());

        this.transformations = generateTransformationMap();
    }

    /**
     * Builds a map of required string replacements based on the config
     * <p>
     * This prevents unneeded information retrieval methods from running every client tick. Because the map contains
     * suppliers, the output values can vary based on current conditions.
     *
     * @return Map with patterns and replacement suppliers
     */
    private Map<String, Supplier<String>> generateTransformationMap() {
        Map<String, Supplier<String>> transformationMap = new HashMap<>();
        if (Config.getWindowTitle().contains(VERSION)) {
            transformationMap.put(VERSION, () -> mcVersion);
        }
        if (Config.getWindowTitle().contains(MOD_COUNT)) {
            transformationMap.put(MOD_COUNT, () -> modCount);
        }
        if (Config.getWindowTitle().contains(TIME)) {
            transformationMap.put(TIME, () -> timeFormatter.format(new Date()));
        }
        if (Config.getWindowTitle().contains(PLAYER_LOCATION)) {
            transformationMap.put(PLAYER_LOCATION, this::getLocation);
        }
        if (Config.getWindowTitle().contains(SCORE)) {
            transformationMap.put(SCORE, this::getScore);
        }
        if (Config.getWindowTitle().contains(BIOME)) {
            transformationMap.put(BIOME, this::getBiome);
        }
        if (Config.getWindowTitle().contains(CHUNK)) {
            transformationMap.put(CHUNK, this::getChunk);
        }
        return transformationMap;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (Config.getWindowTitle().equals("")) {
            return;
        }

        playerEntity = null;
        world = null;
        try {
            playerEntity = TitleChanger.proxy.getClientPlayer();
            world = TitleChanger.proxy.getClientWorld();
            glfwSetWindowTitle(Minecraft.getInstance().getMainWindow().getHandle(), processText(Config.getWindowTitle()));
        } catch (IllegalStateException e) {
            LOGGER.debug("Attempted to call proxy.getClientPlayer() in serverside code.");
        }
    }

    /**
     * Apply transformations to the provided format string to generate the window title
     *
     * @param formatString Format template
     * @return Final window title
     */
    private String processText(String formatString) {
        for (Map.Entry<String, Supplier<String>> entry : transformations.entrySet()) {
            formatString = formatString.replace(entry.getKey(), entry.getValue().get());
        }
        return formatString;
    }

    private String getLocation() {
        return infoRetriever.getLocation(playerEntity);
    }

    private String getScore() {
        return infoRetriever.getScore(playerEntity);
    }

    private String getBiome() {
        return infoRetriever.getBiome(playerEntity, world);
    }

    private String getChunk() {
        return infoRetriever.getChunk(playerEntity);
    }
}
