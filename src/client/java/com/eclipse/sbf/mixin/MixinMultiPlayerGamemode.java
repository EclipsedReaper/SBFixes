package com.eclipse.sbf.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGamemode {

    @Shadow
    public abstract void clickSlot(int syncId, int slotId, int button, ContainerInput input, Player player);

    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    private void onClick(int syncId, int slotId, int button, ContainerInput input, Player player, CallbackInfo ci) {
        ItemStack cursorStack = player.containerMenu.getCarried();
        if (cursorStack.getCount() == 1 && input == ContainerInput.QUICK_CRAFT) {
            int dragStage = button % 4;
            if (dragStage == 1 && slotId != -999) {
                ci.cancel();
                this.clickSlot(syncId, slotId, 0, ContainerInput.PICKUP, player);
            } else {
                ci.cancel();
            }
        }
    }
}