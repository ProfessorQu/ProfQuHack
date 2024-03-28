package net.professorqu.modules;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class XRay extends Hack {
    private static final List<Block> interestingBlocks = new ArrayList<>();

    public XRay() {
        // Add stone ores
        interestingBlocks.add(Blocks.COAL_ORE);
        interestingBlocks.add(Blocks.IRON_ORE);
        interestingBlocks.add(Blocks.GOLD_ORE);
        interestingBlocks.add(Blocks.COPPER_ORE);
        interestingBlocks.add(Blocks.EMERALD_ORE);
        interestingBlocks.add(Blocks.LAPIS_ORE);
        interestingBlocks.add(Blocks.DIAMOND_ORE);
        interestingBlocks.add(Blocks.REDSTONE_ORE);

        // Add deepslate ores
        interestingBlocks.add(Blocks.DEEPSLATE_COAL_ORE);
        interestingBlocks.add(Blocks.DEEPSLATE_IRON_ORE);
        interestingBlocks.add(Blocks.DEEPSLATE_GOLD_ORE);
        interestingBlocks.add(Blocks.DEEPSLATE_COPPER_ORE);
        interestingBlocks.add(Blocks.DEEPSLATE_EMERALD_ORE);
        interestingBlocks.add(Blocks.DEEPSLATE_LAPIS_ORE);
        interestingBlocks.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        interestingBlocks.add(Blocks.DEEPSLATE_REDSTONE_ORE);
    }

    @Override
    public void toggle() {
        super.toggle();

        // Reloading rendering or XRay won't work on already loaded chunks
        MinecraftClient.getInstance().worldRenderer.reload();
    }

    /**
     * Determines if the block {@code block} is interesting, or see able with X-ray
     * @param block the block to check
     * @return      true if the block is interesting and should be seen with X-ray
     */
    public static boolean isInteresting(Block block) {
        return interestingBlocks.contains(block);
    }
}
