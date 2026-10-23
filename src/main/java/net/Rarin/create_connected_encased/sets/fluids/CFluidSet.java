package net.Rarin.create_connected_encased.sets.fluids;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.Rarin.create_connected_encased.sets.SetBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CFluidSet extends SetBase<CFluidSet, CFluidSet.Options> {

    private final Supplier<CTSpriteShiftEntry> tankSideCtSprite;
    private final Supplier<CTSpriteShiftEntry> tankTopCtSprite;
    private final Supplier<CTSpriteShiftEntry> tankInnerCtSprite;
    private final Supplier<PartialModel> engineGaugeModel;
    private final Supplier<PartialModel> engineGaugeDialModel;
    private @Nullable BlockEntry<? extends FluidVesselBlock> fluidVesselBlock;
    private @Nullable BlockEntityEntry<? extends FluidVesselBlockEntity> fluidVesselBlockEntity;

    private final boolean fluidVessel;

    protected CFluidSet(String name, Options options) {
        super(name, options);

        this.tankSideCtSprite = options.tankSideCtSprite;
        this.tankTopCtSprite = options.tankTopCtSprite;
        this.tankInnerCtSprite = options.tankInnerCtSprite;

        this.engineGaugeModel = options.engineGaugeModel;
        this.engineGaugeDialModel = options.engineDialGaugeModel;

        fluidVessel = options.fluidVessel;

        if (options.existingFluidVessel != null) fluidVesselBlock = options.existingFluidVessel;

    }

    public boolean doesGenerateFluidVessel() {
        return fluidVessel;
    }

    @Nullable
    public BlockEntry<? extends FluidVesselBlock> getFluidVesselSupplier() {
        return fluidVesselBlock;
    }

    @Nullable
    public FluidVesselBlock getFluidVessel() {
        return fluidVesselBlock == null ? null : fluidVesselBlock.get();
    }

    @Nullable
    public BlockEntityEntry<? extends FluidVesselBlockEntity> getFluidVesselBlockEntitySupplier() {
        return fluidVesselBlockEntity;
    }

    @Nullable
    public BlockEntityType<? extends FluidVesselBlockEntity> getFluidVesselBlockEntityType() {
        return fluidVesselBlockEntity == null ? null : fluidVesselBlockEntity.get();
    }

    public void setFluidVesselBlockEntity(@Nonnull BlockEntityEntry<? extends FluidVesselBlockEntity> type) {
        if (fluidVesselBlockEntity != null)
            throw new UnsupportedOperationException("You cannot modify a fluid tank block entity that has already been referenced");
        fluidVesselBlockEntity = type;
    }

    public void setFluidVessel(@Nonnull BlockEntry<? extends FluidVesselBlock> tank) {
        if (getFluidVesselSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a fluid tank that has already been referenced");
        fluidVesselBlock = tank;
    }


    public CTSpriteShiftEntry getTankSideSprite() {
        return tankSideCtSprite.get();
    }

    public CTSpriteShiftEntry getTankTopSprite() {
        return tankTopCtSprite.get();
    }

    public CTSpriteShiftEntry getTankInnerSprite() {
        return tankInnerCtSprite.get();
    }

    public PartialModel getEngineGaugeModel() {
        return engineGaugeModel.get();
    }

    public PartialModel getEngineGaugeDialModel() {
        return engineGaugeDialModel.get();
    }


    public boolean isInSet(Block block) {
        return block.equals(getFluidVessel());
    }

    public List<? extends Block> getAllBlocks() {
        return Stream.of(getFluidVessel()).filter(Objects::nonNull).toList();
    }

    public static class Options extends SetBase.Options<CFluidSet, Options> {
        private Supplier<CTSpriteShiftEntry> tankSideCtSprite = () -> null;
        private Supplier<CTSpriteShiftEntry> tankTopCtSprite = () -> null;
        private Supplier<CTSpriteShiftEntry> tankInnerCtSprite = () -> null;
        private Supplier<PartialModel> engineGaugeModel;
        private Supplier<PartialModel> engineDialGaugeModel;

        private boolean fluidVessel;

        private @Nullable BlockEntry<? extends FluidVesselBlock> existingFluidVessel;

        public Options fluidVessel(Supplier<CTSpriteShiftEntry> tankSideCtSprite, Supplier<CTSpriteShiftEntry> tankTopCtSprite, Supplier<CTSpriteShiftEntry> tankInnerCtSprite, Supplier<PartialModel> gauge, Supplier<PartialModel> gaugeDial) {
            this.tankSideCtSprite = tankSideCtSprite;
            this.tankTopCtSprite = tankTopCtSprite;
            this.tankInnerCtSprite = tankInnerCtSprite;
            this.engineGaugeModel = gauge;
            this.engineDialGaugeModel = gaugeDial;
            this.fluidVessel = true;
            return this;
        }

        public Options FluidBlocks(Supplier<CTSpriteShiftEntry> tankSideCtSprite, Supplier<CTSpriteShiftEntry> tankTopCtSprite, Supplier<CTSpriteShiftEntry> tankInnerCtSprite, Supplier<PartialModel> gauge,Supplier<PartialModel> gaugeDial) {
            return fluidVessel(tankSideCtSprite, tankTopCtSprite, tankInnerCtSprite, gauge, gaugeDial);
        }


        Options existingVessel(BlockEntry<? extends FluidVesselBlock> tank) {
            this.existingFluidVessel = tank;
            this.fluidVessel = false;
            return this;
        }

        @Override
        protected Options self() {
            return this;
        }
    }
}
