package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.freewheelclutch.FreewheelClutchBlock;
import com.hlysine.create_connected.content.freewheelclutch.FreewheelClutchBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomFreewheelClutchBlock extends FreewheelClutchBlock {

    private final String type;

    public CustomFreewheelClutchBlock(Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public BlockEntityType<? extends FreewheelClutchBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.FREEWHEEL_CLUTCH.get();
    }
}
