package eu.fischboy.fischconfig.newCosmetics.impl.cape;

import eu.fischboy.fischconfig.newCosmetics.Cosmetic;
import net.minecraft.util.ResourceLocation;

public class Cape implements Cosmetic {

    private final String name;
    private final ResourceLocation resource;

    public Cape(String name, ResourceLocation resource) {
        this.name = name;
        this.resource = resource;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ResourceLocation getResource() {
        return resource;
    }
}
