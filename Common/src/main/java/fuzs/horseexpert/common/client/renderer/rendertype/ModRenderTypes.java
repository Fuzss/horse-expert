package fuzs.horseexpert.common.client.renderer.rendertype;

import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import fuzs.horseexpert.common.HorseExpert;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public final class ModRenderTypes {
    /**
     * Similar to the vanilla pipeline, but with translucent blending to keep the monocle texture transparent.
     *
     * @see RenderPipelines#ARMOR_CUTOUT_NO_CULL
     */
    public static final RenderPipeline ARMOR_TRANSLUCENT_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withLocation(HorseExpert.id("pipeline/armor_translucent"))
            .withShaderDefine("ALPHA_CUTOUT", 0.1F)
            .withShaderDefine("NO_OVERLAY")
            .withShaderDefine("PER_FACE_LIGHTING")
            .withCull(false)
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .build();
    /**
     * @see net.minecraft.client.renderer.rendertype.RenderTypes#ARMOR_CUTOUT_NO_CULL
     */
    private static final Function<Identifier, RenderType> ARMOR_TRANSLUCENT = Util.memoize(texture -> {
        RenderSetup state = RenderSetup.builder(ARMOR_TRANSLUCENT_PIPELINE)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .affectsCrumbling()
                .sortOnUpload()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();
        return RenderType.create(HorseExpert.id("armor_translucent").toString(), state);
    });

    private ModRenderTypes() {
        // NO-OP
    }

    /**
     * @see net.minecraft.client.renderer.rendertype.RenderTypes#armorCutoutNoCull(Identifier)
     */
    public static RenderType armorTranslucent(Identifier texture) {
        return ARMOR_TRANSLUCENT.apply(texture);
    }
}
