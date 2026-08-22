package net.Rarin.create_connected_encased.registries;

import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.ponder.*;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.Rarin.create_connected_encased.casings.CCasingSet;
import net.Rarin.create_connected_encased.casings.CCasingSets;
import net.createmod.ponder.api.registration.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class CCEncasedPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return CreateConnected.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        register(helper);
    }

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemLike> SCENE_HELPER = helper.withKeyFunction(like-> BuiltInRegistries.ITEM.getKey(like.asItem()));

        SCENE_HELPER.forComponents(CCasingSets.getSets().stream().filter(CCasingSet::doesGenerateChainCogwheel).map(CCasingSet::getChainCogwheel).toList())
                .addStoryBoard("chain_cogwheel", ChainCogwheelScenes::chainCogwheelAsRelay, AllCreatePonderTags.KINETIC_RELAYS);
        SCENE_HELPER.forComponents(CCasingSets.getSets().stream().filter(CCasingSet::doesGenerateInvertedClutch).map(CCasingSet::getInvertedClutch).toList())
                .addStoryBoard("inverted_clutch", InvertedClutchScenes::invertedClutch, AllCreatePonderTags.KINETIC_RELAYS);
        SCENE_HELPER.forComponents(CCasingSets.getSets().stream().filter(CCasingSet::doesGenerateInvertedGearShift).map(CCasingSet::getInvertedGearShift).toList())
                .addStoryBoard("inverted_gearshift", InvertedGearshiftScenes::invertedGearshift, AllCreatePonderTags.KINETIC_RELAYS);
        SCENE_HELPER.forComponents(CCasingSets.getSets().stream().filter(CCasingSet::doesGenerateParallelGearbox).map(CCasingSet::getParallelGearbox).toList())
                .addStoryBoard("parallel_gearbox", ParallelGearboxScenes::parallelGearbox, AllCreatePonderTags.KINETIC_RELAYS);
        SCENE_HELPER.forComponents(CCasingSets.getSets().stream().filter(CCasingSet::doesGenerateParallelGearbox).map(CCasingSet::getVerticalParallelGearboxItem).toList())
                .addStoryBoard("parallel_gearbox", ParallelGearboxScenes::parallelGearbox, AllCreatePonderTags.KINETIC_RELAYS);
    }
}
