package com.eclipse.sbf.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.eclipse.sbf.gemstone.PaneHandler.addPane;
import static com.eclipse.sbf.gemstone.PaneHandler.isPane;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
	@Inject(method = "onBlockUpdate", at = @At("TAIL"))
	private void onBlockUpdate(ClientboundBlockUpdatePacket packet, CallbackInfo ci) {
		ClientLevel world = Minecraft.getInstance().level;
		if (world != null) {
			BlockPos pos = packet.getPos();
			if (isPane(world.getBlockState(pos))) {
				addPane(pos);
			}
			if (isPane(world.getBlockState(pos.east()))) {
				addPane(pos.east());
			}
			if (isPane(world.getBlockState(pos.west()))){
				addPane(pos.west());
			}
			if (isPane(world.getBlockState(pos.south()))){
				addPane(pos.south());
			}
			if (isPane(world.getBlockState(pos.north()))){
				addPane(pos.north());
			}
		}
	}
}