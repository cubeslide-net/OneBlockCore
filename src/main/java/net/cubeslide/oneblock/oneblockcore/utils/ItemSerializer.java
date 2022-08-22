package net.cubeslide.oneblock.oneblockcore.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class ItemSerializer {

    public static String serializeToString(ItemStack item) {
        String base64 = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitOut = new BukkitObjectOutputStream(out);
            bukkitOut.writeObject(item);
            bukkitOut.close();
            base64 = Base64Coder.encodeLines(out.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return base64;
    }

    public static ItemStack deserializeFromString(String itemData) {
        ItemStack result = null;
        ByteArrayInputStream in = new ByteArrayInputStream(Base64Coder.decodeLines(itemData));
        try {
            BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(in);
            result = (ItemStack) bukkitIn.readObject();
            bukkitIn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}