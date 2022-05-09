package ru.asl.api.ejcore.json.items;

import java.io.File;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.NonNull;

public class JSONItemStack {

	@Getter private ItemStack stack;

	public JSONItemStack(@NonNull ItemStack stack) {

	}

	public JSONItemStack(File file) {

	}

}
