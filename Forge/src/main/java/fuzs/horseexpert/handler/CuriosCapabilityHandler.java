package fuzs.horseexpert.handler;

import fuzs.horseexpert.init.ModRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosCapabilityHandler {

    public static void onAttachCapabilities(final AttachCapabilitiesEvent<ItemStack> evt) {
        if (!evt.getObject().is(ModRegistry.MONOCLE_ITEM.get())) return;
        evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
            private final ICurio curio = new ICurio() {

                @Override
                public ItemStack getStack() {
                    return evt.getObject();
                }

                @Override
                public boolean canEquipFromUse(SlotContext ctx) {
                    return true;
                }
            };

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, LazyOptional.of(() -> this.curio));
            }
        });
    }
}
