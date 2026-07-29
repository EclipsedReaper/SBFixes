package com.eclipse.sbf.gemstone;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

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
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null) {
            for (BlockPos pos : panes) {
                if (!isPane(world.getBlockState(pos))) continue;
                boolean east = !world.getBlockState(pos.east()).isSolid();
                boolean west = !world.getBlockState(pos.west()).isSolid();
                boolean south = !world.getBlockState(pos.south()).isSolid();
                boolean north = !world.getBlockState(pos.north()).isSolid();
                BlockState newState;
                if (east && west && south && north) {
                    newState = world.getBlockState(pos)
                            .setValue(StainedGlassPaneBlock.NORTH, true)
                            .setValue(StainedGlassPaneBlock.SOUTH, true)
                            .setValue(StainedGlassPaneBlock.EAST, true)
                            .setValue(StainedGlassPaneBlock.WEST, true);
                } else {
                    newState = world.getBlockState(pos)
                            .setValue(StainedGlassPaneBlock.NORTH, !north)
                            .setValue(StainedGlassPaneBlock.SOUTH, !south)
                            .setValue(StainedGlassPaneBlock.EAST, !east)
                            .setValue(StainedGlassPaneBlock.WEST, !west);
                }
                world.setServerVerifiedBlockState(pos, newState, 19);
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
