package dev.kofeychi.osc.mixin;

import dev.kofeychi.osc.FishingRodBehaviour;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;)V",ordinal = 0),method = "use")
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if(world.isClient) return;
        if(user.isSneaking()) {
            FishingRodBehaviour.execute(user.raycast(200,1,true).getPos(),(ServerWorld)world,(ServerPlayerEntity)user,hand);
        }
    }
}
