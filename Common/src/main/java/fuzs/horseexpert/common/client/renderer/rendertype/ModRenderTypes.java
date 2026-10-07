package fuzs.horseexpert.common.client.renderer.rendertype;

import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import fuzs.horseexpert.common.HorseExpert;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.oit.OitPipelineSet;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.TextureTransform;
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
     * OIT equivalent of {@link #ARMOR_TRANSLUCENT_PIPELINE}, required since the render type is routed to the improved
     * transparency phase; mirrors {@link RenderPipelines#OIT_ENTITY}, but without an overlay sampler.
     *
     * @see RenderPipelines#OIT_ENTITY
     */
    public static final OitPipelineSet ARMOR_TRANSLUCENT_OIT_PIPELINES = OitPipelineSet.builder("armor_translucent",
                    RenderPipeline.builder(RenderPipelines.OIT_ENTITY_SNIPPET).withShaderDefine("NO_OVERLAY").withCull(false))
            .withAccumulateModifier(accumulate -> accumulate.withShaderDefine("PER_FACE_LIGHTING")
                    .withBindGroupLayout(BindGroupLayouts.SAMPLER2))
            .build();
    /**
     * Combined base + glint armor pipeline, mirroring {@link RenderPipelines#ARMOR_CUTOUT_NO_CULL_GLINT}, but with
     * translucent blending.
     *
     * @see RenderPipelines#ARMOR_CUTOUT_NO_CULL_GLINT
     */
    public static final RenderPipeline ARMOR_TRANSLUCENT_GLINT_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET,
                    RenderPipelines.GLINT_SNIPPET)
            .withLocation(HorseExpert.id("pipeline/armor_translucent_glint"))
            .withFragmentShader(HorseExpert.id("core/armor_entity_glint"))
            .withShaderDefine("ALPHA_CUTOUT", 0.1F)
            .withShaderDefine("NO_OVERLAY")
            .withShaderDefine("PER_FACE_LIGHTING")
            .withCull(false)
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .build();
    /**
     * OIT equivalent of {@link #ARMOR_TRANSLUCENT_GLINT_PIPELINE}, mirroring {@link RenderPipelines#OIT_ITEM_GLINT} for
     * the entity shader.
     *
     * @see RenderPipelines#OIT_ITEM_GLINT
     */
    public static final OitPipelineSet ARMOR_TRANSLUCENT_GLINT_OIT_PIPELINES = OitPipelineSet.builder(
                    "armor_translucent_glint",
                    RenderPipeline.builder(RenderPipelines.OIT_ENTITY_SNIPPET)
                            .withShaderDefine("NO_OVERLAY")
                            .withFragmentShader(HorseExpert.id("core/armor_entity_glint"))
                            .withCull(false))
            .withAccumulateModifier(accumulate -> accumulate.withSnippet(RenderPipelines.GLINT_SNIPPET)
                    .withShaderDefine("PER_FACE_LIGHTING")
                    .withBindGroupLayout(BindGroupLayouts.SAMPLER2))
            .build();
    /**
     * @see net.minecraft.client.renderer.rendertype.RenderTypes#ARMOR_CUTOUT_NO_CULL
     */
    private static final Function<Identifier, RenderType> ARMOR_TRANSLUCENT = Util.memoize(texture -> {
        RenderSetup state = RenderSetup.builder(ARMOR_TRANSLUCENT_PIPELINE)
                .setOitPipelines(ARMOR_TRANSLUCENT_OIT_PIPELINES)
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
    /**
     * The glint is rendered together with the base geometry in a single pass, so the base texture keeps masking it; a
     * separate glint overlay cannot work under OIT, since it relies on the base pass depth for masking.
     *
     * @see net.minecraft.client.renderer.rendertype.RenderTypes#armorCutoutNoCullGlint(Identifier)
     */
    private static final Function<Identifier, RenderType> ARMOR_TRANSLUCENT_GLINT = Util.memoize(texture -> {
        RenderSetup state = RenderSetup.builder(ARMOR_TRANSLUCENT_GLINT_PIPELINE)
                .setOitPipelines(ARMOR_TRANSLUCENT_GLINT_OIT_PIPELINES)
                .withTexture("Sampler0", texture)
                .withTexture("GlintSampler", ItemFeatureRenderer.ENCHANTED_GLINT_ARMOR)
                .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                .useLightmap()
                .useOverlay()
                .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .affectsCrumbling()
                .sortOnUpload()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();
        return RenderType.create(HorseExpert.id("armor_translucent_glint").toString(), state);
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

    /**
     * @see net.minecraft.client.renderer.rendertype.RenderTypes#armorCutoutNoCullGlint(Identifier)
     */
    public static RenderType armorTranslucentGlint(Identifier texture) {
        return ARMOR_TRANSLUCENT_GLINT.apply(texture);
    }
}
