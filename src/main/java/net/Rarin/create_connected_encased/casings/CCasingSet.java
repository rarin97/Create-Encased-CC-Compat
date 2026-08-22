package net.Rarin.create_connected_encased.casings;

import com.google.common.base.Preconditions;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import fr.iglee42.createcasing.casings.CasingSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CCasingSet {

    private final String name;
    private final Supplier<CTSpriteShiftEntry> ctSprite;
    private final Supplier<CTSpriteShiftEntry> tankSideCtSprite;
    private final Supplier<CTSpriteShiftEntry> tankTopCtSprite;
    private final Supplier<CTSpriteShiftEntry> tankInnerCtSprite;

    private @Nullable Supplier<? extends BlockItem> verticalParallelGearboxBlockItem;
    private @Nullable Supplier<? extends BlockItem> verticalSixWayGearboxBlockItem;
    private @Nullable Supplier<? extends Block> casingBlock;
    private @Nullable Supplier<? extends Block> chainCogwheelBlock;
    private @Nullable Supplier<? extends Block> brakeBlock;
    private @Nullable Supplier<? extends Block> parallelGearboxBlock;
    private @Nullable Supplier<? extends Block> sixWayGearboxBlock;
    private @Nullable Supplier<? extends Block> invertedGearShiftBlock;
    private @Nullable Supplier<? extends Block> invertedClutchBlock;
    private @Nullable Supplier<? extends Block> centrifugalClutchBlock;
    private @Nullable Supplier<? extends Block> freewheelClutchBlock;
    private @Nullable Supplier<? extends Block> overstressClutchBlock;
    private @Nullable Supplier<? extends Block> fluidVesselBlock;

    private final boolean casing;
    private final boolean chainCogwheel;
    private final boolean brake;
    private final boolean parallelGearbox;
    private final boolean sixWayGearbox;
    private final boolean invertedGearShift;
    private final boolean invertedClutch;
    private final boolean centrifugalClutch;
    private final boolean freewheelClutch;
    private final boolean overstressClutch;
    private final boolean fluidVessel;

    protected CCasingSet(String name, Options options) {
        this.name = name;
        this.tankSideCtSprite = options.tankSideCtSprite;
        this.tankTopCtSprite = options.tankTopCtSprite;
        this.tankInnerCtSprite = options.tankInnerCtSprite;

        Preconditions.checkNotNull(options.ctSprite,"Connected Texture Sprite Supplier can't be null");
        ctSprite = options.ctSprite;
        casingBlock = options.existingCasing;
        casing = options.casing;
        chainCogwheel = options.chainCogwheel;
        brake = options.brake;
        parallelGearbox = options.parallelGearbox;
        sixWayGearbox = options.sixWayGearbox;
        invertedGearShift = options.invertedGearShift;
        invertedClutch = options.invertedClutch;
        centrifugalClutch = options.centrifugalClutch;
        freewheelClutch = options.freewheelClutch;
        overstressClutch = options.overstressClutch;
        fluidVessel = options.fluidVessel;

        if (options.existingVerticalParallelGearboxItem != null) verticalParallelGearboxBlockItem = options.existingVerticalParallelGearboxItem;
        if (options.existingVerticalSixWayGearboxItem != null) verticalSixWayGearboxBlockItem = options.existingVerticalSixWayGearboxItem;
        if (options.existingChainCogwheel != null) chainCogwheelBlock = options.existingChainCogwheel;
        if (options.existingBrake != null) brakeBlock = options.existingBrake;
        if (options.existingParallelGearbox != null) parallelGearboxBlock = options.existingParallelGearbox;
        if (options.existingSixWayGearbox != null) sixWayGearboxBlock = options.existingSixWayGearbox;
        if (options.existingInvertedGearShift != null) invertedGearShiftBlock = options.existingInvertedGearShift;
        if (options.existingInvertedClutch != null) invertedClutchBlock = options.existingInvertedClutch;
        if (options.existingCentrifugalClutch != null) centrifugalClutchBlock = options.existingCentrifugalClutch;
        if (options.existingFreewheelClutch != null) freewheelClutchBlock = options.existingFreewheelClutch;
        if (options.existingOverstressClutch != null) overstressClutchBlock = options.existingOverstressClutch;
        if (options.existingFluidVessel != null) fluidVesselBlock = options.existingFluidVessel;
    }

    public String getName() {
        return name;
    }

    public boolean doesGenerateCasing(){
        return casing;
    }
    public boolean doesGenerateChainCogwheel(){
        return chainCogwheel;
    }
    public boolean doesGenerateBrake(){
        return brake;
    }
    public boolean doesGenerateParallelGearbox(){
        return parallelGearbox;
    }
    public boolean doesGenerateSixWayGearbox(){
        return sixWayGearbox;
    }
    public boolean doesGenerateInvertedGearShift(){
        return invertedGearShift;
    }
    public boolean doesGenerateInvertedClutch(){
        return invertedClutch;
    }
    public boolean doesGenerateCentrifugalClutch(){
        return centrifugalClutch;
    }
    public boolean doesGenerateFreewheelClutch(){
        return freewheelClutch;
    }
    public boolean doesGenerateOverstressClutch(){
        return overstressClutch;
    }
    public boolean doesGenerateFluidVessel(){
        return fluidVessel;
    }


    @Nullable
    public Supplier<? extends Block> getCasingSupplier() {
        return casingBlock;
    }

    @Nullable
    public Block getCasing() {
        return casingBlock == null ? null : casingBlock.get();
    }

    @Nullable
    public Supplier<? extends Block> getChainCogwheelSupplier() {
        return chainCogwheelBlock;
    }

    @Nullable
    public Supplier<? extends Block> getBrakeSupplier() {
        return brakeBlock;
    }

    @Nullable
    public Supplier<? extends Block> getParallelGearboxSupplier() {
        return parallelGearboxBlock;
    }

    @Nullable
    public Supplier<? extends Block> getSixWayGearboxSupplier() {
        return sixWayGearboxBlock;
    }

    @Nullable
    public Supplier<? extends Block> getInvertedGearShiftSupplier() {
        return invertedGearShiftBlock;
    }

    @Nullable
    public Supplier<? extends Block> getInvertedClutchSupplier() {
        return invertedClutchBlock;
    }

    @Nullable
    public Supplier<? extends Block> getCentrifugalClutchSupplier() {
        return centrifugalClutchBlock;
    }

    @Nullable
    public Supplier<? extends Block> getFreewheelClutchSupplier() {
        return freewheelClutchBlock;
    }

    @Nullable
    public Supplier<? extends Block> getOverstressClutchSupplier() {
        return overstressClutchBlock;
    }

    @Nullable
    public Supplier<? extends Block> getFluidVesselSupplier() {
        return fluidVesselBlock;
    }

    @Nullable
    public Supplier<? extends BlockItem> getVerticalParallelGearboxItemSupplier() {
        return verticalParallelGearboxBlockItem;
    }

    @Nullable
    public Supplier<? extends BlockItem> getVerticalSixWayGearboxItemSupplier() {
        return verticalSixWayGearboxBlockItem;
    }

    @Nullable
    public BlockItem getVerticalParallelGearboxItem() {
        return verticalParallelGearboxBlockItem == null ? null : verticalParallelGearboxBlockItem.get();
    }

    @Nullable
    public BlockItem getVerticalSixWayGearboxItem() {
        return verticalSixWayGearboxBlockItem == null ? null : verticalSixWayGearboxBlockItem.get();
    }

    @Nullable
    public Block getChainCogwheel() {
        return chainCogwheelBlock == null ? null : chainCogwheelBlock.get();
    }

    @Nullable
    public Block getBrake() {
        return brakeBlock == null ? null : brakeBlock.get();
    }

    @Nullable
    public Block getParallelGearbox() {
        return parallelGearboxBlock == null ? null : parallelGearboxBlock.get();
    }

    @Nullable
    public Block getSixWayGearbox() {
        return sixWayGearboxBlock == null ? null : sixWayGearboxBlock.get();
    }

    @Nullable
    public Block getInvertedGearShift() {
        return invertedGearShiftBlock == null ? null : invertedGearShiftBlock.get();
    }

    @Nullable
    public Block getInvertedClutch() {
        return invertedClutchBlock == null ? null : invertedClutchBlock.get();
    }

    @Nullable
    public Block getCentrifugalClutch() {
        return centrifugalClutchBlock == null ? null : centrifugalClutchBlock.get();
    }

    @Nullable
    public Block getFreewheelClutch() {
        return freewheelClutchBlock == null ? null : freewheelClutchBlock.get();
    }

    @Nullable
    public Block getOverstressClutch() {
        return overstressClutchBlock == null ? null : overstressClutchBlock.get();
    }

    @Nullable
    public Block getFluidVessel() {
        return fluidVesselBlock == null ? null : fluidVesselBlock.get();
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

    public void setCasing(@Nonnull Supplier<? extends Block> casing){
        if (getCasingSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a casing that has already been referenced");
        casingBlock = casing;
    }

    public void setChainCogwheel(@Nonnull Supplier<? extends Block> chainCogwheel){
        if (getChainCogwheelSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a chain wheel that has already been referenced");
        chainCogwheelBlock = chainCogwheel;
    }

    public void setBrake(@Nonnull Supplier<? extends Block> brake){
        if (getBrakeSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a brake that has already been referenced");
        brakeBlock = brake;
    }

    public void setParallelGearbox(@Nonnull Supplier<? extends Block> parallelGearbox){
        if (getParallelGearboxSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a parallel gearbox that has already been referenced");
        parallelGearboxBlock = parallelGearbox;
    }

    public void setSixWayGearbox(@Nonnull Supplier<? extends Block> sixWayGearbox){
        if (getSixWayGearboxSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a six way gearbox that has already been referenced");
        sixWayGearboxBlock = sixWayGearbox;
    }

    public void setInvertedGearShift(@Nonnull Supplier<? extends Block> invertedGearShift){
        if (getInvertedGearShiftSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a inverted gearshift that has already been referenced");
        invertedGearShiftBlock = invertedGearShift;
    }

    public void setInvertedClutch(@Nonnull Supplier<? extends Block> invertedClutch){
        if (getInvertedClutchSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a inverted clutch that has already been referenced");
        invertedClutchBlock = invertedClutch;
    }

    public void setCentrifugalClutch(@Nonnull Supplier<? extends Block> centrifugalClutch){
        if (getCentrifugalClutchSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a centrifugal clutch that has already been referenced");
        centrifugalClutchBlock = centrifugalClutch;
    }

    public void setFreewheelClutch(@Nonnull Supplier<? extends Block> freewheelClutch){
        if (getFreewheelClutchSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a freewheel clutch that has already been referenced");
        freewheelClutchBlock = freewheelClutch;
    }

    public void setOverstressClutch(@Nonnull Supplier<? extends Block> overstressClutch){
        if (getOverstressClutchSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a overstress clutch that has already been referenced");
        overstressClutchBlock = overstressClutch;
    }

    public void setFluidVessel(@Nonnull Supplier<? extends Block> fluidVessel){
        if (getFluidVesselSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a overstress clutch that has already been referenced");
        fluidVesselBlock = fluidVessel;
    }

    public void setVerticalParallelGearboxItem(@Nonnull Supplier<? extends BlockItem> gearbox){
        if (getVerticalParallelGearboxItemSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a vertical gearbox item that has already been referenced");
        verticalParallelGearboxBlockItem = gearbox;
    }

    public void setVerticalSixWayGearboxItem(@Nonnull Supplier<? extends BlockItem> gearbox){
        if (getVerticalSixWayGearboxItemSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a vertical gearbox item that has already been referenced");
        verticalSixWayGearboxBlockItem = gearbox;
    }

    @Nullable
    public CTSpriteShiftEntry getConnectedTextureSprite() {
        return ctSprite.get();
    }

    public boolean isInSet(Block block) {
        return block.equals(getChainCogwheel()) || block.equals(getBrake())
                || block.equals(getParallelGearbox()) || block.equals(getSixWayGearbox())
                || block.equals(getInvertedGearShift()) || block.equals(getInvertedClutch())
                || block.equals(getCentrifugalClutch()) || block.equals(getFreewheelClutch())
                || block.equals(getOverstressClutch());

    }

    public static class Options extends CasingSet.Options {
        private @Nullable Supplier<CTSpriteShiftEntry> ctSprite;
        private Supplier<CTSpriteShiftEntry> tankSideCtSprite = ()->null;
        private Supplier<CTSpriteShiftEntry> tankTopCtSprite = ()->null;
        private Supplier<CTSpriteShiftEntry> tankInnerCtSprite = ()->null;
        private boolean casing;
        private boolean chainCogwheel;
        private boolean brake;
        private boolean parallelGearbox;
        private boolean sixWayGearbox;
        private boolean invertedGearShift;
        private boolean invertedClutch;
        private boolean centrifugalClutch;
        private boolean freewheelClutch;
        private boolean overstressClutch;
        private boolean fluidVessel;

        private @Nullable Supplier<? extends BlockItem> existingVerticalParallelGearboxItem;
        private @Nullable Supplier<? extends BlockItem> existingVerticalSixWayGearboxItem;
        private @Nullable Supplier<? extends Block> existingCasing;
        private @Nullable Supplier<? extends Block> existingChainCogwheel;
        private @Nullable Supplier<? extends Block> existingBrake;
        private @Nullable Supplier<? extends Block> existingParallelGearbox;
        private @Nullable Supplier<? extends Block> existingSixWayGearbox;
        private @Nullable Supplier<? extends Block> existingInvertedGearShift;
        private @Nullable Supplier<? extends Block> existingInvertedClutch;
        private @Nullable Supplier<? extends Block> existingCentrifugalClutch;
        private @Nullable Supplier<? extends Block> existingFreewheelClutch;
        private @Nullable Supplier<? extends Block> existingOverstressClutch;
        private @Nullable Supplier<? extends Block> existingFluidVessel;

        public Options() {
            ctSprite = null;
            existingCasing = null;
            casing = false;
        }

        public Options ctSprite(Supplier<CTSpriteShiftEntry> ctSprite) {
            Preconditions.checkNotNull(ctSprite,"Connected Texture Sprite Supplier can't be null");
            this.ctSprite = ctSprite;
            return this;
        }

        public Options casing() {
            Preconditions.checkState(existingCasing == null,"Cannot create a casing if an existing casing was already set");
            this.casing = true;
            return this;
        }

        public Options chainCogwheel(){
            this.chainCogwheel = true;
            return this;
        }

        public Options brake(){
            this.brake = true;
            return this;
        }

        public Options parallelGearbox(){
            this.parallelGearbox = true;
            return this;
        }

        public Options sixWayGearbox(){
            this.sixWayGearbox = true;
            return this;
        }

        public Options invertedGearShift(){
            this.invertedGearShift = true;
            return this;
        }

        public Options invertedClutch(){
            this.invertedClutch = true;
            return this;
        }

        public Options centrifugalClutch(){
            this.centrifugalClutch = true;
            return this;
        }

        public Options freewheelClutch(){
            this.freewheelClutch = true;
            return this;
        }

        public Options overstressClutch(){
            this.overstressClutch = true;
            return this;
        }

        public Options fluidVessel(Supplier<CTSpriteShiftEntry> tankSideCtSprite, Supplier<CTSpriteShiftEntry> tankTopCtSprite, Supplier<CTSpriteShiftEntry> tankInnerCtSprite){
            this.tankSideCtSprite = tankSideCtSprite;
            this.tankTopCtSprite = tankTopCtSprite;
            this.tankInnerCtSprite = tankInnerCtSprite;
            this.fluidVessel = true;
            return this;
        }

        public Options kineticBlocks(){
            return chainCogwheel().brake().invertedGearShift();
        }

        public Options ClutchBlocks(){
            return invertedClutch().centrifugalClutch().freewheelClutch().overstressClutch();
        }

        public Options GearboxBlocks(){
            return parallelGearbox().sixWayGearbox();
        }

        public Options FluidBlocks(Supplier<CTSpriteShiftEntry> tankSideCtSprite, Supplier<CTSpriteShiftEntry> tankTopCtSprite, Supplier<CTSpriteShiftEntry> tankInnerCtSprite){
            return fluidVessel(tankSideCtSprite, tankTopCtSprite, tankInnerCtSprite);
        }

        public Options existingCasing(Supplier<? extends Block> casing) {
            this.existingCasing = casing;
            this.casing = false;
            return this;
        }

        Options existingChainCogwheel(Supplier<? extends Block> chainCogwheel) {
            existingChainCogwheel = chainCogwheel;
            this.chainCogwheel = false;
            return this;
        }

        Options existingBrake(Supplier<? extends Block> brake) {
            existingBrake = brake;
            this.brake = false;
            return this;
        }

        Options existingParallelGearbox(Supplier<? extends Block> parallelGearbox) {
            existingParallelGearbox = parallelGearbox;
            this.parallelGearbox = false;
            return this;
        }

        Options existingSixWayGearbox(Supplier<? extends Block> sixWayGearbox) {
            existingSixWayGearbox = sixWayGearbox;
            this.sixWayGearbox = false;
            return this;
        }

        Options existingInvertedGearShift(Supplier<? extends Block> invertedGearShift) {
            existingInvertedGearShift = invertedGearShift;
            this.invertedGearShift = false;
            return this;
        }

        Options existingInvertedClutch(Supplier<? extends Block> invertedClutch) {
            existingInvertedClutch = invertedClutch;
            this.invertedClutch = false;
            return this;
        }

        Options existingCentrifugalClutch(Supplier<? extends Block> centrifugalClutch) {
            existingCentrifugalClutch = centrifugalClutch;
            this.centrifugalClutch = false;
            return this;
        }

        Options existingFreewheelClutch(Supplier<? extends Block> freewheelClutch) {
            existingFreewheelClutch = freewheelClutch;
            this.freewheelClutch = false;
            return this;
        }

        Options existingOverstressClutch(Supplier<? extends Block> overstressClutch) {
            existingOverstressClutch = overstressClutch;
            this.overstressClutch = false;
            return this;
        }

        Options existingFluidVessel(Supplier<? extends Block> fluidVessel) {
            existingFluidVessel = fluidVessel;
            this.fluidVessel = false;
            return this;
        }

    }
}
