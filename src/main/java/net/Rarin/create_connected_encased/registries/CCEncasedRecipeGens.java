package net.Rarin.create_connected_encased.registries;

import com.google.common.base.Supplier;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.casings.CCasingSet;
import net.Rarin.create_connected_encased.casings.CCasingSets;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CCEncasedRecipeGens extends BaseRecipeProvider {

	final List<GeneratedRecipe> all = new ArrayList<>();


	/*
	 * End of recipe list
	 */

	String currentFolder = "";
	String lastFolder = "";

	void enterFolder(String folder) {
		currentFolder += (currentFolder.isEmpty() ? "" : "/") + folder;
		lastFolder = folder;
	}

	void leftLastFolder() {
		currentFolder = currentFolder.substring(0, currentFolder.length() - ("/" + lastFolder).length());
	}

	GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
		return new GeneratedRecipeBuilder("", result);
	}

	GeneratedRecipeBuilder create(String path, Supplier<ItemLike> result) {
		return new GeneratedRecipeBuilder(currentFolder + "/" + path, result);
	}


	GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike, ? extends ItemLike> result) {
		return create(result::get);
	}

	BaseRecipeProvider.GeneratedRecipe createSpecial(Function<CraftingBookCategory, Recipe<?>> builder, String recipeType,
													 String path) {
		ResourceLocation location = CCEncased.asResource(recipeType + "/" + currentFolder + "/" + path);
		return register(consumer -> {
			SpecialRecipeBuilder b = SpecialRecipeBuilder.special(builder);
			b.save(consumer, location.toString());
		});
	}


	@Override
	public void buildRecipes(RecipeOutput output) {
		all.forEach(c -> c.register(output));
		CCEncased.LOGGER.info("{} registered {} recipe{}", getName(), all.size(), all.size() == 1 ? "" : "s");
	}

	protected BaseRecipeProvider.GeneratedRecipe register(BaseRecipeProvider.GeneratedRecipe recipe) {
		all.add(recipe);
		return recipe;
	}

	class GeneratedRecipeBuilder {

		private String path;
		private String suffix;
		private Supplier<? extends ItemLike> result;
		List<ICondition> recipeConditions;

		private Supplier<ItemPredicate> unlockedBy;
		private int amount;

		private GeneratedRecipeBuilder(String path) {
			this.path = path;
			this.recipeConditions = new ArrayList<>();
			this.amount = 1;
			this.suffix = "";
		}

		public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
			this(path);
			this.result = result;
		}


		GeneratedRecipeBuilder returns(int amount) {
			this.amount = amount;
			return this;
		}

		GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
			this.unlockedBy = () -> ItemPredicate.Builder.item()
					.of(item.get())
					.build();
			return this;
		}

		GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
			this.unlockedBy = () -> ItemPredicate.Builder.item()
					.of(tag.get())
					.build();
			return this;
		}

		GeneratedRecipeBuilder whenModLoaded(String modid) {
			return withCondition(new ModLoadedCondition(modid));
		}

		GeneratedRecipeBuilder whenModMissing(String modid) {
			return withCondition(new NotCondition(new ModLoadedCondition(modid)));
		}

		GeneratedRecipeBuilder withCondition(ICondition condition) {
			recipeConditions.add(condition);
			return this;
		}

		GeneratedRecipeBuilder suffix(String suffix) {
			this.suffix = suffix;
			return this;
		}

		// FIXME 5.1 refactor - recipe categories as markers instead of sections?
		BaseRecipeProvider.GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
			return register(recipeOutput -> {
				ShapedRecipeBuilder b =
						builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), amount));
				if (unlockedBy != null)
					b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
				RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

				b.save(conditionalOutput, createLocation("crafting"));
			});
		}

		BaseRecipeProvider.GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
			return register(recipeOutput -> {
				ShapelessRecipeBuilder b =
						builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
				if (unlockedBy != null)
					b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

				RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

				b.save(conditionalOutput, createLocation("crafting"));
			});
		}

		BaseRecipeProvider.GeneratedRecipe viaNetheriteSmithing(Supplier<? extends Item> base, Supplier<Ingredient> upgradeMaterial) {
			return register(consumer -> {
				SmithingTransformRecipeBuilder b =
						SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(base.get()), upgradeMaterial.get(), RecipeCategory.COMBAT, result.get()
										.asItem());
				b.unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item()
						.of(base.get())
						.build()));
				b.save(consumer, createLocation("crafting"));
			});
		}

		private ResourceLocation createSimpleLocation(String recipeType) {
			return CCEncased.asResource(path + suffix);
		}

		private ResourceLocation createLocation(String recipeType) {
			if (path.isEmpty())
				return CCEncased.asResource(RegisteredObjectsHelper.getKeyOrThrow(result.get()) + suffix);
			return CCEncased.asResource(path + suffix);
		}

		private ResourceLocation getRegistryName() {
			return RegisteredObjectsHelper.getKeyOrThrow(result.get()
					.asItem());
		}

		GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
			return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
		}

		GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingTag(Supplier<TagKey<Item>> tag) {
			return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag.get()));
		}

		GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
			return new GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder(ingredient);
		}

		class GeneratedCookingRecipeBuilder {

			private Supplier<Ingredient> ingredient;
			private float exp;
			private int cookingTime;

			GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
				this.ingredient = ingredient;
				cookingTime = 200;
				exp = 0;
			}

			GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder forDuration(int duration) {
				cookingTime = duration;
				return this;
			}

			GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder rewardXP(float xp) {
				exp = xp;
				return this;
			}

			BaseRecipeProvider.GeneratedRecipe inFurnace() {
				return inFurnace(b -> b);
			}

			BaseRecipeProvider.GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				return create(RecipeSerializer.SMELTING_RECIPE, builder, SmeltingRecipe::new, 1);
			}

			BaseRecipeProvider.GeneratedRecipe inSmoker() {
				return inSmoker(b -> b);
			}

			BaseRecipeProvider.GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				create(RecipeSerializer.SMELTING_RECIPE, builder, SmeltingRecipe::new, 1);
				create(RecipeSerializer.CAMPFIRE_COOKING_RECIPE, builder, CampfireCookingRecipe::new, 3);
				return create(RecipeSerializer.SMOKING_RECIPE, builder, SmokingRecipe::new, .5f);
			}

			BaseRecipeProvider.GeneratedRecipe inBlastFurnace() {
				return inBlastFurnace(b -> b);
			}

			BaseRecipeProvider.GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				create(RecipeSerializer.SMELTING_RECIPE, builder, SmeltingRecipe::new, 1);
				return create(RecipeSerializer.BLASTING_RECIPE, builder, BlastingRecipe::new, .5f);
			}

			private <T extends AbstractCookingRecipe> BaseRecipeProvider.GeneratedRecipe create(RecipeSerializer<T> serializer,
																								UnaryOperator<SimpleCookingRecipeBuilder> builder, AbstractCookingRecipe.Factory<T> factory, float cookingTimeModifier) {
				return register(recipeOutput -> {

					SimpleCookingRecipeBuilder b = builder.apply(SimpleCookingRecipeBuilder.generic(ingredient.get(),
							RecipeCategory.MISC, result.get(), exp,
							(int) (cookingTime * cookingTimeModifier), serializer, factory));
					if (unlockedBy != null)
						b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

					RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

					b.save(
							conditionalOutput,
							createSimpleLocation(RegisteredObjectsHelper.getKeyOrThrow(serializer).getPath())
					);
				});
			}
		}
	}


	public CCEncasedRecipeGens(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, CCEncased.ID);
		enterFolder("crafting");


		createForSetElement("chain_cogwheel", CCasingSet::doesGenerateChainCogwheel, CCasingSet::getChainCogwheel, (builder, set) -> builder
				.viaShapeless(sh -> sh
						.requires(AllBlocks.ENCASED_CHAIN_DRIVE)
						.requires(AllBlocks.COGWHEEL)
				));

		createForSetElement("brake", CCasingSet::doesGenerateBrake, CCasingSet::getBrake, (builder, set) -> builder
				.viaShapeless(sh -> sh
						.requires(set.getCasing())
						.requires(AllBlocks.SHAFT)
						.requires(Blocks.REDSTONE_WIRE)
						.requires(Blocks.OBSIDIAN)
				));

		createForSetElement("overstress_clutch", CCasingSet::doesGenerateOverstressClutch, CCasingSet::getOverstressClutch, (builder, set) -> builder
				.viaShapeless(sh -> sh
						.requires(set.getCasing())
						.requires(AllBlocks.SHAFT)
						.requires(AllItems.IRON_SHEET)
						.requires(AllItems.ELECTRON_TUBE)
				));

		createForSetElement("centrifugal_clutch", CCasingSet::doesGenerateCentrifugalClutch, CCasingSet::getCentrifugalClutch, (builder, set) -> builder
				.viaShapeless(sh -> sh
						.requires(set.getCasing())
						.requires(AllBlocks.SHAFT)
						.requires(AllItems.IRON_SHEET)
						.requires(AllBlocks.SPEEDOMETER)
				));

		createForSetElement("freewheel_clutch", CCasingSet::doesGenerateFreewheelClutch, CCasingSet::getFreewheelClutch, (builder, set) -> builder
				.viaShapeless(sh -> sh
						.requires(set.getCasing())
						.requires(AllBlocks.SHAFT)
						.requires(AllItems.IRON_SHEET)
						.requires(AllBlocks.COGWHEEL)
				));

		createForSetElement("inverted_gearshift", CCasingSet::doesGenerateInvertedGearShift, CCasingSet::getInvertedGearShift, (builder, set) -> builder
				.viaShapeless(sh -> sh
						.requires(AllBlocks.GEARSHIFT)
				));

		createForSetElement("inverted_gearshift", CCasingSet::doesGenerateInvertedGearShift, CCasingSet::getInvertedGearShift, (builder, set) -> builder
				.suffix("_from_conversion").viaShapeless(sh -> sh
						.requires(set.getInvertedGearShift())
				));

		createForSetElement("inverted_clutch", CCasingSet::doesGenerateInvertedClutch, CCasingSet::getInvertedClutch, (builder, set) -> builder
				.viaShapeless(sh -> sh
						.requires(AllBlocks.CLUTCH)
				));

		createForSetElement("inverted_clutch", CCasingSet::doesGenerateInvertedClutch, CCasingSet::getInvertedClutch, (builder, set) -> builder
				.suffix("_from_conversion").viaShapeless(sh -> sh
						.requires(set.getInvertedClutch())
				));

		createForSetElement("parallel_gearbox", CCasingSet::doesGenerateParallelGearbox, CCasingSet::getParallelGearbox, (builder, set) -> builder
				.viaShapeless(sh -> sh
						//.requires(CasingSets.BRASS.getGearbox())
						.requires(AllBlocks.GEARBOX)
						.requires(AllBlocks.LARGE_COGWHEEL)
				));

		createForSetElement("parallel_gearbox", CCasingSet::doesGenerateParallelGearbox, CCasingSet::getParallelGearbox, (builder, set) -> builder
				.suffix("_from_conversion").viaShapeless(sh -> sh
						.requires(set.getVerticalParallelGearboxItem())
				));

		createForSetElement("parallel_gearbox", CCasingSet::doesGenerateParallelGearbox, CCasingSet::getVerticalParallelGearboxItem, (builder, set) -> builder
				.suffix("_vertical_from_conversion").viaShapeless(sh -> sh
						.requires(set.getParallelGearbox())
				));

		createForSetElement("six_way_gearbox", CCasingSet::doesGenerateSixWayGearbox, CCasingSet::getSixWayGearbox, (builder, set) -> builder
				.viaShaped(sh -> sh
						.define('c', AllBlocks.COGWHEEL)
						.define('l', AllBlocks.LARGE_COGWHEEL)
						.define('s', set.getCasing())
						.pattern("lc ")
						.pattern("csc")
						.pattern(" cl")
				));

		createForSetElement("six_way_gearbox", CCasingSet::doesGenerateSixWayGearbox, CCasingSet::getSixWayGearbox, (builder, set) -> builder
				.suffix("_from_gearbox").viaShapeless(sh -> sh
						.requires(AllBlocks.GEARBOX)
						.requires(AllBlocks.LARGE_COGWHEEL)
						.requires(AllBlocks.LARGE_COGWHEEL)
				));

		createForSetElement("six_way_gearbox", CCasingSet::doesGenerateSixWayGearbox, CCasingSet::getSixWayGearbox, (builder, set) -> builder
				.suffix("_from_parallel").viaShapeless(sh -> sh
						.requires(set.getParallelGearbox())
						.requires(AllBlocks.LARGE_COGWHEEL)
				));

		createForSetElement("six_way_gearbox", CCasingSet::doesGenerateSixWayGearbox, CCasingSet::getSixWayGearbox, (builder, set) -> builder
				.suffix("_from_conversion").viaShapeless(sh -> sh
						.requires(set.getVerticalSixWayGearboxItem())
				));

		createForSetElement("six_way_gearbox", CCasingSet::doesGenerateSixWayGearbox, CCasingSet::getVerticalSixWayGearboxItem, (builder, set) -> builder
				.suffix("_vertical_from_conversion").viaShapeless(sh -> sh
						.requires(set.getSixWayGearbox())
				));
	}

	private void createForSetElement(String folderName, Predicate<CCasingSet> exists, Function<CCasingSet, ? extends ItemLike> block, BiFunction<GeneratedRecipeBuilder, CCasingSet, GeneratedRecipe> recipeGenerator) {
		enterFolder(folderName);
		CCasingSets.getSets().stream().filter(exists).forEach(set -> {
			recipeGenerator.apply(create(set.getName(), () -> block.apply(set)).unlockedBy(set::getCasing), set);
		});
		leftLastFolder();
	}
}
