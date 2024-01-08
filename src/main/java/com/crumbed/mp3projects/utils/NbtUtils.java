package com.crumbed.mp3projects.utils;

import net.minecraft.world.item.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NbtUtils {

    public static ByteArrayInputStream serializeItemsArray(ItemStack[] items) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

        dataOutput.writeInt(items.length);

        // Write each item in the byte stream.
        // As ItemStack implements the ConfigurationSerializable interface,
        // all of the properties of ItemStack are serialized, even custom NBT tags.
        for (int i = 0; i < items.length; i++) {
            dataOutput.writeObject(items[i]);
        }

        dataOutput.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public static ItemStack[] deserializeItemsArray(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null || inputStream.available() == 0)
            return new ItemStack[0];
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        ItemStack[] items = new ItemStack[dataInput.readInt()];

        // Read the serialized inventory
        for (int i = 0; i < items.length; i++) {
            items[i] = (ItemStack) dataInput.readObject();
        }

        dataInput.close();
        return items;
    }
}
