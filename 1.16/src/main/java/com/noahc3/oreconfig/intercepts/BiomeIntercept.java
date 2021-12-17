package com.noahc3.oreconfig.intercepts;

import com.noahc3.oreconfig.config.Config;
import com.noahc3.oreconfig.world.Features;
import com.noahc3.oreconfig.utility.Logger;
import net.minecraft.block.Block;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BiomeIntercept {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void BiomeLoadingIntercept(final BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        scrubFeatureList(gen.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)); //most ores
        scrubFeatureList(gen.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION)); //nether ores

        for (ConfiguredFeature<?, ?> f : Features.registeredOres) {
            gen = (BiomeGenerationSettingsBuilder) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, f);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void FMLServerStartedIntercept(FMLServerStartedEvent event) {
        if (Config.debugOutput && Config.trackedDisabledOres.size() > 0) {
            Logger.debug("The following ores could not be intercepted:");
            for(Block k : Config.trackedDisabledOres) {
                Logger.debug("    " + k.getRegistryName());
            }
        }
    }

    private static void scrubFeatureList(List<Supplier<ConfiguredFeature<?, ?>>> features) {
        Block blockCheck = null;
        ArrayList<Supplier<ConfiguredFeature<?, ?>>> flagged = new ArrayList<>();

        for(Supplier<ConfiguredFeature<?, ?>> f : features) {
            ConfiguredFeature<?, ?> resolved = resolve(f.get());
            if (resolved.feature instanceof OreFeature || resolved.feature instanceof NoExposedOreFeature) {
                //most ores
                OreFeatureConfig config = (OreFeatureConfig) resolved.config;
                blockCheck = config.state.getBlock();
            } else if (resolved.feature instanceof ReplaceBlockFeature) {
                //emeralds are special
                ReplaceBlockConfig config = (ReplaceBlockConfig) resolved.config;
                blockCheck = config.state.getBlock();
            }

            if (blockCheck != null) {
                if (Config.debugOutput) Logger.debug("Intercepted oregen for " + blockCheck.getRegistryName());
                if (Config.disabledOres.contains(blockCheck)) {
                    flagged.add(f);
                    if (Config.debugOutput) {
                        Logger.debug("    Flagged for removal");
                        Config.trackedDisabledOres.remove(blockCheck);
                    }
                }

            }
        }

        for (Supplier<ConfiguredFeature<?, ?>> f : flagged) {
            features.remove(f);
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
