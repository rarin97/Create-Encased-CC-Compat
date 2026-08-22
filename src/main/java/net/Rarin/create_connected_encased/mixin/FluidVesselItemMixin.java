package net.Rarin.create_connected_encased.mixin;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import net.Rarin.create_connected_encased.blocks.CustomFluidVessel;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = FluidVesselItem.class,remap = false)
public class FluidVesselItemMixin {

    @WrapOperation(method = "tryMultiPlace", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/api/connectivity/ConnectivityHandler;partAt(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private <T extends BlockEntity & IMultiBlockEntityContainer> T workWithCustomTanks(BlockEntityType<?> type, BlockGetter level, BlockPos pos, Operation<T> original) {
        if (level.getBlockState(pos).getBlock() instanceof CustomFluidVessel)
            return ConnectivityHandler.partAt(CCEncasedBlockEntities.FLUID_VESSEL.get(), level, pos);
        return original.call(type, level, pos);
    }
}
