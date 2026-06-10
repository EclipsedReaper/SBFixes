package com.eclipse.sbf;

import com.eclipse.sbf.gemstone.PaneHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class SBFixes implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			PaneHandler.handlePanes();
		});
	}
}