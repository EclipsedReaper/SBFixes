package com.eclipse.sbf.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.eclipse.sbf.gemstone.PaneHandler.addPane;
import static com.eclipse.sbf.gemstone.PaneHandler.isPane;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
	@Inject(method = "onBlockUpdate", at = @At("TAIL"))
	private void onBlockUpdate(BlockUpdateS2CPacket packet, CallbackInfo ci) {
		ClientWorld world = MinecraftClient.getInstance().world;
		if (world != null) {
			BlockPos pos = packet.getPos();
			if (isPane(world.getBlockState(pos))) {
				addPane(pos);
			}
			if (isPane(world.getBlockState(pos.add(1, 0, 0)))) {
				addPane(pos.add(1, 0, 0));
			}
			if (isPane(world.getBlockState(pos.add(-1, 0, 0)))){
				addPane(pos.add(-1, 0, 0));
			}
			if (isPane(world.getBlockState(pos.add(0, 0, 1)))){
				addPane(pos.add(0, 0, 1));
			}
			if (isPane(world.getBlockState(pos.add(0, 0, -1)))){
				addPane(pos.add(0, 0, -1));
			}
		}
	}
}