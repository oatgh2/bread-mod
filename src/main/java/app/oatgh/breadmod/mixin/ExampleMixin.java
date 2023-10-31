package app.oatgh.breadmod.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class ExampleMixin {
	@Shadow @Final private static Logger LOGGER;

	@Shadow @Nullable public abstract ServerWorld getWorld(RegistryKey<World> key);

	@Shadow public abstract PlayerManager getPlayerManager();

	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
		LOGGER.info("Loading World");
	}

	@Inject(at = @At("TAIL"), method = "loadWorld")
	private void end(CallbackInfo info){
		LOGGER.info("World Loaded");
	}
}