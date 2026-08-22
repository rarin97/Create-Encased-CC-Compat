package net.Rarin.create_connected_encased.mixin;

import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlock.ClutchState;
import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.ticks.TickPriority;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlock.STATE;

@Mixin(value = OverstressClutchBlockEntity.class, remap = false)
public class OverstressClutchBlockEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void scheduleTick(CallbackInfo ci) {
        OverstressClutchBlockEntity be = (OverstressClutchBlockEntity) (Object) this;
        Level level = be.getLevel();

        if (be.getBlockState().getValue(STATE) == ClutchState.UNCOUPLING && level != null && !level.isClientSide) {
            level.scheduleTick(be.getBlockPos(), be.getBlockState().getBlock(), 0, TickPriority.EXTREMELY_HIGH);
        }
    }
}