
package xyz.souldb.ris3.item;

import xyz.souldb.ris3.itemgroup.RIStoolsItemGroup;
import xyz.souldb.ris3.Ris3ModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.HoeItem;

@Ris3ModElements.ModElement.Tag
public class GalaxiumHoeItem extends Ris3ModElements.ModElement {
	@ObjectHolder("ris3:galaxium_hoe")
	public static final Item block = null;
	public GalaxiumHoeItem(Ris3ModElements instance) {
		super(instance, 54);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new HoeItem(new IItemTier() {
			public int getMaxUses() {
				return 250;
			}

			public float getEfficiency() {
				return 6f;
			}

			public float getAttackDamage() {
				return 0f;
			}

			public int getHarvestLevel() {
				return 2;
			}

			public int getEnchantability() {
				return 14;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(GalaxiumDustItem.block, (int) (1)));
			}
		}, -3f, new Item.Properties().group(RIStoolsItemGroup.tab)) {
		}.setRegistryName("galaxium_hoe"));
	}
}