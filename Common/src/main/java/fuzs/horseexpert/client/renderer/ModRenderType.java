package fuzs.horseexpert.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class ModRenderType extends RenderType {
    private static final Function<ResourceLocation, RenderType> ARMOR_CUTOUT_TRANSLUCENT_NO_CULL = Util.memoize((p_173206_) -> {
        CompositeState rendertype$compositestate = CompositeState.builder().setShaderState(RENDERTYPE_ARMOR_CUTOUT_NO_CULL_SHADER).setTextureState(new TextureStateShard(p_173206_, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(true);
        return create("armor_cutout_translucent_no_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$compositestate);
    });

    private ModRenderType(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }

    public static RenderType armorCutoutTranslucentNoCull(ResourceLocation textureLocation) {
        return ARMOR_CUTOUT_TRANSLUCENT_NO_CULL.apply(textureLocation);
    }
}
