package maxwell_lt.titlechanger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(new ReplaceTitle());
        ReplaceTitle.replace();
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
