package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselCTBehaviour;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.createmod.catnip.data.Iterate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomFluidVesselModel extends CTModel {

    protected static final ModelProperty<CustomFluidVesselModel.CullData> CULL_PROPERTY = new ModelProperty<>();

    public CustomFluidVesselModel(BakedModel originalModel, CTSpriteShiftEntry side, CTSpriteShiftEntry top,
                                  CTSpriteShiftEntry inner) {
        super(originalModel, new FluidVesselCTBehaviour(side, top, inner));
    }

    @Override
    protected ModelData.Builder gatherModelData(ModelData.Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state,
                                                ModelData blockEntityData) {
        super.gatherModelData(builder, world, pos, state, blockEntityData);
        CustomFluidVesselModel.CullData cullData = new CustomFluidVesselModel.CullData();
        Direction.Axis axis = state.getValue(FluidVesselBlock.AXIS);
        for (Direction d : Iterate.directions) {
            if (d.getAxis() == axis)
                continue;
            cullData.setCulled(d, ConnectivityHandler.isConnected(world, pos, pos.relative(d)));
        }
        return builder.with(CULL_PROPERTY, cullData);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
        if (side != null)
            return Collections.emptyList();

        List<BakedQuad> quads = new ArrayList<>();
        for (Direction d : Iterate.directions) {
            if (extraData.has(CULL_PROPERTY) && extraData.get(CULL_PROPERTY)
                    .isCulled(d))
                continue;
            quads.addAll(super.getQuads(state, d, rand, extraData, renderType));
        }
        quads.addAll(super.getQuads(state, null, rand, extraData, renderType));
        return quads;
    }

    protected static class CullData {
        boolean[] culledFaces;

        public CullData() {
            culledFaces = new boolean[6];
            Arrays.fill(culledFaces, false);
        }

        void setCulled(Direction face, boolean cull) {
            culledFaces[face.get3DDataValue()] = cull;
        }

        boolean isCulled(Direction face) {
            return culledFaces[face.get3DDataValue()];
        }
    }

}
