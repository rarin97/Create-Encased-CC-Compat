package net.Rarin.create_connected_encased.mixin;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.Rarin.create_connected_encased.sets.fluids.CFluidSet;
import net.Rarin.create_connected_encased.sets.fluids.CFluidSets;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(value = FluidVesselRenderer.class,remap = false)
public class FluidVesselRendererMixin {


    @WrapOperation(method = "renderAsBoiler",at= @At(value = "INVOKE", target = "Lnet/createmod/catnip/render/CachedBuffers;partial(Ldev/engine_room/flywheel/lib/model/baked/PartialModel;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/createmod/catnip/render/SuperByteBuffer;",ordinal = 0))
    private SuperByteBuffer createConnectedEncased$replaceGauge(PartialModel partial, BlockState referenceState, Operation<SuperByteBuffer> original){
        Optional<CFluidSet> set = CFluidSets.getSets().stream().filter(CFluidSet::doesGenerateFluidVessel).filter(s->s.isInSet(referenceState.getBlock())).findFirst();
        if (set.isPresent()) return CachedBuffers.partial(set.get().getEngineGaugeModel(),referenceState);
        return original.call(partial,referenceState);
    }

    @WrapOperation(method = "renderAsBoiler",at= @At(value = "INVOKE", target = "Lnet/createmod/catnip/render/CachedBuffers;partial(Ldev/engine_room/flywheel/lib/model/baked/PartialModel;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/createmod/catnip/render/SuperByteBuffer;",ordinal = 1))
    private SuperByteBuffer createConnectedEncased$replaceGaugeDial(PartialModel partial, BlockState referenceState, Operation<SuperByteBuffer> original){
        Optional<CFluidSet> set = CFluidSets.getSets().stream().filter(CFluidSet::doesGenerateFluidVessel).filter(s->s.isInSet(referenceState.getBlock())).findFirst();
        if (set.isPresent()) return CachedBuffers.partial(set.get().getEngineGaugeDialModel(),referenceState);
        return original.call(partial,referenceState);
    }

}
