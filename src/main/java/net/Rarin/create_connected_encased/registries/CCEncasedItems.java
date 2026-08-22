package net.Rarin.create_connected_encased.registries;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.Rarin.create_connected_encased.casings.CCasingSets;
import net.Rarin.create_connected_encased.items.CustomVerticalParallelGearboxItem;
import net.Rarin.create_connected_encased.items.CustomVerticalSixWayGearboxItem;
import net.minecraft.world.item.Item;

import java.util.Objects;

import static net.Rarin.create_connected_encased.CCEncased.REGISTRATE;
import static net.Rarin.create_connected_encased.registries.CCEncasedBlockStateGens.*;


public class CCEncasedItems {

    static {
        REGISTRATE.setCreativeTab(CCEncasedCreativeModeTabs.MAIN_TAB);
    }

    public static ItemEntry<CustomVerticalParallelGearboxItem> createVerticalParallelGearboxItem(String name, NonNullFunction<Item.Properties, CustomVerticalParallelGearboxItem> function){
        return REGISTRATE.item(name + "_vertical_parallel_gearbox", function)
                .model((ctx,prov)->prov.getBuilder(ctx.getName()).parent(Objects.requireNonNull(parallelGearboxModel(prov, name, "item_vertical"))))
                .register();
    }

    public static ItemEntry<CustomVerticalSixWayGearboxItem> createVerticalSixWayGearboxItem(String name, NonNullFunction<Item.Properties, CustomVerticalSixWayGearboxItem> function){
        return REGISTRATE.item(name + "_vertical_six_way_gearbox", function)
                .model((ctx,prov)->prov.getBuilder(ctx.getName()).parent(Objects.requireNonNull(sixWayGearboxModel(prov, name, "item_vertical"))))
                .register();
    }

    public static void register(){
        CCasingSets.getSets().forEach(set->{

            if (set.doesGenerateParallelGearbox())
                set.setVerticalParallelGearboxItem(createVerticalParallelGearboxItem(set.getName(),p->new CustomVerticalParallelGearboxItem(p,set.getParallelGearbox())));

            if (set.doesGenerateSixWayGearbox())
                set.setVerticalSixWayGearboxItem(createVerticalSixWayGearboxItem(set.getName(),p->new CustomVerticalSixWayGearboxItem(p,set.getSixWayGearbox())));

        });
    }
}
