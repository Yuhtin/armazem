package com.yuhtin.minecraft.armazem.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {
    private final ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(String name, boolean link) {
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

        if (!link) {
            meta.setOwner(name);
            itemStack.setItemMeta(meta);
            itemMeta = itemStack.getItemMeta();
            return;
        }

        name = "http://textures.minecraft.net/texture/" + name;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder()
                .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", name).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemStack.setItemMeta(meta);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int quantity, int data) {
        this.itemStack = new ItemStack(material, quantity, (short) data);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(ColorUtils.colored(name));
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder lore(String line) {
        itemMeta.setLore(Collections.singletonList(ColorUtils.colored(line)));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        itemMeta.setLore(Arrays.asList(ColorUtils.colored(lore)));
        return this;
    }

    public ItemBuilder setTag(String path, String value) {
        net.minecraft.server.v1_8_R3.ItemStack itemNBT = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = (itemNBT.hasTag() ? itemNBT.getTag() : new NBTTagCompound());
        tag.set(path, new NBTTagString(value));
        itemStack = CraftItemStack.asBukkitCopy(itemNBT);
        return this;
    }

    public ItemBuilder setTag(String path, double value) {
        net.minecraft.server.v1_8_R3.ItemStack itemNBT = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = (itemNBT.hasTag() ? itemNBT.getTag() : new NBTTagCompound());
        tag.set(path, new NBTTagDouble(value));
        itemStack = CraftItemStack.asBukkitCopy(itemNBT);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        lore.replaceAll(line -> line = ColorUtils.colored(line));

        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreRestrictions) {
        itemMeta.addEnchant(enchantment, level, ignoreRestrictions);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder glow() {
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder buildMeta() {
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack resultNoMeta() {
        return itemStack;
    }

    public ItemStack result() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}