package app.oatgh.breadmod.mixin;

import app.oatgh.breadmod.BreadMod;
import app.oatgh.breadmod.BreadModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class BowlMixin {

    private Fluid fluidTest = Fluids.EMPTY;

    @Shadow
    public abstract TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand);

    @Shadow
    protected static BlockHitResult raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
        throw new AssertionError();
    }


    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    void onUseBowlInWater(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {

        ItemStack handItem = user.getStackInHand(hand);
        if (handItem.getItem().equals(Items.BOWL)) {
            BlockHitResult blockHitResult = raycast(world, user, this.fluidTest == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY
                    : RaycastContext.FluidHandling.NONE);

            if (blockHitResult.getType() == HitResult.Type.MISS) cir.setReturnValue(TypedActionResult
                    .fail(handItem));
            else if (blockHitResult.getType() != HitResult.Type.BLOCK) cir.setReturnValue(TypedActionResult
                    .fail(handItem));
            else {
                BlockPos hitedBlockPos = blockHitResult.getBlockPos();
                Direction hitedDirection = blockHitResult.getSide();
                BlockPos realAimedPos = hitedBlockPos.offset(hitedDirection);
                BlockState hitedBlockState = world.getBlockState(hitedBlockPos);
                if (world.canPlayerModifyAt(user, hitedBlockPos) && user.canPlaceOn(realAimedPos, hitedDirection, handItem)) {
                    if (this.fluidTest == Fluids.EMPTY) {
                        if (hitedBlockState.getBlock() instanceof FluidDrainable) {
                            FluidDrainable hitedFluid = (FluidDrainable) hitedBlockState.getBlock();
                            ItemStack drainedBucket = hitedFluid.tryDrainFluid(world, hitedBlockPos, hitedBlockState);
                            if (!drainedBucket.isEmpty()) {
                                hitedFluid.getBucketFillSound().ifPresent((sound) -> {
                                    user.playSound(sound, 1.0F, 1.0F);
                                });
                                world.emitGameEvent(user, GameEvent.FLUID_PICKUP, hitedBlockPos);
                                ItemStack waterBowl = new ItemStack(BreadModItems.WATER_BOWL, 1);
                                ItemStack exchanged =
                                        ItemUsage.exchangeStack(handItem, user, waterBowl);
                                if (!world.isClient) {
                                    Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) user, drainedBucket);
                                }
                                cir.setReturnValue(TypedActionResult.success(exchanged));
                            } else cir.setReturnValue(TypedActionResult.fail(handItem));
                        } else cir.setReturnValue(TypedActionResult.fail(handItem));
                    } else cir.setReturnValue(TypedActionResult.fail(handItem));
                } else cir.setReturnValue(TypedActionResult.fail(handItem));
            }
        }
    }
}
