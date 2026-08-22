package net.Rarin.create_connected_encased.registries;

import com.google.common.base.Function;
import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlock;
import com.simibubi.create.content.kinetics.chainDrive.ChainDriveBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import fr.iglee42.createcasing.registries.EncasedBlockStateGens;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import java.util.Objects;

import static com.hlysine.create_connected.content.freewheelclutch.FreewheelClutchBlock.UNCOUPLED;

public class CCEncasedBlockStateGens extends EncasedBlockStateGens {

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> parallelgearbox(String casing) {
        return (ctx, prov) -> axisBlock(ctx, prov, parallelGearboxModel(prov, casing, "block_y"));
    }
    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> sixWaygearbox(String casing) {
        return (ctx, prov) -> axisBlock(ctx, prov, sixWayGearboxModel(prov, casing, "block"));
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> encasedChainWheel(String casing) {
        return (ctx, prov) -> {
            prov.getVariantBuilder(ctx.getEntry())
                    .forAllStatesExcept(state -> {
                        int rotationX = getChainWheelXRotation(state);
                        int rotationY = getChainWheelYRotation(state);
                        String suffix = getChainWheelModelSuffix(state);
                        return ConfiguredModel.builder().modelFile(createChainWheelModel(prov, casing, false, suffix)).rotationX(rotationX).rotationY(rotationY).build();
                    });
        };
    }

    public static ModelFile createChainWheelModel(RegistrateProvider provider, String casing, boolean item, String suffix) {
        if (!item) {
            String partKey = suffix.startsWith("end") ? "2" : "1";
            String partKey1 = suffix.startsWith("end") ? "3" : "2";
            return Objects.requireNonNull(createModelInBlock(provider, "encased_chain_cogwheel/" + casing + "/" + suffix))
                    .parent(new ModelFile.UncheckedModelFile("create_connected:block/encased_chain_cogwheel/" + suffix))
                    .texture(partKey, suffix.equals("single") ? getGearboxTexture(casing) : getChainDrivePart(casing, partKey))
                    .texture(partKey1, suffix.equals("single") ? getGearboxTexture(casing) : getChainWheelPart(casing, partKey))
                    .texture("side", getChainWheelSideTexture(casing))
                    .texture("particle", getCasingTexture(casing));

        } else {
            return Objects.requireNonNull(createModelInBlock(provider, "encased_chain_cogwheel/" + casing + "/item"))
                    .parent(new ModelFile.UncheckedModelFile("create_connected:block/encased_chain_cogwheel/item"))
                    .texture("1", getGearboxTexture(casing))
                    .texture("2", getGearboxTexture(casing))
                    .texture("side", getChainWheelSideTexture(casing))
                    .texture("particle", getCasingTexture(casing));
        }
    }

    private static int getChainWheelXRotation(BlockState state) {
        ChainDriveBlock.Part part = state.getValue(ChainDriveBlock.PART);
        boolean connectedAlongFirst = state.getValue(ChainDriveBlock.CONNECTED_ALONG_FIRST_COORDINATE);
        Direction.Axis axis = state.getValue(ChainDriveBlock.AXIS);

        if (part == ChainDriveBlock.Part.NONE)
            return axis == Direction.Axis.Y ? 90 : 0;
        if (axis == Direction.Axis.X)
            return (connectedAlongFirst ? 90 : 0) + (part == ChainDriveBlock.Part.START ? 180 : 0);
        if (axis == Direction.Axis.Z)
            return (connectedAlongFirst ? 0 : (part == ChainDriveBlock.Part.START ? 270 : 90));
        return 0;
    }

    private static int getChainWheelYRotation(BlockState state) {
        ChainDriveBlock.Part part = state.getValue(ChainDriveBlock.PART);
        boolean connectedAlongFirst = state.getValue(ChainDriveBlock.CONNECTED_ALONG_FIRST_COORDINATE);
        Direction.Axis axis = state.getValue(ChainDriveBlock.AXIS);

        if (part == ChainDriveBlock.Part.NONE)
            return axis == Direction.Axis.X ? 90 : 0;
        if (axis == Direction.Axis.Z)
            return (connectedAlongFirst && part == ChainDriveBlock.Part.END ? 270 : 90);
        boolean flip = part == ChainDriveBlock.Part.END && !connectedAlongFirst || part == ChainDriveBlock.Part.START && connectedAlongFirst;
        if (axis == Direction.Axis.Y)
            return (connectedAlongFirst ? 90 : 0) + (flip ? 180 : 0);
        return 0;
    }

    private static String getChainWheelModelSuffix(BlockState state) {
        ChainDriveBlock.Part part = state.getValue(ChainDriveBlock.PART);
        Direction.Axis axis = state.getValue(ChainDriveBlock.AXIS);

        if (part == ChainDriveBlock.Part.NONE)
            return "single";

        String orientation = axis == Direction.Axis.Y ? "vertical" : "horizontal";
        String section = part == ChainDriveBlock.Part.MIDDLE ? "middle" : "end";
        return section + "_" + orientation;
    }

    public static <T> Function<BlockState, ModelFile> brakeModel(RegistrateProvider p, String casing) {
        if (isValidProvider(p))
            return state -> {
                boolean powered = state.getValue(BlockStateProperties.POWERED);
                return Objects.requireNonNull(createModelInBlock(p, "brake/" + casing + "/block" + (powered ? "_powered" : "")))
                        .parent(new ModelFile.UncheckedModelFile("create_connected:block/brake/block" + (powered ? "_powered" : "")))
                        .texture("0", getBrakeTexture(casing, powered))
                        .texture("1", getGearboxTexture(casing))
                        .texture("2", getFunnelFrameTexture(casing))
                        .texture("particle", getBrakeTexture(casing, powered));
            };
        return null;
    }

    public static ModelFile brakeItemModel(RegistrateProvider p, String casing) {
        return Objects.requireNonNull(createModelInBlock(p, "brake/" + casing + "/item"))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/brake/item"))
                .texture("0", getBrakeTexture(casing, false))
                .texture("1", getGearboxTexture(casing))
                .texture("4", getFunnelFrameTexture(casing))
                .texture("particle", getBrakeTexture(casing, false));
    }

    public static <T> Function<BlockState, ModelFile> invertedGearshiftModel(RegistrateProvider p, String casing) {
        if (isValidProvider(p))
            return state -> {
                boolean powered = state.getValue(BlockStateProperties.POWERED);
                return Objects.requireNonNull(createModelInBlock(p, "inverted_gearshift/" + casing + "/block" + (powered ? "_powered" : "")))
                        .parent(new ModelFile.UncheckedModelFile("create_connected:block/inverted_gearshift/block" + (powered ? "_powered" : "")))
                        .texture("0", getInvertedGearshiftTexture(casing, powered))
                        .texture("1", getGearboxTexture(casing))
                        .texture("2", getFunnelFrameTexture(casing))
                        .texture("particle", getInvertedGearshiftTexture(casing, powered));
            };
        return null;
    }

    public static ModelFile invertedGearshiftItemModel(RegistrateProvider p, String casing) {
        return Objects.requireNonNull(createModelInBlock(p, "inverted_gearshift/" + casing + "/item"))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/inverted_gearshift/item"))
                .texture("0", getInvertedGearshiftTexture(casing, false))
                .texture("1", getGearboxTexture(casing))
                .texture("particle", getInvertedGearshiftTexture(casing, false));
    }

    public static <T> Function<BlockState, ModelFile> invertedClutchModel(RegistrateProvider p, String casing) {
        if (isValidProvider(p))
            return state -> {
                boolean powered = state.getValue(BlockStateProperties.POWERED);
                return Objects.requireNonNull(createModelInBlock(p, "inverted_clutch/" + casing + "/block" + (powered ? "_powered" : "")))
                        .parent(new ModelFile.UncheckedModelFile("create_connected:block/inverted_clutch/block" + (powered ? "_powered" : "")))
                        .texture("0", getInvertedClutchTexture(casing, powered))
                        .texture("1", getGearboxTexture(casing))
                        .texture("2", getFunnelFrameTexture(casing))
                        .texture("particle", getInvertedClutchTexture(casing, powered));
            };
        return null;
    }

    public static ModelFile invertedClutchItemModel(RegistrateProvider p, String casing) {
        return Objects.requireNonNull(createModelInBlock(p, "inverted_clutch/" + casing + "/item"))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/inverted_clutch/item"))
                .texture("0", getInvertedClutchTexture(casing, false))
                .texture("1", getGearboxTexture(casing))
                .texture("4", getFunnelFrameTexture(casing))
                .texture("particle", getInvertedClutchTexture(casing, false));
    }

    public static <T> Function<BlockState, ModelFile> freewheelClutchModel(RegistrateProvider p, String casing) {
        if (isValidProvider(p))
            return state -> {
                boolean uncoupled = state.getValue(UNCOUPLED);
                return Objects.requireNonNull(createModelInBlock(p, "freewheel_clutch/" + casing + "/block" + (uncoupled ? "_uncoupled" : "")))
                        .parent(new ModelFile.UncheckedModelFile("create_connected:block/freewheel_clutch/block" + (uncoupled ? "_uncoupled" : "")))
                        .texture("0", getFreewheelClutchTexture(casing, uncoupled))
                        .texture("1", getGearboxTexture(casing))
                        .texture("2", getFunnelFrameTexture(casing))
                        .texture("particle", getFreewheelClutchTexture(casing, uncoupled));
            };
        return null;
    }

    public static ModelFile freewheelClutchItemModel(RegistrateProvider p, String casing) {
        return Objects.requireNonNull(createModelInBlock(p, "freewheel_clutch/" + casing + "/item"))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/freewheel_clutch/item"))
                .texture("0", getFreewheelClutchTexture(casing, false))
                .texture("1", getGearboxTexture(casing))
                .texture("4", getFunnelFrameTexture(casing))
                .texture("particle", getFreewheelClutchTexture(casing, false));
    }

    public static <T> Function<BlockState, ModelFile> centrifugalClutchModel(RegistrateProvider p, String casing) {
        if (isValidProvider(p))
            return state -> {
                boolean uncoupled = state.getValue(UNCOUPLED);
                return Objects.requireNonNull(createModelInBlock(p, "centrifugal_clutch/" + casing + "/block" + (uncoupled ? "_uncoupled" : "")))
                        .parent(new ModelFile.UncheckedModelFile("create_connected:block/centrifugal_clutch/block" + (uncoupled ? "_uncoupled" : "")))
                        .texture("0", getCentrifugalClutchTexture(casing, uncoupled))
                        .texture("1", getGearboxTexture(casing))
                        .texture("2", getFunnelFrameTexture(casing))
                        .texture("particle", getFreewheelClutchTexture(casing, uncoupled));
            };
        return null;
    }

    public static ModelFile centrifugalClutchItemModel(RegistrateProvider p, String casing) {
        return Objects.requireNonNull(createModelInBlock(p, "centrifugal_clutch/" + casing + "/item"))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/centrifugal_clutch/item"))
                .texture("0", getCentrifugalClutchTexture(casing, false))
                .texture("1", getGearboxTexture(casing))
                .texture("4", getFunnelFrameTexture(casing))
                .texture("particle", getFreewheelClutchTexture(casing, false));
    }

    public static <T> Function<BlockState, ModelFile> overstressClutchModel(RegistrateProvider p, String casing) {
        if (isValidProvider(p))
            return state -> {
                boolean uncoupled = state.getValue(OverstressClutchBlock.STATE) == OverstressClutchBlock.ClutchState.UNCOUPLED;
                boolean powered = state.getValue(OverstressClutchBlock.POWERED);
                return Objects.requireNonNull(createModelInBlock(p, "overstress_clutch/" + casing + "/block"  + (uncoupled ? "_uncoupled" : "") + (powered ? "_powered" : "")))
                        .parent(new ModelFile.UncheckedModelFile("create_connected:block/overstress_clutch/block"  + (uncoupled ? "_uncoupled" : "") + (powered ? "_powered" : "")))
                        .texture("0", getOverstressClutchTexture(casing, powered, uncoupled))
                        .texture("1", getGearboxTexture(casing))
                        .texture("2", getFunnelFrameTexture(casing))
                        .texture("particle", getOverstressClutchTexture(casing, powered, uncoupled));
            };
        return null;
    }

    public static ModelFile overstressClutchItemModel(RegistrateProvider p, String casing) {
        return Objects.requireNonNull(createModelInBlock(p, "overstress_clutch/" + casing + "/item"))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/overstress_clutch/item"))
                .texture("0", getOverstressClutchTexture(casing, false, false))
                .texture("1", getGearboxTexture(casing))
                .texture("4", getFunnelFrameTexture(casing))
                .texture("particle", getOverstressClutchTexture(casing, false, false));
    }

    public static <T> ModelFile sixWayGearboxModel(RegistrateProvider p, String casing, String type) {
        return isValidProvider(p) ? ((ModelBuilder)Objects.requireNonNull(createModelInBlock(p, "six_way_gearbox/" + casing + "/" + type)))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/six_way_gearbox/" + type))
                .texture("0", getGearboxTexture(casing))
                .texture("1", getParallelGearboxTexture(casing))
                .texture("2", getFunnelFrameTexture(casing))
                .texture("particle", getCasingTexture(casing)) : null;
    }

    public static <T> ModelFile parallelGearboxModel(RegistrateProvider p, String casing, String type) {
        String casingKey = type.equals("item_vertical") ? "gearbox" : "1";
        String topKey = type.equals("item_vertical") ? "gearbox_top" : "0";
        return isValidProvider(p) ? ((ModelBuilder)Objects.requireNonNull(createModelInBlock(p, "parallel_gearbox/" + casing + "/" + type)))
                .parent(new ModelFile.UncheckedModelFile("create_connected:block/parallel_gearbox/" + type))
                .texture(topKey, getCasingTexture(casing))
                .texture(casingKey, getParallelGearboxTexture(casing))
                .texture("particle", getCasingTexture(casing)) : null;
    }

    public static String getChainWheelSideTexture(String casing) {
        return casing.equals("normal") ? "create_connected:block/encased_chain_cogwheel" : "create_connected_encased:block/encased_chain_cogwheel/" + casing;
    }

    public static String getChainWheelPart(String casing, String partSuffix) {
        String part = partSuffix.equals("2") ? "single" : "middle";
        return casing.equals("normal") ? "block/stripped_spruce_log_top" + part : getGearboxTexture(casing);
    }

    public static String getBrakeTexture(String casing, boolean powered) {
        return casing.equals("normal") ? "create_connected:block/brake_" + (powered ? "on" : "off") : "create_connected_encased:block/brake_" + (powered ? "on" : "off") + "/" + casing;
    }

    public static String getParallelGearboxTexture(String casing) {
        return casing.equals("normal") ? "create_connected:block/encased_chain_cogwheel" : "create_connected_encased:block/gearbox/" + casing;
    }

    public static String getInvertedGearshiftTexture(String casing, boolean powered) {
        return casing.equals("normal") ? "create_connected:block/inverted_gearshift_" + (powered ? "on" : "off") : "create_connected_encased:block/inverted_gearshift_" + (powered ? "on" : "off") + "/" + casing;
    }

    public static String getInvertedClutchTexture(String casing, boolean powered) {
        return casing.equals("normal") ? "create_connected:block/inverted_clutch_" + (powered ? "on" : "off") : "create_connected_encased:block/inverted_clutch_" + (powered ? "on" : "off") + "/" + casing;
    }

    public static String getFreewheelClutchTexture(String casing, boolean powered) {
        return casing.equals("normal") ? "create_connected:block/freewheel_clutch_" + (powered ? "on" : "off") : "create_connected_encased:block/freewheel_clutch_" + (powered ? "on" : "off") + "/" + casing;
    }

    public static String getCentrifugalClutchTexture(String casing, boolean powered) {
        return casing.equals("normal") ? "create_connected:block/centrifugal_clutch_" + (powered ? "on" : "off") : "create_connected_encased:block/centrifugal_clutch_" + (powered ? "on" : "off") + "/" + casing;
    }

    public static String getOverstressClutchTexture(String casing, boolean powered, boolean state) {
        return casing.equals("normal") ? "create_connected:block/overstress_clutch_" + (powered ? "on" : "off") : "create_connected_encased:block/overstress_clutch_" + (state ? "on" : "off") + (powered ? "_powered" : "") + "/" + casing;
    }
}
