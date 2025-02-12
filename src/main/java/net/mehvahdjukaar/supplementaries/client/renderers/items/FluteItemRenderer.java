package net.mehvahdjukaar.supplementaries.client.renderers.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.common.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraftforge.client.ForgeHooksClient;

public class FluteItemRenderer extends BlockEntityWithoutLevelRenderer {

    public static final ResourceLocation FLUTE_3D_MODEL = new ResourceLocation("supplementaries:item/flute_in_hand");
    public static final ResourceLocation FLUTE_2D_MODEL = new ResourceLocation("supplementaries:item/flute_gui");
    //new ModelResourceLocation("minecraft:spyglass#inventory")

    public FluteItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }


    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transform, PoseStack matrixStack,
                             MultiBufferSource buffer, int light, int overlay) {
        if (!stack.isEmpty()) {

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            matrixStack.pushPose();
            boolean gui = transform == ItemTransforms.TransformType.GUI || transform == ItemTransforms.TransformType.GROUND || transform == ItemTransforms.TransformType.FIXED;
            BakedModel model;
            if (gui) {
                model = itemRenderer.getItemModelShaper().getModelManager().getModel(FLUTE_2D_MODEL);
            }
            else{
                model = itemRenderer.getItemModelShaper().getModelManager().getModel(FLUTE_3D_MODEL);
            }

            if (model.isLayered()) {
                ForgeHooksClient.drawItemLayered(itemRenderer, model, stack, matrixStack, buffer, light, overlay, true); }
            else {
                RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, true);
                VertexConsumer vertexconsumer;
                vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, rendertype, true, stack.hasFoil());

                itemRenderer.renderModelLists(model, stack, light, overlay, matrixStack, vertexconsumer);
            }
            matrixStack.popPose();
        }

    }


}
