package fuzs.horseexpert.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

public final class ModRenderType extends RenderType {
    private static final Function<ResourceLocation, RenderType> ARMOR_CUTOUT_TRANSLUCENT_NO_CULL = Util.memoize((ResourceLocation resourceLocation) -> {
        return createArmorCutoutNoCull("armor_cutout_translucent_no_cull", resourceLocation, false);
    });

    private ModRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    private static RenderType.CompositeRenderType createArmorCutoutNoCull(String name, ResourceLocation id, boolean equalDepthTest) {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ARMOR_CUTOUT_NO_CULL_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(id, TriState.FALSE, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setDepthTestState(equalDepthTest ? EQUAL_DEPTH_TEST : LEQUAL_DEPTH_TEST)
                .createCompositeState(true);
        return create(name, DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, compositeState);
    }

    public static RenderType armorCutoutTranslucentNoCull(ResourceLocation resourceLocation) {
        return ARMOR_CUTOUT_TRANSLUCENT_NO_CULL.apply(resourceLocation);
    }
}
