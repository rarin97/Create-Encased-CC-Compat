package net.Rarin.create_connected_encased.config;

import net.createmod.catnip.config.ConfigBase;

public class CCEServer extends ConfigBase {

	public final CCEStress stressValues = nested(0, CCEStress::new, Comments.stress);

	private static class Comments {
		static String stress = "Fine tune the kinetic stats of individual components";
	}

	@Override
	public String getName() {
		return "server";
	}

}
