package app.oatgh.breadmod.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapelessRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.class)
public class RecipeMixin {

    @Inject(at=@At("HEAD"), method = "getOutput", cancellable = true)
    void Recipe(CallbackInfoReturnable<ItemStack> cir){
    }
}
