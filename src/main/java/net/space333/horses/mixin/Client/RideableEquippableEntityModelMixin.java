package net.space333.horses.mixin.Client;

import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import net.space333.horses.render.entity.model.ExtendedRideableEquippableEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Horses can equip armor and llamas can equip carpets
@Mixin(value = {HorseEntityModel.class, LlamaEntityModel.class}, priority = 960)
public abstract class RideableEquippableEntityModelMixin implements ExtendedRideableEquippableEntityModel {

    @Unique
    private boolean horses$playerPassenger;

    @Override
    public void horses$setPlayerPassenger(boolean playerPassenger) {
        this.horses$playerPassenger = playerPassenger;
    }

    @Override
    public boolean horses$isPlayerPassenger() {
        return this.horses$playerPassenger;
    }
}
