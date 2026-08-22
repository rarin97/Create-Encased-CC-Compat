package net.Rarin.create_connected_encased.config;

import com.simibubi.create.api.stress.BlockStressValues;
import net.Rarin.create_connected_encased.CCEncased;
import net.createmod.catnip.config.ConfigBase;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = CCEncased.ID)
public class CCEConfigs {

	private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

	//private static CCEClient client;
	//private static CCECommon common;
	private static CCEServer server;

//	public static CCEClient client() {
//		return client;
//	}

//	public static CCECommon common() {
//		return common;
//	}

	public static CCEServer server() {
		return server;
	}

	public static ConfigBase byType(ModConfig.Type type) {
		return CONFIGS.get(type);
	}

	private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
		Pair<T, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(builder -> {
			T config = factory.get();
			config.registerAll(builder);
			return config;
		});

		T config = specPair.getLeft();
		config.specification = specPair.getRight();
		CONFIGS.put(side, config);
		return config;
	}

	public static void register(ModContainer container) {
		//client = register(CClient::new, ModConfig.Type.CLIENT);
		//common = register(CCECommon::new, ModConfig.Type.COMMON);
		server = register(CCEServer::new, ModConfig.Type.SERVER);

		for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
			container.registerConfig(pair.getKey(), pair.getValue().specification);

		CCEStress stress = server().stressValues;
		BlockStressValues.IMPACTS.registerProvider(stress::getImpact);
		BlockStressValues.CAPACITIES.registerProvider(stress::getCapacity);
	}

	@SubscribeEvent
	public static void onLoad(ModConfigEvent.Loading event) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == event.getConfig()
					.getSpec())
				config.onLoad();
	}

	@SubscribeEvent
	public static void onReload(ModConfigEvent.Reloading event) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == event.getConfig()
					.getSpec())
				config.onReload();
	}
}
