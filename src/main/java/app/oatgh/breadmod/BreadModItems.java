package app.oatgh.breadmod;

import app.oatgh.breadmod.food.Foods;
import app.oatgh.breadmod.items.WaterBowl;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BreadModItems {
    public static Item WATER_BOWL = new WaterBowl(new FabricItemSettings().maxCount(1));
    public static Item BREAD_POWDER = new Item((new Item.Settings()).group(ItemGroup.FOOD).food(Foods.BREAD_POWDER));
}
