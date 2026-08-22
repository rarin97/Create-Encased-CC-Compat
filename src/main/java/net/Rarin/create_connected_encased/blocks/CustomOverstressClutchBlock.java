package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlock;
import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class CustomOverstressClutchBlock extends OverstressClutchBlock {

    public static final EnumProperty<ClutchState> STATE = EnumProperty.create("state", ClutchState.class);
    private final String type;

    public CustomOverstressClutchBlock(Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public BlockEntityType<? extends OverstressClutchBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.OVERSTRESS_CLUTCH.get();
    }
}
