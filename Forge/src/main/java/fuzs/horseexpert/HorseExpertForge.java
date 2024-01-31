package fuzs.horseexpert;

import fuzs.horseexpert.data.*;
import fuzs.horseexpert.handler.CuriosCapabilityHandler;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(HorseExpert.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseExpertForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, CuriosCapabilityHandler::onAttachCapabilities);
    }

    @SubscribeEvent
    public static void onInterModEnqueue(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        evt.getGenerator().addProvider(new ModEntityTypeTagsProvider(evt, HorseExpert.MOD_ID));
        evt.getGenerator().addProvider(new ModItemTagsProvider(evt, HorseExpert.MOD_ID));
        evt.getGenerator().addProvider(new ModLanguageProvider(evt, HorseExpert.MOD_ID));
        evt.getGenerator().addProvider(new ModModelProvider(evt, HorseExpert.MOD_ID));
        evt.getGenerator().addProvider(new ModRecipeProvider(evt, HorseExpert.MOD_ID));
    }
}
