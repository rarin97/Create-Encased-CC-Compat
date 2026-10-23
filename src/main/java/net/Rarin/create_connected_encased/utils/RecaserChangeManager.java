package net.Rarin.create_connected_encased.utils;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import net.Rarin.create_connected_encased.sets.SetBase;
import net.Rarin.create_connected_encased.sets.casings.CCasingSet;
import net.Rarin.create_connected_encased.sets.casings.CCasingSets;
import net.Rarin.create_connected_encased.sets.fluids.CFluidSet;
import net.Rarin.create_connected_encased.sets.fluids.CFluidSets;
import net.Rarin.create_connected_encased.sets.transmissions.CTransmissionSet;
import net.Rarin.create_connected_encased.sets.transmissions.CTransmissionSets;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.Rarin.create_connected_encased.utils.RecaserBlockChanger.changeBlock;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class RecaserChangeManager {

    public static final HashMap<Item, List<SetBase<?, ?>>> ITEM_SETS_CACHE = new HashMap<>();

    public record CCEAmmo(ItemStack stack, List<SetBase<?, ?>> sets) {
    }

    public static InteractionResult tryChangeBlock(Level level, BlockPos pos, BlockState state, Player player, CCEAmmo ammo) {
        SetBase<?, ?> blockSet = getSetForBlock(state.getBlock());

        if (blockSet == null)
            return InteractionResult.PASS;

        if (ammo.sets().contains(blockSet))
            return InteractionResult.PASS;

        SetBase<?, ?> newSet = ammo.sets()
                .stream()
                .filter(set -> set.getClass().equals(blockSet.getClass()))
                .findFirst()
                .orElse(null);

        if (newSet == null)
            return InteractionResult.PASS;

        RecaserBlockChanger changer = new RecaserBlockChanger();
        InteractionResult tankResult = null;

        if (newSet instanceof CCasingSet newCSet && blockSet instanceof CCasingSet oldCSet) {
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getChainCogwheel, AXIS);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getBrake, AXIS);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getParallelGearbox, AXIS);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getSixWayGearbox, AXIS);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getInvertedGearShift, AXIS);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getInvertedClutch, AXIS);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getCentrifugalClutch, FACING);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getFreewheelClutch, FACING);
            changer.replace(level, pos, oldCSet, newCSet, CCasingSet::getOverstressClutch, AXIS);
        }

        if (newSet instanceof CTransmissionSet newTSet && blockSet instanceof CTransmissionSet oldTSet) {
            changer.replace(level, pos, oldTSet, newTSet, CTransmissionSet::getShearPin, AXIS);
        }

        if (newSet instanceof CFluidSet newFSet && blockSet instanceof CFluidSet oldFSet) {
            if (player.isShiftKeyDown() && newFSet.getFluidVessel() != null && oldFSet.getFluidVessel() != null && state.is(oldFSet.getFluidVessel()))
                tankResult = changeWholeTank(level, pos, newFSet.getFluidVessel(), player, ammo);
            else
                changer.replace(level, pos, oldFSet, newFSet, CFluidSet::getFluidVessel, AXIS);
        }

        InteractionResult placementResult = tankResult != null ? tankResult : changer.getPlacementResult();
        if (placementResult.consumesAction()) {
            if (tankResult == null && !player.isCreative()) {
                ammo.stack().shrink(1);
                if (ammo.stack().isEmpty())
                    player.getInventory().removeItem(ammo.stack());
            }
            if (level.isClientSide)
                ScrollValueHandler.wrenchCog.bump(10, 30f);
            player.swing(InteractionHand.MAIN_HAND);
        }
        return placementResult;
    }


    private static InteractionResult changeWholeTank(Level level, BlockPos pos, FluidVesselBlock newTank, Player player, CCEAmmo ammo) {
        if (level.isClientSide)
            return InteractionResult.SUCCESS;
        ItemStack plates = ammo.stack();
        if (!(level.getBlockEntity(pos) instanceof FluidVesselBlockEntity clicked))
            return InteractionResult.PASS;
        FluidVesselBlockEntity controller = clicked.getControllerBE();
        if (controller == null)
            return InteractionResult.PASS;

        BlockPos origin = controller.getBlockPos();
        int width = controller.getWidth();
        int height = controller.getHeight();
        FluidStack fluid = controller.getTankInventory().getFluid().copy();
        boolean window = !(controller.getExtraData() instanceof Boolean b) || b;

        outer:
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                for (int z = 0; z < width; z++) {
                    BlockPos p = origin.offset(x, y, z);
                    BlockState oldState = level.getBlockState(p);
                    if (!(oldState.getBlock() instanceof FluidVesselBlock) || oldState.is(newTank))
                        continue;
                    if (!player.isCreative()) {
                        if (plates.isEmpty())
                            break outer;
                        plates.shrink(1);
                    }
                    BlockState newState = newTank.defaultBlockState();
                    for (Property<?> prop : new Property<?>[]{FluidVesselBlock.POSITIVE, FluidVesselBlock.NEGATIVE, FluidVesselBlock.AXIS, FluidVesselBlock.SHAPE})
                        newState = copyProperty(oldState, newState, prop);
                    changeBlock(level, p, oldState, newState);
                }

        if (plates.isEmpty() && !player.isCreative())
            player.getInventory().removeItem(plates);

        if (level.getBlockEntity(origin) instanceof FluidVesselBlockEntity newBe) {
            FluidVesselBlockEntity newController = newBe.getControllerBE();
            if (newController != null) {
                newController.setWindows(window);
                if (!fluid.isEmpty())
                    newController.getTankInventory().fill(fluid, IFluidHandler.FluidAction.EXECUTE);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> prop) {
        return to.setValue(prop, from.getValue(prop));
    }

    public static @Nullable SetBase<?, ?> getSetForBlock(Block block) {
        CCasingSet cSet = getCasingSetForBlock(block);
        if (cSet != null) return cSet;
        CTransmissionSet tSet = getTransmissionSetForBlock(block);
        if (tSet != null) return tSet;
        return getFluidSetForBlock(block);
    }

    private static CCasingSet getCasingSetForBlock(Block block) {
        return CCasingSets.getSets().stream()
                .filter(set -> set.isInSet(block))
                .findFirst()
                .orElse(null);
    }

    private static CTransmissionSet getTransmissionSetForBlock(Block block) {
        return CTransmissionSets.getSets().stream()
                .filter(set -> set.isInSet(block))
                .findFirst()
                .orElse(null);
    }

    private static CFluidSet getFluidSetForBlock(Block block) {
        return CFluidSets.getSets().stream()
                .filter(set -> set.isInSet(block))
                .findFirst()
                .orElse(null);
    }

    private static CCasingSet getCasingSetForItem(RegistryAccess registryAccess, Item item) {
        return CCasingSets.getSets().stream()
                .filter(set -> set.isItemForSet(registryAccess, item))
                .findFirst()
                .orElse(null);
    }

    private static CTransmissionSet getTransmissionSetForItem(RegistryAccess registryAccess, Item item) {
        return CTransmissionSets.getSets().stream()
                .filter(set -> set.isItemForSet(registryAccess, item))
                .findFirst()
                .orElse(null);
    }

    private static CFluidSet getFluidSetForItem(RegistryAccess registryAccess, Item item) {
        return CFluidSets.getSets().stream()
                .filter(set -> set.isItemForSet(registryAccess, item))
                .findFirst()
                .orElse(null);
    }

    public static boolean isShearPin(BlockState state) {
        return CTransmissionSets.getSets().stream().filter(set -> set.getShearPin() != null).anyMatch(set -> state.getBlock().equals(set.getShearPin()));
    }

    public static List<SetBase<?, ?>> getSetsForItem(RegistryAccess registryAccess, Item item) {
        return ITEM_SETS_CACHE.computeIfAbsent(item, key -> findSetsForItem(registryAccess, key));
    }

    private static List<SetBase<?, ?>> findSetsForItem(RegistryAccess registryAccess, Item item) {
        List<SetBase<?, ?>> sets = new ArrayList<>();
        CCasingSet cSet = getCasingSetForItem(registryAccess, item);
        if (cSet != null) sets.add(cSet);
        CTransmissionSet tSet = getTransmissionSetForItem(registryAccess, item);
        if (tSet != null) sets.add(tSet);
        CFluidSet fSet = getFluidSetForItem(registryAccess, item);
        if (fSet != null) sets.add(fSet);
        return sets;
    }
}
