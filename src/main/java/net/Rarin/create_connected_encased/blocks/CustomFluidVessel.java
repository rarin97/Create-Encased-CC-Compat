package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomFluidVessel extends FluidVesselBlock {

    public CustomFluidVessel(Properties properties) {
        super(properties, false);
    }

    @Override
    public BlockEntityType<? extends FluidVesselBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.FLUID_VESSEL.get();
    }
}
