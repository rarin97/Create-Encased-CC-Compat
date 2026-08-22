package net.Rarin.create_connected_encased.utils;

import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import fr.iglee42.createcasing.config.EncasedConfigs;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.casings.CCasingSet;
import net.Rarin.create_connected_encased.casings.CCasingSets;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.function.Function;

@EventBusSubscriber(modid = CCEncased.ID)
public class CCEItemChangeBlockManager {

    @SubscribeEvent
    public static <T extends Comparable<T>> void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getEntity().level();
        if (event.getItemStack().isEmpty()) return;
        if (level.getBlockState(event.getPos()).isAir()) return;
        BlockState state = level.getBlockState(event.getPos());
        CCasingSet casingSet;
        if ((casingSet = getSetForCasing(event.getItemStack().getItem())) != null && EncasedConfigs.common().kinetics.casingBlockSwappable.get()) {
            if (casingSet.isInSet(state.getBlock())) return;
            if (isElementInSet(state, CCasingSet::getChainCogwheel) && casingSet.getChainCogwheel() != null)
                changeAxisBlock(event, state, level, casingSet.getChainCogwheel().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getParallelGearbox) && casingSet.getParallelGearbox() != null)
                changeAxisBlock(event, state, level, casingSet.getParallelGearbox().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getSixWayGearbox) && casingSet.getSixWayGearbox() != null)
                changeAxisBlock(event, state, level, casingSet.getSixWayGearbox().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getBrake) && casingSet.getBrake() != null)
                changeAxisBlock(event, state, level, casingSet.getBrake().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getInvertedGearShift) && casingSet.getInvertedGearShift() != null)
                changeAxisBlock(event, state, level, casingSet.getInvertedGearShift().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getInvertedClutch) && casingSet.getInvertedClutch() != null)
                changeAxisBlock(event, state, level, casingSet.getInvertedClutch().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getCentrifugalClutch) && casingSet.getCentrifugalClutch() != null)
                changeFacingBlock(event, state, level, casingSet.getCentrifugalClutch().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getFreewheelClutch) && casingSet.getFreewheelClutch() != null)
                changeFacingBlock(event, state, level, casingSet.getFreewheelClutch().defaultBlockState());
            if (isElementInSet(state, CCasingSet::getOverstressClutch) && casingSet.getOverstressClutch() != null)
                changeAxisBlock(event, state, level, casingSet.getOverstressClutch().defaultBlockState());

        }
    }

    private static void changeBlock(PlayerInteractEvent.RightClickBlock event, BlockState state, Level level, BlockState newBlock) {
        level.setBlockAndUpdate(event.getPos(), newBlock);
        level.levelEvent(2001, event.getPos(), Block.getId(newBlock));
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    private static void changeAxisBlock(PlayerInteractEvent.RightClickBlock event, BlockState state, Level level, BlockState newBlock) {
        if (state.getBlock() instanceof RotatedPillarKineticBlock) {
            Direction.Axis axis = (Direction.Axis)state.getValue(RotatedPillarKineticBlock.AXIS);
            changeBlock(event, state, level, (BlockState)newBlock.setValue(RotatedPillarKineticBlock.AXIS, axis));
        }
    }

    private static void changeFacingBlock(PlayerInteractEvent.RightClickBlock event, BlockState state, Level level, BlockState newBlock) {
        if (state.hasProperty(BlockStateProperties.FACING)) {
            Direction direction = (Direction)state.getValue(BlockStateProperties.FACING);
            changeBlock(event, state, level, (BlockState)newBlock.setValue(BlockStateProperties.FACING, direction));
        }
    }

        private static CCasingSet getSetForCasing(Item casing){
            return CCasingSets.getSets().stream()
                    .filter(set->set.getCasing() != null)
                    .filter(set->set.getCasing().asItem().equals(casing))
                    .findFirst()
                    .orElse(null);
        }

    public static boolean isElementInSet(BlockState state, Function<CCasingSet, Block> function) {
        return CCasingSets.getSets().stream().filter((set) -> function.apply(set) != null).anyMatch((set) -> state.getBlock().equals(function.apply(set)));
    }
}
