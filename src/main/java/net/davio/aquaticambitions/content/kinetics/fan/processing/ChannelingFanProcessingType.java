package net.davio.aquaticambitions.content.kinetics.fan.processing;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import net.createmod.catnip.theme.Color;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlock.ConduitPowerLevel;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlockEntity;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.davio.aquaticambitions.registry.CAATags.CAABlockTags;
import net.davio.aquaticambitions.registry.CAATags.CAAFluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class ChannelingFanProcessingType implements FanProcessingType {

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {

        if (checkForActiveConduit(level, pos)) {
            return true;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        //Check for matching tags.
        FluidState fluidState = level.getFluidState(pos);
        BlockState blockState = level.getBlockState(pos);

        return CAABlockTags.FAN_PROCESSING_CATALYSTS_CHANNELING.matches(blockState)
                || CAAFluidTags.FAN_PROCESSING_CATALYSTS_CHANNELING.matches(fluidState);
    }

    @Override
    public int getPriority() {
        return 1200;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        var recipeManager = level.getRecipeManager();
        var input = new SingleRecipeInput(stack);
        return recipeManager
                .getRecipeFor(CAARecipeTypes.CHANNELING.getType(), input, level)
                .isPresent();
    }

    @Override
    public @Nullable List<ItemStack> process(ItemStack stack, Level level) {
        var recipeManager = level.getRecipeManager();
        var input = new SingleRecipeInput(stack);
        return recipeManager
                .getRecipeFor(CAARecipeTypes.CHANNELING.getType(), input, level)
                .map(recipe -> RecipeApplier.applyRecipeOn(level, stack, recipe.value(), true))
                .orElse(null);
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0)
            return;
        Vector3f color = new Color(0x0055FF).asVectorF();
        level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        if (level.random.nextInt(8) < 2) {
            level.addParticle(ParticleTypes.NAUTILUS, pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f));
        } else if (level.random.nextInt(8) < 4) {
            level.addParticle(ParticleTypes.ENCHANT, pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f));
        }
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(0x1F96B1, 0x0055FF, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 256f)
            particleAccess.spawnExtraParticle(ParticleTypes.ENCHANT, .125f);
        if (random.nextFloat() < 1 / 1024f)
            particleAccess.spawnExtraParticle(ParticleTypes.NAUTILUS, .075f);
        if (random.nextFloat() < 1 / 64f)
            particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE, .125f);
        if (random.nextFloat() < 1 / 64f)
            particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE_POP, .125f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        if (level.isClientSide){
            return;
        }

        if (entity instanceof Player){
            ((Player) entity).addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 260, 0, true, true));
        }
    }

    public boolean checkForActiveConduit(Level level, BlockPos pos) {
        //Check if any blockEntity in 3x3 box is an active conduit.
        //This (and priority = 1200) allows us to prevent washing process to override channeling in the 3x3 conduit box
        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                for(int k = -1; k <= 1; ++k) {
                    BlockPos adjacentPos = pos.offset(i, j, k);
                    BlockEntity blockEntity = level.getBlockEntity(adjacentPos);
                    if (blockEntity instanceof ConduitBlockEntity){
                        if(((ConduitBlockEntity) blockEntity).isActive()){
                            return true;
                        }
                    }
                }
            }
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity != null) {
            if (blockEntity instanceof MechanicalConduitBlockEntity) {
                ConduitPowerLevel powerLevel = ((MechanicalConduitBlockEntity) blockEntity).getConduitLevelFromBlock();
                return powerLevel == ConduitPowerLevel.AWAKENED;
            }
        }

        return false;
    }

}
