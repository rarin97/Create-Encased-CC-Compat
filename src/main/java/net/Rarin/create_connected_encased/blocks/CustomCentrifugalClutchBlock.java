package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.centrifugalclutch.CentrifugalClutchBlock;
import com.hlysine.create_connected.content.centrifugalclutch.CentrifugalClutchBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomCentrifugalClutchBlock extends CentrifugalClutchBlock {

    private final String type;

    public CustomCentrifugalClutchBlock(Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public BlockEntityType<? extends CentrifugalClutchBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.CENTRIFUGAL_CLUTCH.get();
    }
}
