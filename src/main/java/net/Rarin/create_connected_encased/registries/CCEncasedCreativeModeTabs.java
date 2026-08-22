package net.Rarin.create_connected_encased.registries;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import fr.iglee42.createcasing.registries.EncasedCreativeModeTabs;
import it.unimi.dsi.fastutil.objects.*;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.casings.CCasingSet;
import net.Rarin.create_connected_encased.casings.CCasingSets;
import net.Rarin.create_connected_encased.client.ClientItemPredicates;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CCEncasedCreativeModeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CCEncased.ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + CCEncased.ID + ".base"))
                    .withTabsBefore(EncasedCreativeModeTabs.MAIN_TAB.getKey())
                    .icon(() -> CCasingSets.BRASS.getChainCogwheel().asItem().getDefaultInstance())
                    //.displayItems(new ItemDisplay())
                    .displayItems(new CCEncasedCreativeModeTabs.RegistrateDisplayItemsGenerator(true, CCEncasedCreativeModeTabs.MAIN_TAB))
                    .build());

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }

//    public static class ItemDisplay implements CreativeModeTab.DisplayItemsGenerator {
//
//        @Override
//        public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
//            CCEncased.REGISTRATE.getAll(Registries.ITEM)
//                    .forEach(entry -> output.accept(entry.get()));
//        }
//    }
//
//}

    public static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
        private static final Predicate<Item> IS_ITEM_3D_PREDICATE;

        static {
            MutableObject<Predicate<Item>> isItem3d = new MutableObject<>(item -> false);
            CatnipServices.PLATFORM.executeOnClientOnly(
                    () -> () -> isItem3d.setValue(ClientItemPredicates.make3dItemPredicate()));
            IS_ITEM_3D_PREDICATE = isItem3d.getValue();
        }

        private final boolean addItems;
        private final DeferredHolder<CreativeModeTab, CreativeModeTab> tabFilter;

        public RegistrateDisplayItemsGenerator(boolean addItems, DeferredHolder<CreativeModeTab, CreativeModeTab> tabFilter) {
            this.addItems = addItems;
            this.tabFilter = tabFilter;
        }

        private static Predicate<Item> makeExclusionPredicate() {
            Set<Item> exclusions = new ReferenceOpenHashSet<>();
            exclusions.addAll(CCEncased.hidedItems.stream().map(ItemLike::asItem).toList());
            // exclusions.add(TransmissionSets.MLDEG.getShaft().asItem());
            return exclusions::contains;
        }

        private static List<ItemOrdering> makeOrderings() {
            List<ItemOrdering> orderings = new ReferenceArrayList<>();

            Map<ItemProviderEntry<?, ?>, ItemProviderEntry<?, ?>> simpleBeforeOrderings = Map.of(

            );

            Map<Supplier<? extends Item>, Supplier<? extends Item>> simpleAfterOrderings = new HashMap<>(Map.of(
            ));

            CCasingSets.getSets().stream().filter(CCasingSet::doesGenerateChainCogwheel).forEach(set -> simpleAfterOrderings.put(set.getVerticalParallelGearboxItemSupplier(), () -> set.getParallelGearbox().asItem()));
            CCasingSets.getSets().stream().filter(CCasingSet::doesGenerateChainCogwheel).forEach(set -> simpleAfterOrderings.put(set.getVerticalSixWayGearboxItemSupplier(), () -> set.getSixWayGearbox().asItem()));

            simpleBeforeOrderings.forEach((entry, otherEntry) -> {
                orderings.add(ItemOrdering.before(entry.asItem(), otherEntry.asItem()));
            });

            simpleAfterOrderings.forEach((entry, otherEntry) -> {
                orderings.add(ItemOrdering.after(entry.get(), otherEntry.get()));
            });

            return orderings;
        }

        private static Function<Item, ItemStack> makeStackFunc() {
            Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>();

            Map<ItemProviderEntry<?, ?>, Function<Item, ItemStack>> simpleFactories = Map.of(

            );

            simpleFactories.forEach((entry, factory) -> {
                factories.put(entry.asItem(), factory);
            });

            return item -> {
                Function<Item, ItemStack> factory = factories.get(item);
                if (factory != null) {
                    return factory.apply(item);
                }
                return new ItemStack(item);
            };
        }

        private static Function<Item, CreativeModeTab.TabVisibility> makeVisibilityFunc() {
            Map<Item, CreativeModeTab.TabVisibility> visibilities = new Reference2ObjectOpenHashMap<>();

            Map<ItemProviderEntry<?, ?>, CreativeModeTab.TabVisibility> simpleVisibilities = Map.of(
            );

            simpleVisibilities.forEach((entry, factory) -> {
                visibilities.put(entry.asItem(), factory);
            });


            return item -> {
                CreativeModeTab.TabVisibility visibility = visibilities.get(item);
                if (visibility != null) {
                    return visibility;
                }
                return CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
            };
        }

        private static void applyOrderings(List<Item> items, List<ItemOrdering> orderings) {
            for (ItemOrdering ordering : orderings) {
                int anchorIndex = items.indexOf(ordering.anchor());
                if (anchorIndex != -1) {
                    Item item = ordering.item();
                    int itemIndex = items.indexOf(item);
                    if (itemIndex != -1) {
                        items.remove(itemIndex);
                        if (itemIndex < anchorIndex) {
                            anchorIndex--;
                        }
                    }
                    if (ordering.type() == ItemOrdering.Type.AFTER) {
                        items.add(anchorIndex + 1, item);
                    } else {
                        items.add(anchorIndex, item);
                    }
                }
            }
        }

        private static void outputAll(CreativeModeTab.Output output, List<Item> items, Function<Item, ItemStack> stackFunc, Function<Item, CreativeModeTab.TabVisibility> visibilityFunc) {
            for (Item item : items) {
                output.accept(stackFunc.apply(item), visibilityFunc.apply(item));
            }
        }

        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters pParameters, CreativeModeTab.Output output) {
            if (EffectiveSide.get().isServer()) return;
            if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) return;
            Predicate<Item> exclusionPredicate = makeExclusionPredicate();
            List<ItemOrdering> orderings = makeOrderings();
            Function<Item, ItemStack> stackFunc = makeStackFunc();
            Function<Item, CreativeModeTab.TabVisibility> visibilityFunc = makeVisibilityFunc();
            DeferredHolder<CreativeModeTab, CreativeModeTab> tab = MAIN_TAB;


            List<Item> items = new LinkedList<>();
            if (addItems) {
                items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE.negate())));
            }
            items.addAll(collectBlocks(exclusionPredicate));
            if (addItems) {
                items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE)));
            }

            applyOrderings(items, orderings);
            outputAll(output, items, stackFunc, visibilityFunc);
        }

        private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
            List<Item> items = new ReferenceArrayList<>();
            for (RegistryEntry<Block, Block> entry : CCEncased.REGISTRATE.getAll(Registries.BLOCK)) {
                if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
                    continue;
                Item item = entry.get()
                        .asItem();
                if (item == Items.AIR)
                    continue;
                if (!exclusionPredicate.test(item))
                    items.add(item);
            }
            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }

        private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
            List<Item> items = new ReferenceArrayList<>();
            for (RegistryEntry<Item, Item> entry : CCEncased.REGISTRATE.getAll(Registries.ITEM)) {
                if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
                    continue;
                Item item = entry.get();
                if (item instanceof BlockItem)
                    continue;
                if (!exclusionPredicate.test(item))
                    items.add(item);
            }
            return items;
        }

        private record ItemOrdering(Item item, Item anchor, ItemOrdering.Type type) {
            public static ItemOrdering before(Item item, Item anchor) {
                return new ItemOrdering(item, anchor, ItemOrdering.Type.BEFORE);
            }

            public static ItemOrdering after(Item item, Item anchor) {
                return new ItemOrdering(item, anchor, ItemOrdering.Type.AFTER);
            }

            public enum Type {
                BEFORE,
                AFTER;
            }
        }
    }
}
