package me.obsilabor.arachnophobiamodeforminecraft.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.obsilabor.arachnophobiamodeforminecraft.config.ArachnophobiaModeConfig;
import me.obsilabor.arachnophobiamodeforminecraft.config.ClothConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void injectAlwaysRenderNameTags(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if(entity instanceof SpiderEntity) {
            ArachnophobiaModeConfig config = ClothConfigManager.INSTANCE.getConfig();
            if(config == null) {
                return;
            }
            if(!config.isEnabled()) {
                return;
            }
            this.renderCat((SpiderEntity) entity, entity.getDisplayName(), matrixStack);
            ci.cancel();
        }
    }

    @Shadow @Final protected EntityRenderDispatcher dispatcher;

    @Shadow public abstract TextRenderer getTextRenderer();

    private void renderCat(SpiderEntity entity, Text text, MatrixStack matrices) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        ArachnophobiaModeConfig config = ClothConfigManager.INSTANCE.getConfig();
        if (config == null) {
            return;
        }
        if (minecraft.player == null) {
            return;
        }
        if (!minecraft.player.canSee(entity) && !minecraft.player.isSpectator()) {
            return;
        }
        float f = entity.getHeight() + 0.5F;
        matrices.push();
        matrices.translate(0.0D, f, 0.0D);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.scale(-0.025F, -0.025F, 0.025F);
        TextRenderer textRenderer = this.getTextRenderer();
        float h = (float)(-textRenderer.getWidth(text) / 2);
        int catImage = config.getCatImage();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new Identifier("arachnophobiamode", "cat" + catImage + ".png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        DrawableHelper.drawTexture(matrices, (int) h-25, -30, 0, 0, 128, 128, 128, 128);
        matrices.pop();
    }
}
