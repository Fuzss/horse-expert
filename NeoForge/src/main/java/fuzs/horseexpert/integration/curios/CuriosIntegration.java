package fuzs.horseexpert.integration.curios;

import fuzs.horseexpert.init.ModRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosIntegration {

    public static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, (AttachCapabilitiesEvent<ItemStack> evt) -> {
            ItemStack itemStack = evt.getObject();
            if (!itemStack.is(ModRegistry.MONOCLE_ITEM.get())) return;
            LazyOptional<ICurio> curio = LazyOptional.of(() -> new ICurio() {

                @Override
                public boolean canEquipFromUse(SlotContext slotContext) {
                    return true;
                }

                @Override
                public ItemStack getStack() {
                    return itemStack;
                }
            });
            evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {

                @NotNull
                @Override
                public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
                    return CuriosCapability.ITEM.orEmpty(capability, curio);
                }
            });
            evt.addListener(curio::invalidate);
        });
    }
}
