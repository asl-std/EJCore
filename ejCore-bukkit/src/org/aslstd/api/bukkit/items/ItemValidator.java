package org.aslstd.api.bukkit.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import lombok.AllArgsConstructor;

public class ItemValidator {

	@AllArgsConstructor
	public enum Validator {
		NOT_AIR(null);

		private Consumer<ItemStack> function;

		public void accept(ItemStack stack) {
			function.accept(stack);
		}

	}

}
