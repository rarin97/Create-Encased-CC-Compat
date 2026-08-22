package net.Rarin.create_connected_encased.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import fr.iglee42.createcasing.blocks.customs.CustomChainDriveBlock;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomChainCogwheelBlock extends CustomChainDriveBlock implements ICogWheel {


    public CustomChainCogwheelBlock(Properties properties, String type) {
        super(properties, type);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.CHAIN_COGWHEEL.get();
    }
}
