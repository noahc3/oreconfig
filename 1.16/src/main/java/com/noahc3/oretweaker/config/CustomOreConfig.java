package com.noahc3.oretweaker.config;

import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.PreserveNotNull;
import com.electronwill.nightconfig.core.conversion.SpecIntInRange;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CustomOreConfig {
    @Path("Custom Ore")
    public List<CustomOre> customOres;

    public static class CustomOre {
        @Path("Ore Name")
        @PreserveNotNull
        public String oreName;

        @Path("Filler Name")
        @PreserveNotNull
        public String fillerName;

        @Path("Max Vein Size")
        @SpecIntInRange(min = 0, max = 64)
        public int maxVeinSize;

        @Path("Max Vein Level")
        @SpecIntInRange(min = 1, max = 256)
        public int maxVeinLevel;

        @Path("Spawn Rate")
        @SpecIntInRange(min = -10, max = 128)
        public int spawnRate;

        public CustomOre() {

        }

        public ResourceLocation getOreRegistryName() {
            return new ResourceLocation(oreName);
        }

        public ResourceLocation getFillerRegistryName() {
            return new ResourceLocation(fillerName);
        }

        @Override
        public String toString() {
            return "CustomOreConfig{"
                    + "Ore Name = '"
                    + oreName
                    + "', Filler Name = '"
                    + fillerName
                    + "', Max Vein Size ="
                    + maxVeinSize
                    + ", Max Vein Level="
                    + maxVeinLevel
                    + ", Spawn Rate="
                    + spawnRate
                    + '}';
        }
    }
}
