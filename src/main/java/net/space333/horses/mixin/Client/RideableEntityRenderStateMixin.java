package net.space333.horses.mixin.Client;

import net.minecraft.client.render.entity.state.CamelEntityRenderState;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import net.minecraft.client.render.entity.state.LlamaEntityRenderState;
import net.space333.horses.render.entity.state.ExtendedRideableEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Additional fields for:
// horses, donkeys, mules, undead horses, skeleton horses (LivingHorseEntityRenderState)
// llamas and camels
@Mixin(value = {LivingHorseEntityRenderState.class, LlamaEntityRenderState.class, CamelEntityRenderState.class}, priority = 960)
public abstract class RideableEntityRenderStateMixin implements ExtendedRideableEntityRenderState {

    @Unique
    private int horses$id;

    @Unique
    private boolean horses$playerPassenger;

    @Override
    public void horses$setId(int id) {
        this.horses$id = id;
    }

    @Override
    public int horses$getId() {
        return this.horses$id;
    }

    @Override
    public void horses$setPlayerPassenger(boolean playerPassenger) {
        this.horses$playerPassenger = playerPassenger;
    }

    @Override
    public boolean horses$isPlayerPassenger() {
        return horses$playerPassenger;
    }
}
