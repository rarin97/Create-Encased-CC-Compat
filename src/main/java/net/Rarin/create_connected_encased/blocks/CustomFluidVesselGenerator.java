package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import fr.iglee42.createcasing.CreateCasing;
import net.Rarin.create_connected_encased.CCEncased;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import static com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock.*;
import static com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock.AXIS;

public class CustomFluidVesselGenerator extends SpecialBlockStateGen {

	private final String name;

    public CustomFluidVesselGenerator(String name) {
        this.name = name;
    }

    @Override
	protected int getXRotation(BlockState state) {
		return 0;
	}

	@Override
	protected int getYRotation(BlockState state) {
		return 0;
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
		BlockState state) {
		Boolean positive = state.getValue(POSITIVE);
		Boolean negative = state.getValue(NEGATIVE);
		FluidVesselBlock.Shape shape = state.getValue(SHAPE);
		Direction.Axis axis = state.getValue(AXIS);

		if (positive && negative)
			shape = shape.nonSingleVariant();

		String shapeName = "middle";
		if (positive && negative)
			shapeName = "single";
		else if (positive)
			shapeName = "positive";
		else if (negative)
			shapeName = "negative";

		String modelName = (axis == Direction.Axis.X ? "x" : "z") +
				"_" + shapeName +
				(shape == FluidVesselBlock.Shape.PLAIN ? "" : "_" + shape.getSerializedName());

		return prov.models()
				.withExistingParent("block/fluid_vessel/"+name+"/block_" + modelName, CreateConnected.asResource("block/fluid_vessel/block_" + modelName))
				.texture("0", CreateCasing.asResource("block/fluid_tank_top/" + name))
				.texture("1", CreateCasing.asResource("block/fluid_tank/" + name))
				.texture("3", CreateCasing.asResource("block/fluid_tank_window/"+name))
				.texture("4", CreateCasing.asResource("block/fluid_tank_inner/" + name))
				.texture("5", CreateCasing.asResource("block/fluid_tank_window_single/"+name))
				.texture("6", CCEncased.asResource("block/fluid_container_window/"+name))
				.texture("7", CCEncased.asResource("block/fluid_container_window_single/"+name))
				.texture("particle", CreateCasing.asResource("block/fluid_tank/"+name));
	}

}
