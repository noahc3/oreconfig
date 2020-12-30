package com.noahc3.oretweaker.intercepts;

import com.noahc3.oretweaker.config.Config;
import com.noahc3.oretweaker.world.Features;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BiomeIntercept {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void BiomeLoadingIntercept(final BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ArrayList<Supplier<ConfiguredFeature<?, ?>>> flagged = new ArrayList<>();
        List<Supplier<ConfiguredFeature<?, ?>>> oreFeatures = gen.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);

        for(Supplier<ConfiguredFeature<?, ?>> f : oreFeatures) {
            ConfiguredFeature<?, ?> resolved = resolve(f.get());
            if (resolved.feature instanceof OreFeature) {
                //most ores
                OreFeatureConfig config = (OreFeatureConfig) resolved.config;
                if (Config.disabledOres.contains(config.state.getBlock())) {
                    flagged.add(f);
                }
            } else if (resolved.feature instanceof ReplaceBlockFeature) {
                //emeralds are special
                ReplaceBlockConfig config = (ReplaceBlockConfig) resolved.config;
                if (Config.disabledOres.contains(config.state.getBlock())) {
                    flagged.add(f);
                }
            }
        }

        for (Supplier<ConfiguredFeature<?, ?>> f : flagged) {
            oreFeatures.remove(f);
        }

        for (ConfiguredFeature<?, ?> f : Features.registeredOres) {
            gen = (BiomeGenerationSettingsBuilder) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, f);
        }
    }

    private static ConfiguredFeature<?, ?> resolve(ConfiguredFeature<?, ?> f) {
        ConfiguredFeature<?, ?> result = f;

        while (result.feature instanceof DecoratedFeature) {
            result = ((DecoratedFeatureConfig)result.getConfig()).feature.get();
        }

        return result;
    }
}
