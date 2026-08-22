package net.Rarin.create_connected_encased.mixin;

import com.hlysine.create_connected.content.fluidvessel.BoilerData;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BoilerData.class, remap = false)
public class BoilerDataMixin {

    @WrapOperation(method = "evaluate",at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",ordinal = 0))
    private boolean encased$allowAllEngine(BlockEntry<?> instance, BlockState state, Operation<Boolean> original){
        if (state.getBlock() instanceof SteamEngineBlock) {
            return true;
        }
        return original.call(instance, state);
    }

    @WrapOperation(method = "evaluate",at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",ordinal = 1))
    private boolean encased$allowAllWhistle(BlockEntry<?> instance, BlockState state, Operation<Boolean> original){
        if (state.getBlock() instanceof WhistleBlock) {
            return true;
        }
        return original.call(instance, state);
    }

    @WrapOperation(method = "checkPipeOrganAdvancement",at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",ordinal = 0))
    private boolean encased$allowAllWhistleForChecks(BlockEntry<?> instance, BlockState state, Operation<Boolean> original){
        if (state.getBlock() instanceof WhistleBlock) {
            return true;
        }
        return original.call(instance, state);
    }
}
