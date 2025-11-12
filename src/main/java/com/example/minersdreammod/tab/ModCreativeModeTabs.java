package com.example.minersdreammod.tab;

import com.example.examplemod.ExampleMod;
import com.example.minersdreammod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID); // <-- FIXED

    public static final RegistryObject<CreativeModeTab> MINERS_DREAM_TAB = CREATIVE_MODE_TABS.register("miners_dream_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.MINERS_DREAM.get()))
                    .title(Component.translatable("creativetab.miners_dream_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MINERS_DREAM.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
