package net.Rarin.create_connected_encased.registries;

import com.hlysine.create_connected.content.brake.BrakeBlockEntity;
import com.hlysine.create_connected.content.centrifugalclutch.CentrifugalClutchBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselRenderer;
import com.hlysine.create_connected.content.freewheelclutch.FreewheelClutchBlockEntity;
import com.hlysine.create_connected.content.invertedclutch.InvertedClutchBlockEntity;
import com.hlysine.create_connected.content.invertedgearshift.InvertedGearshiftBlockEntity;
import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlockEntity;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxBlockEntity;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxRenderer;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxVisual;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxBlockEntity;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxRenderer;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxVisual;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogVisual;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.simibubi.create.content.kinetics.transmission.SplitShaftVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.casings.CCasingSet;
import net.Rarin.create_connected_encased.casings.CCasingSets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static net.Rarin.create_connected_encased.CCEncased.REGISTRATE;

@EventBusSubscriber(modid = CCEncased.ID)
public class CCEncasedBlockEntities {

    public static final BlockEntityEntry<SimpleKineticBlockEntity> CHAIN_COGWHEEL = REGISTRATE
            .blockEntity("encased_chain_cogwheel", SimpleKineticBlockEntity::new)
            .visual(() -> EncasedCogVisual::small, false)
            .renderer(() -> EncasedCogRenderer::small)
            .register();

    public static final BlockEntityEntry<BrakeBlockEntity> BRAKE = REGISTRATE
            .blockEntity("brake", BrakeBlockEntity::new)
            .visual(() -> SplitShaftVisual::new, false)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<ParallelGearboxBlockEntity> PARALLEL_GEARBOX = REGISTRATE
            .blockEntity("parallel_gearbox", ParallelGearboxBlockEntity::new)
            .visual(() -> ParallelGearboxVisual::new, false)
            .renderer(() -> ParallelGearboxRenderer::new)
            .register();

    public static final BlockEntityEntry<SixWayGearboxBlockEntity> SIX_WAY_GEARBOX = REGISTRATE
            .blockEntity("six_way_gearbox", SixWayGearboxBlockEntity::new)
            .visual(() -> SixWayGearboxVisual::new, false)
            .renderer(() -> SixWayGearboxRenderer::new)
            .register();

    public static final BlockEntityEntry<InvertedGearshiftBlockEntity> INVERTED_GEARSHIFT = REGISTRATE
            .blockEntity("inverted_gearshift", InvertedGearshiftBlockEntity::new)
            .visual(() -> SplitShaftVisual::new, false)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<InvertedClutchBlockEntity> INVERTED_CLUTCH = REGISTRATE
            .blockEntity("inverted_clutch", InvertedClutchBlockEntity::new)
            .visual(() -> SplitShaftVisual::new, false)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<CentrifugalClutchBlockEntity> CENTRIFUGAL_CLUTCH = REGISTRATE
            .blockEntity("centrifugal_clutch", CentrifugalClutchBlockEntity::new)
            .visual(() -> SplitShaftVisual::new, false)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<FreewheelClutchBlockEntity> FREEWHEEL_CLUTCH = REGISTRATE
            .blockEntity("freewheel_clutch", FreewheelClutchBlockEntity::new)
            .visual(() -> SplitShaftVisual::new, false)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<OverstressClutchBlockEntity> OVERSTRESS_CLUTCH = REGISTRATE
            .blockEntity("overstress_clutch", OverstressClutchBlockEntity::new)
            .visual(() -> SplitShaftVisual::new, false)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<FluidVesselBlockEntity> FLUID_VESSEL = REGISTRATE
            .blockEntity("fluid_vessel", FluidVesselBlockEntity::new)
            .renderer(() -> FluidVesselRenderer::new)
            .register();

    public static void register() {}

    @SubscribeEvent
    public static void modifyBlockEntity(BlockEntityTypeAddBlocksEvent event){
        register(event, CHAIN_COGWHEEL.get(), CCasingSet::getChainCogwheel, CCasingSet::doesGenerateChainCogwheel);
        register(event, BRAKE.get(), CCasingSet::getBrake, CCasingSet::doesGenerateBrake);
        register(event, PARALLEL_GEARBOX.get(), CCasingSet::getParallelGearbox, CCasingSet::doesGenerateParallelGearbox);
        register(event, SIX_WAY_GEARBOX.get(), CCasingSet::getSixWayGearbox, CCasingSet::doesGenerateSixWayGearbox);
        register(event, INVERTED_GEARSHIFT.get(), CCasingSet::getInvertedGearShift, CCasingSet::doesGenerateInvertedGearShift);
        register(event, INVERTED_CLUTCH.get(), CCasingSet::getInvertedClutch, CCasingSet::doesGenerateInvertedClutch);
        register(event, CENTRIFUGAL_CLUTCH.get(), CCasingSet::getCentrifugalClutch, CCasingSet::doesGenerateCentrifugalClutch);
        register(event, FREEWHEEL_CLUTCH.get(), CCasingSet::getFreewheelClutch, CCasingSet::doesGenerateFreewheelClutch);
        register(event, OVERSTRESS_CLUTCH.get(), CCasingSet::getOverstressClutch, CCasingSet::doesGenerateOverstressClutch);
        register(event, FLUID_VESSEL.get(), CCasingSet::getFluidVessel, CCasingSet::doesGenerateFluidVessel);
    }

    public static void register(BlockEntityTypeAddBlocksEvent event, BlockEntityType<?> type, Function<CCasingSet, Block> blockFunction, Predicate<CCasingSet> validateFunction){
        CCasingSets.getSets().stream().filter(set-> Objects.nonNull(blockFunction.apply(set))).filter(validateFunction).map(blockFunction).forEach(b->event.modify(type,b));
    }
}
