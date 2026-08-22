package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.brake.BrakeBlock;
import com.hlysine.create_connected.content.brake.BrakeBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomBrakeBlock extends BrakeBlock {

    private final String type;

    public CustomBrakeBlock(Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public BlockEntityType<? extends BrakeBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.BRAKE.get();
    }
}
