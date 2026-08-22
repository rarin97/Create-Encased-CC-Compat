package net.Rarin.create_connected_encased.casings;

import com.google.common.collect.ImmutableList;
import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import fr.iglee42.createcasing.casings.CasingSets;
import fr.iglee42.createcasing.registries.EncasedSprites;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CCasingSets {

    private static final List<CCasingSet> sets = new ArrayList<CCasingSet>();

    public static final CCasingSet ANDESITE = register("andesite",new CCasingSet.Options()
            .ctSprite(()-> AllSpriteShifts.ANDESITE_CASING)
            .existingCasing(()-> AllBlocks.ANDESITE_CASING.get())
            .existingChainCogwheel(()-> CCBlocks.ENCASED_CHAIN_COGWHEEL.get())
            .existingBrake(()-> CCBlocks.BRAKE.get())
            .existingParallelGearbox(()-> CCBlocks.PARALLEL_GEARBOX.get())
            .existingSixWayGearbox(()-> CCBlocks.SIX_WAY_GEARBOX.get())
            .existingInvertedGearShift(()-> CCBlocks.INVERTED_GEARSHIFT.get())
            .existingInvertedClutch(()-> CCBlocks.INVERTED_CLUTCH.get())
            .existingCentrifugalClutch(()-> CCBlocks.CENTRIFUGAL_CLUTCH.get())
            .existingFreewheelClutch(()-> CCBlocks.FREEWHEEL_CLUTCH.get())
            .existingOverstressClutch(()-> CCBlocks.OVERSTRESS_CLUTCH.get())
            //.FluidBlocks(()-> EncasedSprites.ANDESITE_FLUID_TANK,()->EncasedSprites.ANDESITE_FLUID_TANK_TOP,()->EncasedSprites.ANDESITE_FLUID_TANK_INNER)
    );

    public static final CCasingSet BRASS = register("brass",new CCasingSet.Options()
            .ctSprite(()->AllSpriteShifts.BRASS_CASING)
            .existingCasing(()-> AllBlocks.BRASS_CASING.get())
            .kineticBlocks()
            .parallelGearbox()
            .sixWayGearbox()
            .ClutchBlocks()
            //.FluidBlocks(()-> EncasedSprites.BRASS_FLUID_TANK,()->EncasedSprites.BRASS_FLUID_TANK_TOP,()->EncasedSprites.BRASS_FLUID_TANK_INNER)
    );

    public static final CCasingSet COPPER = register("copper",new CCasingSet.Options()
            .ctSprite(()->AllSpriteShifts.COPPER_CASING)
            .existingCasing(()->AllBlocks.COPPER_CASING.get())
            .existingFluidVessel(CCBlocks.FLUID_VESSEL)
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet RAILWAY = register("railway",new CCasingSet.Options()
            .ctSprite(()->AllSpriteShifts.RAILWAY_CASING)
            .existingCasing(()->AllBlocks.RAILWAY_CASING.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
            //.FluidBlocks(()-> CCEncasedSprites.RAILWAY_FLUID_TANK,()-> CCEncasedSprites.RAILWAY_FLUID_TANK_INNER,()->CCEncasedSprites.RAILWAY_FLUID_TANK_TOP)
    );

    public static final CCasingSet SHADOW_STEEL = register("shadow_steel",new CCasingSet.Options()
            .ctSprite(()->AllSpriteShifts.SHADOW_STEEL_CASING)
            .existingCasing(()->AllBlocks.SHADOW_STEEL_CASING.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
            //.FluidBlocks(()-> CCEncasedSprites.SHADOW_STEEL_FLUID_TANK,()-> CCEncasedSprites.SHADOW_STEEL_FLUID_TANK_INNER,()->CCEncasedSprites.SHADOW_STEEL_FLUID_TANK_TOP)
    );

    public static final CCasingSet REFINED_RADIANCE = register("refined_radiance",new CCasingSet.Options()
            .ctSprite(()->AllSpriteShifts.REFINED_RADIANCE_CASING)
            .existingCasing(()->AllBlocks.REFINED_RADIANCE_CASING.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
            //.FluidBlocks(()-> CCEncasedSprites.REFINED_RADIANCE_FLUID_TANK,()-> CCEncasedSprites.REFINED_RADIANCE_FLUID_TANK_INNER,()->CCEncasedSprites.REFINED_RADIANCE_FLUID_TANK_TOP)
    );

    public static final CCasingSet CREATIVE = register("creative",new CCasingSet.Options()
            .ctSprite(()->AllSpriteShifts.CREATIVE_CASING)
            .existingCasing(() -> CasingSets.CREATIVE.getCasing())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet INDUSTRIAL_IRON = register("industrial_iron",new CCasingSet.Options()
            .ctSprite(()->null)
            .existingCasing(()->AllBlocks.INDUSTRIAL_IRON_BLOCK.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
            //.FluidBlocks(()-> CCEncasedSprites.INDUSTRIAL_IRON_FLUID_TANK,()->CCEncasedSprites.INDUSTRIAL_IRON_FLUID_TANK_TOP,()->CCEncasedSprites.INDUSTRIAL_IRON_FLUID_TANK_INNER)
    );

    public static final CCasingSet WEATHERED_IRON = register("weathered_iron",new CCasingSet.Options()
            .ctSprite(()->null)
            .existingCasing(()->AllBlocks.WEATHERED_IRON_BLOCK.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
            //.FluidBlocks(()-> CCEncasedSprites.WEATHERED_IRON_FLUID_TANK,()->CCEncasedSprites.WEATHERED_IRON_FLUID_TANK_TOP,()->CCEncasedSprites.WEATHERED_IRON_FLUID_TANK_INNER)
    );

    public static final CCasingSet ZINC = register("zinc", new CCasingSet.Options()
            .ctSprite(()-> EncasedSprites.ZINC_CASING)
            .existingCasing(()->CasingSets.ZINC.getCasing())
            .GearboxBlocks()
            //.FluidBlocks(()-> EncasedSprites.ZINC_FLUID_TANK,()->EncasedSprites.ZINC_FLUID_TANK_TOP,()->EncasedSprites.ZINC_FLUID_TANK_INNER)
    );

//        public static final CCasingSet INDUSTRIAL = register("industrial", new CCasingSet.Options()
//            .ctSprite(() -> omni("industrial"))
//            .existingCasing(()-> DesiresBlocks.INDUSTRIAL_CASING.get())
//            .GearboxBlocks()
//    );

    public static CCasingSet register(String id, CCasingSet.Options options){
        String name = id.toLowerCase(Locale.ROOT);
        if (sets.stream().anyMatch(set->set.getName().equalsIgnoreCase(name)))
            throw new IllegalArgumentException("A casing set with name `" + name + "` already exists !");
        CCasingSet set = new CCasingSet(name,options);
        sets.add(set);
        return set;
    }

    public static List<CCasingSet> getSets() {
        return ImmutableList.copyOf(sets);
    }
}
