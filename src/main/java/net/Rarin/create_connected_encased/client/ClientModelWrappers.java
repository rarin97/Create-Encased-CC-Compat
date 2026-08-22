package net.Rarin.create_connected_encased.client;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.Rarin.create_connected_encased.blocks.CustomFluidVesselModel;
import net.minecraft.client.resources.model.BakedModel;

public final class ClientModelWrappers {

    private ClientModelWrappers() {
    }

    public static NonNullFunction<BakedModel, ? extends BakedModel> encasedFluidVessel(CTSpriteShiftEntry side,
                                                                                    CTSpriteShiftEntry top,
                                                                                    CTSpriteShiftEntry inner) {
        return model -> new CustomFluidVesselModel(model, side, top, inner);
    }
}
