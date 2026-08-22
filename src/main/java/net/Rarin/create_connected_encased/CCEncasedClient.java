package net.Rarin.create_connected_encased;

import net.Rarin.create_connected_encased.registries.CCEncasedPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static net.Rarin.create_connected_encased.CCEncased.modEventBus;

@Mod(value = CCEncased.ID, dist = Dist.CLIENT)
public class CCEncasedClient {

    public CCEncasedClient() {
        modEventBus.addListener(CCEncasedClient::init);
    }

    public static void init(final FMLClientSetupEvent event) {

        PonderIndex.addPlugin(new CCEncasedPonderPlugin());
    }
}

