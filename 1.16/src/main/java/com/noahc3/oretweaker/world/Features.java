package com.noahc3.oretweaker.world;

import com.noahc3.oretweaker.config.Config;
import com.noahc3.oretweaker.config.CustomOreConfig;
import com.noahc3.oretweaker.utility.Logger;
import com.noahc3.oretweaker.utility.Reference;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.IDecoratable;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class Features {

    public static final ArrayList<ConfiguredFeature<?, ?>> registeredOres = new ArrayList<>();

    public static void registerCustomOreGens() {
        for(int i = 0; i < Config.customOres.size(); i++) {
            CustomOreConfig.CustomOre k = Config.customOres.get(i);

            registeredOres.add(
                    register("custom_oregen_" + i, buildOreFeature(k.getOre(), k.getFiller(), k.maxVeinSize + 1, k.minVeinLevel, k.maxVeinLevel, k.spawnRate))
            );

            if (Config.debugOutput) {
                Logger.debug("Registered custom oregen 'custom_oregen_" + i + "' from " + k.getOreRegistryName() + "/" + k.getFillerRegistryName());
            }
        }
    }

    private static ConfiguredFeature<?, ?> buildOreFeature(Block ore, Block filler, int maxVeinSize, int minVeinLevel, int maxVeinLevel, int spawnRate) {
        ConfiguredFeature<?, ?> feature = Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(filler), ore.getDefaultState(), maxVeinSize));
        feature = minMaxRange(feature, minVeinLevel, maxVeinLevel).square();
        feature = feature.func_242731_b(spawnRate);
        return feature;
    }

    private static IDecoratable<ConfiguredFeature<?,?>> minMaxRange(IDecoratable<ConfiguredFeature<?,?>> f, int min, int max) {
        return f.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(min, min, max)));
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Reference.ModInfo.ModID + ":" + name, configuredFeature);
    }
}
