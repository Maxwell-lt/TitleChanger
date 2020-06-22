package maxwell_lt.titlechanger.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InfoRetriever {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String placeholderText;

    public InfoRetriever(String placeholderText) {
        this.placeholderText = placeholderText;
    }

    public String getBiome(PlayerEntity playerEntity, World world) {
        if (playerEntity != null && world != null) {
            Vec3d pos = playerEntity.getPositionVec();
            Biome biome = world.getBiome(new BlockPos(pos.x, pos.y, pos.z));
            return biome.getDisplayName().getString();
        } else {
            return placeholderText;
        }
    }

    public String getScore(PlayerEntity playerEntity) {
        if (playerEntity != null) {
            return Integer.toString(playerEntity.getScore());
        } else {
            return placeholderText;
        }
    }

    public String getLocation(PlayerEntity playerEntity) {
        String posX;
        String posY;
        String posZ;

        if (playerEntity != null) {
            Vec3d pos = playerEntity.getPositionVector();
            posX = String.format("%.0f", pos.x);
            posY = String.format("%.0f", pos.y);
            posZ = String.format("%.0f", pos.z);
        } else {
            posX = posY = posZ = placeholderText;
        }
        return String.format("%s %s %s", posX, posY, posZ);
    }

    public String getChunk(PlayerEntity playerEntity) {
        String chunkX;
        String chunkY;
        String chunkZ;

        if (playerEntity != null) {
            chunkX = String.format("%d", playerEntity.chunkCoordX);
            chunkY = String.format("%d", playerEntity.chunkCoordY);
            chunkZ = String.format("%d", playerEntity.chunkCoordZ);
        } else {
            chunkX = chunkY = chunkZ = placeholderText;
        }
        return String.format("%s %s %s", chunkX, chunkY, chunkZ);
    }
}
