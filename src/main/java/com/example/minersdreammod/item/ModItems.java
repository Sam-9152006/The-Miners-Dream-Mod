package com.example.minersdreammod.item;

import com.example.examplemod.ExampleMod; 
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    // --- THIS IS THE FIX ---
    // Removed .durability(250) and kept .stacksTo(16)
    public static final RegistryObject<Item> MINERS_DREAM = ITEMS.register("miners_dream",
            () -> new MinersDreamItem(new Item.Properties().stacksTo(16))); 

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
