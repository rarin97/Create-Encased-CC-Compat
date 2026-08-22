package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxBlock;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class CustomSixWayGearboxBlock extends SixWayGearboxBlock {

    //private final String type;
    private final Supplier<BlockItem> verticalItem;

    public CustomSixWayGearboxBlock(Properties properties, Supplier<BlockItem> verticalItem) {
        super(properties);
        this.verticalItem = verticalItem;
    }

    @Override
    public BlockEntityType<? extends SixWayGearboxBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.SIX_WAY_GEARBOX.get();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        if (state.getValue(AXIS).isVertical())
            return super.getDrops(state, builder);
        return Arrays.asList(new ItemStack(verticalItem.get()));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader world, BlockPos pos, Player player) {
        if (state.getValue(AXIS).isVertical())
            return super.getCloneItemStack(state, target, world, pos, player);
        return new ItemStack(verticalItem.get());
    }
}
