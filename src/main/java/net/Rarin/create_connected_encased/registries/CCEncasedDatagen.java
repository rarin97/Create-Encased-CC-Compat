package net.Rarin.create_connected_encased.registries;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.Rarin.create_connected_encased.CCEncased;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.function.BiConsumer;

public class CCEncasedDatagen {
	public static void gatherDataHighPriority(GatherDataEvent event) {
		if (event.getMods().contains(CCEncased.ID))
			addExtraRegistrateData();
	}

	public static void gatherData(GatherDataEvent event) {
		if (!event.getMods().contains(CCEncased.ID))
			return;
		event.getGenerator().addProvider(event.includeServer(),new CCEncasedRecipeGens(event.getGenerator().getPackOutput(),event.getLookupProvider()));
	}

	private static void addExtraRegistrateData() {

		CCEncased.registrate().addDataGenerator(ProviderType.LANG, provider -> {
			BiConsumer<String, String> langConsumer = provider::add;

			provideDefaultLang("interface", langConsumer);
			provideDefaultLang("tooltips", langConsumer);
		});
	}

	private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
		String path = "assets/create_connected_encased/lang/default/" + fileName + ".json";
		JsonElement jsonElement = FilesHelper.loadJsonResource(path);
		if (jsonElement == null) {
			throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
		}
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().getAsString();
			consumer.accept(key, value);
		}
	}

}
