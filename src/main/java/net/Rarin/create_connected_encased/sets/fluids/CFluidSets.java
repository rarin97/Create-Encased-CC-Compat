package net.Rarin.create_connected_encased.sets.fluids;

import com.google.common.collect.ImmutableList;
import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import fr.iglee42.createcasing.registries.EncasedPartialModels;
import fr.iglee42.createcasing.registries.EncasedSprites;
import fr.iglee42.createcasing.registries.EncasedTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class CFluidSets {

    private static final List<CFluidSet> sets = new ArrayList<>();

    public static final CFluidSet ANDESITE = register("andesite", options -> options
            .item(EncasedTags.EItemTags.ANDESITE_PLATES.tag)
            .FluidBlocks(() -> EncasedSprites.ANDESITE_FLUID_TANK, () -> EncasedSprites.ANDESITE_FLUID_TANK_TOP, () -> EncasedSprites.ANDESITE_FLUID_TANK_INNER,
                    () -> EncasedPartialModels.ANDESITE_GAUGE, () -> EncasedPartialModels.ANDESITE_GAUGE_DIAL)
    );

    public static final CFluidSet BRASS = register("brass", options -> options
            .item(CommonMetal.BRASS.plates)
            .FluidBlocks(() -> EncasedSprites.BRASS_FLUID_TANK, () -> EncasedSprites.BRASS_FLUID_TANK_TOP, () -> EncasedSprites.BRASS_FLUID_TANK_INNER,
                    () -> EncasedPartialModels.BRASS_GAUGE, () -> EncasedPartialModels.BRASS_GAUGE_DIAL)
    );


    public static final CFluidSet COPPER = register("copper", options -> options
            .item(CommonMetal.COPPER.plates)
            .existingVessel(CCBlocks.FLUID_VESSEL)
    );

    public static final CFluidSet ZINC = register("zinc", options -> options
            .item(CommonMetal.ZINC.plates)
            .FluidBlocks(() -> EncasedSprites.ZINC_FLUID_TANK, () -> EncasedSprites.ZINC_FLUID_TANK_TOP, () -> EncasedSprites.ZINC_FLUID_TANK_INNER,
                    () -> EncasedPartialModels.ZINC_GAUGE, () -> EncasedPartialModels.ZINC_GAUGE_DIAL)
    );

    public static CFluidSet register(String id, UnaryOperator<CFluidSet.Options> options) {
        String name = id.toLowerCase(Locale.ROOT);
        if (sets.stream().anyMatch(set -> set.getName().equalsIgnoreCase(name)))
            throw new IllegalArgumentException("A fluid set with name `" + name + "` already exists !");
        CFluidSet set = new CFluidSet(name, options.apply(new CFluidSet.Options()));
        sets.add(set);
        return set;
    }

    public static List<CFluidSet> getSets() {
        return ImmutableList.copyOf(sets);
    }
}
