package eu.fischboy.fischconfig.newCosmetics;

import eu.fischboy.fischconfig.newCosmetics.impl.cape.Cape;
import eu.fischboy.fischconfig.newCosmetics.impl.cape.CapeType;
import eu.fischboy.fischconfig.newCosmetics.impl.wings.Wings;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CosmeticManager {

    @Getter @Setter
    private List<Cosmetic> cosmetics = new ArrayList<>();
    private CapeType currentCape;
    private Wings wings;

    public CosmeticManager() {
        this.currentCape = CapeType.CLASSIC;
        this.wings = new Wings();
        for (CapeType type : CapeType.values()) {
            cosmetics.add(new Cape(type.getName(), this.loadResource(type.getName())));
        }
    }

    public Cosmetic getCosmeticByName(String name) {
        return cosmetics.stream().filter(cosmetics -> cosmetics.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    private ResourceLocation loadResource(String path) {
        ResourceLocation location = new ResourceLocation("fischconfig","cosmetics/Capes/" + path + ".png");
        if (Minecraft.getMinecraft().getTextureManager().getTexture(location) != null) {
            System.out.println("Loading cape texture: " + path);
        }
        return location;
    }

    public CapeType getCurrentCape() {
        return currentCape;
    }

    public void setCurrentCape(CapeType currentCape) {
        this.currentCape = currentCape;
    }

    public void updateCapeType(int capeDesign) {
        switch (capeDesign) {
            case 1:
                setCurrentCape(CapeType.MODERN);
                break;
            case 2:
                setCurrentCape(CapeType.CUSTOM_COLOR);
                break;
            default:
                setCurrentCape(CapeType.CLASSIC);
                break;
        }
    }
}
