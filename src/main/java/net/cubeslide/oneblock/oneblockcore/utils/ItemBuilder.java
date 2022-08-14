package net.cubeslide.oneblock.oneblockcore.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    public static final org.bukkit.inventory.ItemStack GRAY_GLASS = new ItemBuilder( Material.GRAY_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack MAGENTA_GLASS = new ItemBuilder( Material.MAGENTA_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack RED_GLASS = new ItemBuilder( Material.RED_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack PURPLE_GLASS = new ItemBuilder( Material.PURPLE_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack LIME_GLASS = new ItemBuilder( Material.LIME_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack BLUE_GLASS = new ItemBuilder( Material.BLUE_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack YELLOW_GLASS = new ItemBuilder( Material.YELLOW_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack ORANGE_GLASS = new ItemBuilder( Material.ORANGE_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack GLASS_GLASS = new ItemBuilder( Material.GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack LIGHT_BLUE_GLASS = new ItemBuilder( Material.LIGHT_BLUE_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack GREEN_GLASS = new ItemBuilder( Material.GREEN_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack WHITE_GLASS = new ItemBuilder( Material.WHITE_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack BROWN_GLASS = new ItemBuilder( Material.BROWN_STAINED_GLASS_PANE ).name( "§e" ).build();
    public static final org.bukkit.inventory.ItemStack PREVIOUS_PAGE = new ItemBuilder( Material.PAPER ).name( "§cSeite zurück" ).build();
    public static final org.bukkit.inventory.ItemStack NEXT_PAGE = new ItemBuilder( Material.PAPER ).name( "§cSeite vorwärts" ).build();

    private org.bukkit.inventory.ItemStack is;

    public ItemBuilder(Material mat, int amount, int data) {
        this.is = new org.bukkit.inventory.ItemStack(mat, amount, (short) data);
    }
    public ItemBuilder(Material mat) {
        this.is = new org.bukkit.inventory.ItemStack(mat);
    }

    public ItemBuilder(org.bukkit.inventory.ItemStack is) {
        this.is = is;
    }

    public ItemBuilder amount(int amount) {
        this.is.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = this.is.getItemMeta();
        meta.setDisplayName(name);
        this.is.setItemMeta(meta);
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ItemBuilder lore(String name) {
        ItemMeta meta = this.is.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList();
        }
        if(name == null) return this;
        lore.add(name);
        meta.setLore(lore);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = this.is.getItemMeta();
        meta.setLore(lore);
        this.is.setItemMeta(meta);
        return this;
    }
    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        ItemMeta meta = this.is.getItemMeta();
        meta.addItemFlags(itemFlag);
        this.is.setItemMeta(meta);
        return this;
    }
    public ItemBuilder removeItemFlag(ItemFlag itemFlag) {
        ItemMeta meta = this.is.getItemMeta();
        meta.removeItemFlags(itemFlag);
        this.is.setItemMeta(meta);
        return this;
    }
    public ItemBuilder addAllItemFlags() {
        ItemMeta meta = this.is.getItemMeta();
        for(ItemFlag flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchantedBook(Enchantment ench, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.is.getItemMeta();
        meta.addStoredEnchant(ench, level, true);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder durability(int durability) {
        this.is.setDurability((short)durability);
        return this;
    }

    public ItemBuilder data(int data) {
        byte dataa = this.is.getData().getData();
        dataa = (byte) data;
        this.is.getData().setData(dataa);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.is.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment) {
        this.is.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder type(Material material) {
        this.is.setType(material);
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ItemBuilder clearLore() {
        ItemMeta meta = this.is.getItemMeta();
        meta.setLore(new ArrayList());
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearEnchantments() {
        for (Enchantment e : this.is.getEnchantments().keySet()) {
            this.is.removeEnchantment(e);
        }
        return this;
    }

    public ItemBuilder color(Color color) {
        if ((this.is.getType() == Material.LEATHER_BOOTS) || (this.is.getType() == Material.LEATHER_CHESTPLATE) || (this.is.getType() == Material.LEATHER_HELMET) ||
                (this.is.getType() == Material.LEATHER_LEGGINGS)) {
            LeatherArmorMeta meta = (LeatherArmorMeta)this.is.getItemMeta();
            meta.setColor(color);
            this.is.setItemMeta(meta);
            return this;
        }
        throw new IllegalArgumentException("color() only applicable for leather armor!");
    }

    public ItemBuilder setSkullOwner(String owner) {
        if ((this.is.getType() == Material.PLAYER_HEAD) || (this.is.getType() == Material.SKELETON_SKULL)) {
            this.is.setDurability((short)SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta)this.is.getItemMeta();
            meta.setOwner(owner);
            this.is.setItemMeta(meta);
            return this;
        }
        throw new IllegalArgumentException("skullOwner() only applicable for skulls!");
    }

    public ItemBuilder addGlow() {
        ItemStack stack = this.is;
        stack.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return this;
    }

    public org.bukkit.inventory.ItemStack build() {
        return this.is;
    }
}
