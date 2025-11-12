package com.example.minersdreammod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MinersDreamItem extends Item {
    
    private static final TagKey<Block> ORES_TAG = BlockTags.create(ResourceLocation.fromNamespaceAndPath("forge", "ores"));

    public MinersDreamItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        ItemStack itemStack = pContext.getItemInHand();

        // --- Configuration ---
        // We are NOT using durability, but we must consume the item if it's not a tool
        // int durabilityCost = 1; // Removed
        // int cooldownTicks = 100; // <-- REMOVED THIS

        if (!pLevel.isClientSide && pPlayer != null) {
            
            Direction facing = pContext.getHorizontalDirection();
            Direction right = facing.getClockWise();
            BlockPos playerFeetPos = pPlayer.getOnPos(); // Block UNDER your feet
            
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (int l = 1; l <= 65; l++) { // Length
                for (int w = -5; w <= 5; w++) { // Width (11 wide)
                    for (int h = 1; h <= 5; h++) { // Height (5 tall)
                        
                        mutablePos.set(playerFeetPos)
                                  .move(facing, l)
                                  .move(right, w)
                                  .move(Direction.UP, h);
                        
                        BlockState blockState = pLevel.getBlockState(mutablePos);

                        if (blockState.isAir()) {
                            continue;
                        }
                        
                        boolean isTorchSpot = (w == 0) && (h == 1) && ((l - 1) % 5 == 0);
                        
                        if (isTorchSpot) {
                            BlockState floorBlockState = pLevel.getBlockState(mutablePos.below());
                            if (floorBlockState.isSolid()) {
                                pLevel.setBlock(mutablePos, Blocks.REDSTONE_TORCH.defaultBlockState(), 3);
                            } else {
                                pLevel.destroyBlock(mutablePos, false);
                            }
                        } else {
                            boolean isOre = blockState.is(ORES_TAG);

                            if (!isOre) {
                                pLevel.destroyBlock(mutablePos, false);
                            }
                        }
                    }
                }
            }

            // --- THIS IS THE FIX ---
            // We must shrink the stack by 1 since it's a consumable item, not a tool
            if (!pPlayer.getAbilities().instabuild) { // Don't consume in creative mode
                itemStack.shrink(1);
            }

            // pPlayer.getCooldowns().addCooldown(this.asItem(), cooldownTicks); // <-- REMOVED THIS

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
