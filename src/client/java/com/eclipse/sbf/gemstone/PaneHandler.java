package com.eclipse.sbf.gemstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class PaneHandler {
    private static final Set<Block> GEMSTONE_PANES = Set.of(
            Blocks.GLASS_PANE, Blocks.PINK_STAINED_GLASS_PANE, Blocks.PURPLE_STAINED_GLASS_PANE,
            Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE,
            Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
            Blocks.LIME_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS_PANE,
            Blocks.RED_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE
    );
    private static final Set<BlockPos> panes = new HashSet<>();

    public static void handlePanes() {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world != null) {
            for (BlockPos pos : panes) {
                if (!isPane(world.getBlockState(pos))) continue;
                boolean xup = !world.getBlockState(pos.add(1, 0, 0)).isSolid();
                boolean xdown = !world.getBlockState(pos.add(-1, 0, 0)).isSolid();
                boolean zup = !world.getBlockState(pos.add(0, 0, 1)).isSolid();
                boolean zdown = !world.getBlockState(pos.add(0, 0, -1)).isSolid();
                BlockState newState;
                if (xup && xdown && zup && zdown) {
                    newState = world.getBlockState(pos)
                            .with(PaneBlock.NORTH, true)
                            .with(PaneBlock.SOUTH, true)
                            .with(PaneBlock.EAST, true)
                            .with(PaneBlock.WEST, true);
                } else {
                    newState = world.getBlockState(pos)
                            .with(PaneBlock.NORTH, !zdown)
                            .with(PaneBlock.SOUTH, !zup)
                            .with(PaneBlock.EAST, !xup)
                            .with(PaneBlock.WEST, !xdown);
                }
                world.setBlockState(pos, newState, 19);
            }
            panes.clear();
        }
    }

    public static boolean isPane(BlockState blockState) {
        return GEMSTONE_PANES.contains(blockState.getBlock());
    }

    public static void addPane(BlockPos pos) {
        panes.add(pos);
    }
}
