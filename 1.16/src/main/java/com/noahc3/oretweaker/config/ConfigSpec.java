package com.noahc3.oretweaker.config;

import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.noahc3.oretweaker.utility.Reference;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Reference.ModInfo.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigSpec {

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static CustomOreConfig oreConfigs;

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> disableOres;


        public Common(ForgeConfigSpec.Builder BUILDER) {
            disableOres = BUILDER.defineList("Disable Ores", new ArrayList<String>() {{ add("minecraft:diamond_ore"); }}, Objects::nonNull);
            BUILDER.define("Custom Ore", new ArrayList<>());
        }
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        oreConfigs = new ObjectConverter().toObject(event.getConfig().getConfigData(), CustomOreConfig::new);
        Config.bake();
    }
}
