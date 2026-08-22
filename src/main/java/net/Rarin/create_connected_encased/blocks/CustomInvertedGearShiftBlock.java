package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.invertedgearshift.InvertedGearshiftBlock;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomInvertedGearShiftBlock extends InvertedGearshiftBlock {

    private final String type;

    public CustomInvertedGearShiftBlock(Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public BlockEntityType<? extends SplitShaftBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.INVERTED_GEARSHIFT.get();
    }
}
