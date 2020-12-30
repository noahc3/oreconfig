package com.noahc3.oretweaker.world;

import com.noahc3.oretweaker.config.Config;
import com.noahc3.oretweaker.config.CustomOreConfig;
import com.noahc3.oretweaker.utility.Reference;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class Features {

    public static final ArrayList<ConfiguredFeature<?, ?>> registeredOres = new ArrayList<>();

    public static void registerCustomOreGens() {
        for(int i = 0; i < Config.customOres.size(); i++) {
            CustomOreConfig.CustomOre k = Config.customOres.get(i);

            registeredOres.add(
                    register(
                            "custom_oregen_" + i,
                            Feature.ORE.withConfiguration(
                                    new OreFeatureConfig(
                                            new BlockMatchRuleTest(ForgeRegistries.BLOCKS.getValue(k.getFillerRegistryName())),
                                            ForgeRegistries.BLOCKS.getValue(k.getOreRegistryName()).getDefaultState(),
                                            k.maxVeinSize))
                                    .range(k.maxVeinLevel).square()
                                    .func_242731_b(k.spawnRate)
                    )
            );

        }
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Reference.ModInfo.ModID + ":" + name, configuredFeature);
    }
}
