package net.Rarin.create_connected_encased;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import fr.iglee42.createcasing.mixins.create.fluids.FluidTankBlockEntityAccessor;
import net.Rarin.create_connected_encased.config.CCEConfigs;
import net.Rarin.create_connected_encased.registries.*;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(CCEncased.ID)
public class CCEncased {

    public static final String ID = "create_connected_encased";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static IEventBus modEventBus;
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    public static List<ItemLike> hidedItems = new ArrayList<>();

    public CCEncased(IEventBus eventBus, ModContainer modContainer) {
        modEventBus = eventBus;
        REGISTRATE.registerEventListeners(eventBus);

        CCEncasedBlocks.register();
        CCEncasedItems.register();
        CCEncasedBlockEntities.register();
        CCEncasedCreativeModeTabs.register(eventBus);
        CCEConfigs.register(modContainer);

        modEventBus.addListener(EventPriority.HIGHEST, CCEncasedDatagen::gatherDataHighPriority);
        modEventBus.addListener(EventPriority.LOWEST, CCEncasedDatagen::gatherData);

        modEventBus.addListener(this::registerCapabilities);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                CCEncasedBlockEntities.FLUID_VESSEL.get(),
                (be, context) -> {
                    FluidTankBlockEntityAccessor accessor = (FluidTankBlockEntityAccessor) be;
                    if (accessor.encased$getFluidCapability() == null)
                        accessor.encased$refreshCapability();
                    return accessor.encased$getFluidCapability();
                }
        );
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }


}
