package com.eclipse.sbf.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager {

    @Shadow
    public abstract void clickSlot(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player);

    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    private void onClick(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        ItemStack cursorStack = player.currentScreenHandler.getCursorStack();
        if (cursorStack.getCount() == 1 && actionType == SlotActionType.QUICK_CRAFT) {
            int dragStage = button % 4;
            if (dragStage == 1 && slotId != -999) {
                ci.cancel();
                this.clickSlot(syncId, slotId, 0, SlotActionType.PICKUP, player);
            } else {
                ci.cancel();
            }
        }
    }
}