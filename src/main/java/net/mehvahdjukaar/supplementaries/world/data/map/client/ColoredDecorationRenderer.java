package net.mehvahdjukaar.supplementaries.world.data.map.client;

import net.mehvahdjukaar.selene.map.client.DecorationRenderer;
import net.mehvahdjukaar.supplementaries.client.renderers.color.ColorHelper;
import net.mehvahdjukaar.supplementaries.world.data.map.ColoredDecoration;
import net.minecraft.resources.ResourceLocation;

public class ColoredDecorationRenderer extends DecorationRenderer<ColoredDecoration> {

    public ColoredDecorationRenderer(ResourceLocation texture) {
        super(texture);
    }

    @Override
    public int getMapColor(ColoredDecoration decoration) {
        return decoration.getColorValue();
    }
}
