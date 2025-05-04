package net.space333.horses.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Enchantment.class)
public class EnchantHorseArmorMixin {
    @ModifyVariable(method = "slotMatches", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
    private EquipmentSlot feetEnchantsOnHorse(EquipmentSlot slot){
        if (slot==EquipmentSlot.BODY) {
            return EquipmentSlot.FEET;
        }
        return slot;
    }
}
