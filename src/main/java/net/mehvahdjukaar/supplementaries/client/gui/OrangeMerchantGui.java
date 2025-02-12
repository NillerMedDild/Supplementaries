package net.mehvahdjukaar.supplementaries.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mehvahdjukaar.supplementaries.common.Textures;
import net.mehvahdjukaar.supplementaries.inventories.RedMerchantContainer;
import net.mehvahdjukaar.supplementaries.network.NetworkHandler;
import net.mehvahdjukaar.supplementaries.network.ServerBoundSelectMerchantTradePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class OrangeMerchantGui extends AbstractContainerScreen<RedMerchantContainer> {
    private static final ResourceLocation VILLAGER_LOCATION = Textures.RED_MERCHANT_GUI_TEXTURE;
    private static final Component TRADES_LABEL = new TranslatableComponent("merchant.trades");
    private static final Component LEVEL_SEPARATOR = new TextComponent(" - ");
    private static final Component DEPRECATED_TOOLTIP = new TranslatableComponent("merchant.deprecated");
    private int shopItem;
    private final OrangeMerchantGui.TradeButton[] tradeOfferButtons = new OrangeMerchantGui.TradeButton[7];
    private int scrollOff;
    private boolean isDragging;

    public OrangeMerchantGui(RedMerchantContainer p_i51080_1_, Inventory p_i51080_2_, Component p_i51080_3_) {
        super(p_i51080_1_, p_i51080_2_, p_i51080_3_);
        this.imageWidth = 276;
        this.inventoryLabelX = 107;
    }

    private void postButtonClick() {
        this.menu.setSelectionHint(this.shopItem);
        this.menu.tryMoveItems(this.shopItem);

        NetworkHandler.sendToServerPlayer(new ServerBoundSelectMerchantTradePacket(this.shopItem));
    }

    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k = j + 16 + 2;

        for (int l = 0; l < 7; ++l) {
            this.tradeOfferButtons[l] = this.addWidget(new OrangeMerchantGui.TradeButton(i + 5, k, l, (p_214132_1_) -> {
                if (p_214132_1_ instanceof OrangeMerchantGui.TradeButton) {
                    this.shopItem = ((OrangeMerchantGui.TradeButton) p_214132_1_).getIndex() + this.scrollOff;
                    this.postButtonClick();
                }

            }));
            k += 20;
        }

    }

    protected void renderLabels(PoseStack pPoseStack, int pX, int pY) {

        Component tradeOffer = new TranslatableComponent("gui.supplementaries.orange_trader.trade")
                .withStyle(ChatFormatting.WHITE)
                .withStyle(ChatFormatting.BOLD);
        this.font.draw(pPoseStack, tradeOffer, (float) (49 + this.imageWidth / 2 - this.font.width(tradeOffer) / 2), 10.0F, 4210752);

        Component iReceive = new TranslatableComponent("gui.supplementaries.orange_trader.get")
                .withStyle(ChatFormatting.WHITE);
        this.font.draw(pPoseStack, iReceive, (float) (49 - 29 + this.imageWidth / 2 - this.font.width(iReceive) / 2), 24.0F, 4210752);

        Component uReceive = new TranslatableComponent("gui.supplementaries.orange_trader.receive")
                .withStyle(ChatFormatting.WHITE);
        this.font.draw(pPoseStack, uReceive, (float) (49 + 42 + this.imageWidth / 2 - this.font.width(uReceive) / 2), 24.0F, 4210752);


        this.font.draw(pPoseStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
        int l = this.font.width(TRADES_LABEL);
        this.font.draw(pPoseStack, TRADES_LABEL, (float) (5 - l / 2 + 48), 6.0F, 4210752);
    }


    @Override
    protected void renderBg(PoseStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        blit(pMatrixStack, i, j, this.getBlitOffset(), 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 512);
        MerchantOffers merchantoffers = this.menu.getOffers();
        if (!merchantoffers.isEmpty()) {
            int k = this.shopItem;
            if (k < 0 || k >= merchantoffers.size()) {
                return;
            }

            MerchantOffer merchantoffer = merchantoffers.get(k);
            if (merchantoffer.isOutOfStock()) {
                RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                blit(pMatrixStack, this.leftPos + 83 + 99, this.topPos + 35, this.getBlitOffset(), 311.0F, 0.0F, 28, 21, 256, 512);
            }
        }
    }

    private void renderProgressBar(PoseStack poseStack, int pX, int pY, MerchantOffer merchantOffer) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
        int i = this.menu.getTraderLevel();
        int j = this.menu.getTraderXp();
        if (i < 5) {
            blit(poseStack, pX + 136, pY + 16, this.getBlitOffset(), 0.0F, 186.0F, 102, 5, 256, 512);
            int k = VillagerData.getMinXpPerLevel(i);
            if (j >= k && VillagerData.canLevelUp(i)) {
                int l = 100;
                float f = 100.0F / (float) (VillagerData.getMaxXpPerLevel(i) - k);
                int i1 = Math.min(Mth.floor(f * (float) (j - k)), 100);
                blit(poseStack, pX + 136, pY + 16, this.getBlitOffset(), 0.0F, 191.0F, i1 + 1, 5, 256, 512);
                int j1 = this.menu.getFutureTraderXp();
                if (j1 > 0) {
                    int k1 = Math.min(Mth.floor((float) j1 * f), 100 - i1);
                    blit(poseStack, pX + 136 + i1 + 1, pY + 16 + 1, this.getBlitOffset(), 2.0F, 182.0F, k1, 3, 256, 512);
                }

            }
        }
    }

    private void renderScroller(PoseStack p_238840_1_, int p_238840_2_, int p_238840_3_, MerchantOffers p_238840_4_) {
        int i = p_238840_4_.size() + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int l = 113;
            int i1 = Math.min(113, this.scrollOff * k);
            if (this.scrollOff == i - 1) {
                i1 = 113;
            }

            blit(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18 + i1, this.getBlitOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
        } else {
            blit(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18, this.getBlitOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
        }

    }

    @Override
    public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        MerchantOffers merchantoffers = this.menu.getOffers();
        if (!merchantoffers.isEmpty()) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            int k = j + 16 + 1;
            int l = i + 5 + 5;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
            this.renderScroller(pMatrixStack, i, j, merchantoffers);
            int i1 = 0;

            for (MerchantOffer merchantoffer : merchantoffers) {
                if (this.canScroll(merchantoffers.size()) && (i1 < this.scrollOff || i1 >= 7 + this.scrollOff)) {
                    ++i1;
                } else {
                    ItemStack itemstack = merchantoffer.getBaseCostA();
                    ItemStack itemstack1 = merchantoffer.getCostA();
                    ItemStack itemstack2 = merchantoffer.getCostB();
                    ItemStack itemstack3 = merchantoffer.getResult();
                    this.itemRenderer.blitOffset = 100.0F;
                    int j1 = k + 2;
                    this.renderAndDecorateCostA(pMatrixStack, itemstack1, itemstack, l, j1);
                    if (!itemstack2.isEmpty()) {
                        this.itemRenderer.renderAndDecorateFakeItem(itemstack2, i + 5 + 35, j1);
                        this.itemRenderer.renderGuiItemDecorations(this.font, itemstack2, i + 5 + 35, j1);
                    }

                    this.renderButtonArrows(pMatrixStack, merchantoffer, i, j1);
                    this.itemRenderer.renderAndDecorateFakeItem(itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.renderGuiItemDecorations(this.font, itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.blitOffset = 0.0F;
                    k += 20;
                    ++i1;
                }
            }

            int k1 = this.shopItem;
            MerchantOffer merchantOffer = merchantoffers.get(k1);
            if (this.menu.showProgressBar()) {
                this.renderProgressBar(pMatrixStack, i, j, merchantOffer);
            }

            if (merchantOffer.isOutOfStock() && this.isHovering(186, 35, 22, 21, (double) pMouseX, (double) pMouseY) && this.menu.canRestock()) {
                this.renderTooltip(pMatrixStack, DEPRECATED_TOOLTIP, pMouseX, pMouseY);
            }

            for (OrangeMerchantGui.TradeButton tradeButton : this.tradeOfferButtons) {
                if (tradeButton.isHovered()) {
                    tradeButton.renderToolTip(pMatrixStack, pMouseX, pMouseY);
                }

                tradeButton.visible = tradeButton.index < this.menu.getOffers().size();
            }

            RenderSystem.enableDepthTest();
        }

        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    private void renderButtonArrows(PoseStack pPoseStack, MerchantOffer pMerchantOffer, int pPosX, int pPosY) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
        if (pMerchantOffer.isOutOfStock()) {
            blit(pPoseStack, pPosX + 5 + 35 + 20, pPosY + 3, this.getBlitOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
        } else {
            blit(pPoseStack, pPosX + 5 + 35 + 20, pPosY + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
        }
    }

    private void renderAndDecorateCostA(PoseStack pPoseStack, ItemStack pRealCost, ItemStack pBaseCost, int pX, int pY) {
        this.itemRenderer.renderAndDecorateFakeItem(pRealCost, pX, pY);
        if (pBaseCost.getCount() == pRealCost.getCount()) {
            this.itemRenderer.renderGuiItemDecorations(this.font, pRealCost, pX, pY);
        } else {
            this.itemRenderer.renderGuiItemDecorations(this.font, pBaseCost, pX, pY, pBaseCost.getCount() == 1 ? "1" : null);
            this.itemRenderer.renderGuiItemDecorations(this.font, pRealCost, pX + 14, pY, pRealCost.getCount() == 1 ? "1" : null);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
            this.setBlitOffset(this.getBlitOffset() + 300);
            blit(pPoseStack, pX + 7, pY + 12, this.getBlitOffset(), 0.0F, 176.0F, 9, 2, 256, 512);
            this.setBlitOffset(this.getBlitOffset() - 300);
        }

    }

    private boolean canScroll(int pNumOffers) {
        return pNumOffers > 7;
    }

    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        int i = this.menu.getOffers().size();
        if (this.canScroll(i)) {
            int j = i - 7;
            this.scrollOff = (int) ((double) this.scrollOff - pDelta);
            this.scrollOff = Mth.clamp(this.scrollOff, 0, j);
        }

        return true;
    }

    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        int i = this.menu.getOffers().size();
        if (this.isDragging) {
            int j = this.topPos + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float) pMouseY - (float) j - 13.5F) / ((float) (k - j) - 27.0F);
            f = f * (float) l + 0.5F;
            this.scrollOff = Mth.clamp((int) f, 0, l);
            return true;
        } else {
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        this.isDragging = false;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (this.canScroll(this.menu.getOffers().size()) && pMouseX > (double) (i + 94) && pMouseX < (double) (i + 94 + 6) && pMouseY > (double) (j + 18) && pMouseY <= (double) (j + 18 + 139 + 1)) {
            this.isDragging = true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }


    class TradeButton extends Button {
        final int index;

        public TradeButton(int p_i50601_2_, int p_i50601_3_, int p_i50601_4_, Button.OnPress p_i50601_5_) {
            super(p_i50601_2_, p_i50601_3_, 89, 20, TextComponent.EMPTY, p_i50601_5_);
            this.index = p_i50601_4_;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }

        public void renderToolTip(PoseStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
            if (this.isHovered && OrangeMerchantGui.this.menu.getOffers().size() > this.index + OrangeMerchantGui.this.scrollOff) {
                if (p_230443_2_ < this.x + 20) {
                    ItemStack itemstack = OrangeMerchantGui.this.menu.getOffers().get(this.index + OrangeMerchantGui.this.scrollOff).getCostA();
                    OrangeMerchantGui.this.renderTooltip(p_230443_1_, itemstack, p_230443_2_, p_230443_3_);
                } else if (p_230443_2_ < this.x + 50 && p_230443_2_ > this.x + 30) {
                    ItemStack itemstack2 = OrangeMerchantGui.this.menu.getOffers().get(this.index + OrangeMerchantGui.this.scrollOff).getCostB();
                    if (!itemstack2.isEmpty()) {
                        OrangeMerchantGui.this.renderTooltip(p_230443_1_, itemstack2, p_230443_2_, p_230443_3_);
                    }
                } else if (p_230443_2_ > this.x + 65) {
                    ItemStack itemstack1 = OrangeMerchantGui.this.menu.getOffers().get(this.index + OrangeMerchantGui.this.scrollOff).getResult();
                    OrangeMerchantGui.this.renderTooltip(p_230443_1_, itemstack1, p_230443_2_, p_230443_3_);
                }
            }

        }
    }
}
