package net.Rarin.create_connected_encased.registries;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTType;
import fr.iglee42.createcasing.CreateCasing;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.resources.ResourceLocation;

public class CCEncasedSprites {

    public static final CTSpriteShiftEntry REFINED_RADIANCE_FLUID_TANK = getCT(AllCTTypes.RECTANGLE, "fluid_tank/refined_radiance");
    public static final CTSpriteShiftEntry REFINED_RADIANCE_FLUID_TANK_TOP = getCT(AllCTTypes.RECTANGLE, "fluid_tank_top/refined_radiance");
    public static final CTSpriteShiftEntry REFINED_RADIANCE_FLUID_TANK_INNER = getCT(AllCTTypes.RECTANGLE, "fluid_tank_inner/refined_radiance");

    public static final CTSpriteShiftEntry INDUSTRIAL_IRON_FLUID_TANK = getCT(AllCTTypes.RECTANGLE, "fluid_tank/industrial_iron");
    public static final CTSpriteShiftEntry INDUSTRIAL_IRON_FLUID_TANK_TOP = getCT(AllCTTypes.RECTANGLE, "fluid_tank_top/industrial_iron");
    public static final CTSpriteShiftEntry INDUSTRIAL_IRON_FLUID_TANK_INNER = getCT(AllCTTypes.RECTANGLE, "fluid_tank_inner/industrial_iron");

    public static final CTSpriteShiftEntry WEATHERED_IRON_FLUID_TANK = getCT(AllCTTypes.RECTANGLE, "fluid_tank/weathered_iron");
    public static final CTSpriteShiftEntry WEATHERED_IRON_FLUID_TANK_TOP = getCT(AllCTTypes.RECTANGLE, "fluid_tank_top/weathered_iron");
    public static final CTSpriteShiftEntry WEATHERED_IRON_FLUID_TANK_INNER = getCT(AllCTTypes.RECTANGLE, "fluid_tank_inner/weathered_iron");

    public static final CTSpriteShiftEntry SHADOW_STEEL_FLUID_TANK = getCT(AllCTTypes.RECTANGLE, "fluid_tank/shadow_steel");
    public static final CTSpriteShiftEntry SHADOW_STEEL_FLUID_TANK_TOP = getCT(AllCTTypes.RECTANGLE, "fluid_tank_top/shadow_steel");
    public static final CTSpriteShiftEntry SHADOW_STEEL_FLUID_TANK_INNER = getCT(AllCTTypes.RECTANGLE, "fluid_tank_inner/shadow_steel");

    public static final CTSpriteShiftEntry RAILWAY_FLUID_TANK = getCT(AllCTTypes.RECTANGLE, "fluid_tank/railway");
    public static final CTSpriteShiftEntry RAILWAY_FLUID_TANK_TOP = getCT(AllCTTypes.RECTANGLE, "fluid_tank_top/railway");
    public static final CTSpriteShiftEntry RAILWAY_FLUID_TANK_INNER = getCT(AllCTTypes.RECTANGLE, "fluid_tank_inner/railway");

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }

    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static SpriteShiftEntry getFromCreate(String originalLocation, String targetLocation) {
        return get(Create.asResource(originalLocation), CreateCasing.asResource(targetLocation));
    }
    private static SpriteShiftEntry getFromCreate(String location) {
        return get(Create.asResource(location), CreateCasing.asResource(location));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return getCT(type, CreateCasing.asResource("block/" + blockTextureName), CreateCasing.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }

    private static CTSpriteShiftEntry getCT(CTType type, ResourceLocation blockTexture, ResourceLocation connectedTexture){
        CTSpriteShiftEntry entry = new CTSpriteShiftEntry(type);
        if (CatnipServices.PLATFORM.getEnv().isClient())
            entry.set(blockTexture, connectedTexture);
        return entry;
    }

    public static SpriteShiftEntry get(ResourceLocation originalLocation, ResourceLocation targetLocation) {
        SpriteShiftEntry entry = new SpriteShiftEntry();
        CatnipServices.PLATFORM.executeOnClientOnly(() -> () -> entry.set(originalLocation, targetLocation));
        return entry;
    }
}
