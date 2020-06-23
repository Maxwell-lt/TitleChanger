package maxwell_lt.titlechanger.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class InfoRetrieverTest {
    static final String DEFAULT_PLACEHOLDER = "--";

    InfoRetriever infoRetriever;

    @BeforeEach
    void setUp() {
        infoRetriever = new InfoRetriever(DEFAULT_PLACEHOLDER);
    }

    @Test
    void testGetScoreWhenPlayerEntityNotNull() {
        PlayerEntity playerEntity = mock(PlayerEntity.class);
        when(playerEntity.getScore())
                .thenReturn(125);
        assertThat(infoRetriever.getScore(playerEntity))
                .isEqualTo("125");
    }

    @Test
    void testGetScoreWhenPlayerEntityIsNull() {
        assertThat(infoRetriever.getScore(null))
                .isEqualTo(DEFAULT_PLACEHOLDER);
    }

    @Test
    void testGetLocation() {
        PlayerEntity playerEntity = mock(PlayerEntity.class);
        when(playerEntity.getPositionVector())
                .thenReturn(new Vec3d(1024D, 63D, 1024D));
        assertThat(infoRetriever.getLocation(playerEntity))
                .isEqualTo("1024 63 1024");
    }

    @Test
    void testGetLocationWhenNegative() {
        PlayerEntity playerEntity = mock(PlayerEntity.class);
        when(playerEntity.getPositionVector())
                .thenReturn(new Vec3d(-1024D, 255D, -1024D));
        assertThat(infoRetriever.getLocation(playerEntity))
                .isEqualTo("-1024 255 -1024");
    }

    @Test
    void testGetLocationWithDecimalComponent() {
        PlayerEntity playerEntity = mock(PlayerEntity.class);
        when(playerEntity.getPositionVector())
                .thenReturn(new Vec3d(5.12345D, 63.33339D, -4.5000001));
        assertThat(infoRetriever.getLocation(playerEntity))
                .isEqualTo("5 63 -5");
    }

    @Test
    void testGetLocationWhenPlayerEntityIsNull() {
        assertThat(infoRetriever.getLocation(null))
                .isEqualTo(String.format(
                        "%s %s %s",
                        DEFAULT_PLACEHOLDER,
                        DEFAULT_PLACEHOLDER,
                        DEFAULT_PLACEHOLDER));
    }

    @Test
    void testGetChunk() {
        PlayerEntity playerEntity = mock(PlayerEntity.class);
        playerEntity.chunkCoordX = -5;
        playerEntity.chunkCoordY = 3;
        playerEntity.chunkCoordZ = 15;

        assertThat(infoRetriever.getChunk(playerEntity))
                .isEqualTo("-5 3 15");
    }

    @Test
    void testGetChunkWhenPlayerEntityIsNull() {
        assertThat(infoRetriever.getChunk(null))
                .isEqualTo(String.format(
                        "%s %s %s",
                        DEFAULT_PLACEHOLDER,
                        DEFAULT_PLACEHOLDER,
                        DEFAULT_PLACEHOLDER));
    }

    @Test
    void testGetBiomeWhenPlayerEntityIsNull() {
        World world = mock(World.class);
        assertThat(infoRetriever.getBiome(null, world))
                .isEqualTo(DEFAULT_PLACEHOLDER);
    }

    @Test
    void testGetBiomeWhenWorldIsNull() {
        PlayerEntity playerEntity = mock(PlayerEntity.class);
        assertThat(infoRetriever.getBiome(playerEntity, null))
                .isEqualTo(DEFAULT_PLACEHOLDER);
    }

    @Test
    void testGetBiome() {
        PlayerEntity playerEntity = mock(PlayerEntity.class);
        World world = mock(World.class);
        Biome biome = mock(Biome.class);
        ITextComponent biomeName = new TranslationTextComponent("Beach");
        Vec3d vecCoords = new Vec3d(0D, 63D, 0D);
        BlockPos blockCoords = new BlockPos(vecCoords);

        when(playerEntity.getPositionVector()).thenReturn(vecCoords);
        when(world.getBiome(eq(blockCoords))).thenReturn(biome);
        when(biome.getDisplayName()).thenReturn(biomeName);

        assertThat(infoRetriever.getBiome(playerEntity, world))
                .isEqualTo("Beach");
    }
}