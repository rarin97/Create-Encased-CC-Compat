package net.Rarin.create_connected_encased.mixin;

import com.mojang.brigadier.context.CommandContext;
import fr.iglee42.createcasing.commands.CreateCasingCommand;
import net.Rarin.create_connected_encased.CCEncased;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(CreateCasingCommand.class)
public class CreateCasingCommandMixin {

    @Inject(method = "placeBlocks", at = @At("TAIL"))
    private void placeMyBlocks(CommandContext<CommandSourceStack> source, CallbackInfoReturnable<Integer> cir) {
        ServerLevel level = source.getSource().getLevel();
        if (!source.getSource().isPlayer()) {
            return;
        }
        AtomicInteger x = new AtomicInteger();
        AtomicInteger y = new AtomicInteger();
        BuiltInRegistries.BLOCK.keySet().stream().filter(k -> k.getNamespace().equals(CCEncased.ID)).forEach(k -> {
            Block block = BuiltInRegistries.BLOCK.get(k);
            if (block != null) {
                BlockPos pos = source.getSource().getPlayer().blockPosition().offset(x.get() + 19, y.get(), 0);
                level.setBlockAndUpdate(pos, block.defaultBlockState());
                x.getAndIncrement();
                if (x.get() > 16) {
                    x.set(0);
                    y.getAndIncrement();
                }
            }
        });
    }

    @Inject(method = "placeBlocksWithFilter", at = @At("TAIL"))
    private void placeMyBlocksWithFilter(CommandContext<CommandSourceStack> source, CallbackInfoReturnable<Integer> cir) {
        ServerLevel level = source.getSource().getLevel();
        if (!source.getSource().isPlayer()) {
            return;
        }
        AtomicInteger x = new AtomicInteger();
        AtomicInteger y = new AtomicInteger();
        String filter = source.getArgument("filter", String.class);
        List<ResourceLocation> blocks;
        if (filter.startsWith("/") && filter.endsWith("/")) {
            String regex = filter.substring(1, filter.length() - 1);
            blocks = BuiltInRegistries.BLOCK.keySet().stream().filter(k -> k.getNamespace().equals(CCEncased.ID) && k.getPath().matches(regex)).toList();
        } else {
            blocks = BuiltInRegistries.BLOCK.keySet().stream().filter(k -> k.getNamespace().equals(CCEncased.ID) && k.getPath().contains(filter)).toList();
        }
        blocks.forEach(k -> {
            Block block = BuiltInRegistries.BLOCK.get(k);
            if (block != null) {
                BlockPos pos = source.getSource().getPlayer().blockPosition().offset(x.get() + 19, y.get(), 0);
                level.setBlockAndUpdate(pos, block.defaultBlockState());
                x.getAndIncrement();
                if (x.get() > 16) {
                    x.set(0);
                    y.getAndIncrement();
                }
            }
        });
    }
}