package maxwell_lt.titlechanger.util;

import net.minecraft.entity.player.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

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

}