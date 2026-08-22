package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.invertedclutch.InvertedClutchBlock;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomInvertedClutchBlock extends InvertedClutchBlock {

    private final String type;

    public CustomInvertedClutchBlock(Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public BlockEntityType<? extends SplitShaftBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.INVERTED_CLUTCH.get();
    }

}
