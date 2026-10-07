# Horse Expert

A Minecraft mod. Downloads can be found on [CurseForge](https://www.curseforge.com/members/fuzs_/projects) and [Modrinth](https://modrinth.com/user/Fuzs).

![](banner.png)

## Porting guide

The mod ships exactly one custom shader, `assets/horseexpert/shaders/core/armor_entity_glint.fsh`. It is a copy of
vanilla `assets/minecraft/shaders/core/entity.fsh` with a single change, so it is ported by copying the vanilla file
again and deleting the following block from `main()`:

```glsl
    #ifdef GLINT
    color.a = max(color.a, GlintAlpha);
    #endif
```

Do not make any other change; keeping the rest byte-for-byte identical to the vanilla file makes a future port a
one-block diff.

Reason: `GlintAlpha` (the glint strength option, default `0.75`) also clamps the fragment alpha via that block.
`entity.fsh` normally renders opaque/cutout armor whose alpha is already `0` or `1`, so the clamp never shows, but the
monocle is genuinely translucent and was forced nearly opaque whenever it had a glint. Removing only that block keeps
the glint brightness scaled by `GlintAlpha` (`glintColor = GlintAlpha * texture(...)` is untouched) while leaving the
base alpha intact.
