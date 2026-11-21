package guivnf.losttrinkets.api.trinket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/// A trinket. Every registered item implementing this is picked up automatically.
///
/// The hooks below are only called while the trinket is active for the player passed as `owner`.
public interface ITrinket {
    default void addTrinketDescription(ItemStack stack, List<Component> lines) {
        // info.losttrinkets.<name>
        String key = stack.getItem().getDescriptionId().replace("item.", "info.");
        lines.add(Component.translatable(key).withStyle(net.minecraft.ChatFormatting.GRAY));
    }

    void onActivated(Level level, BlockPos pos, Player player);

    void onDeactivated(Level level, BlockPos pos, Player player);

    Rarity getRarity();

    boolean isUnlockable();

    void setUnlockable(boolean unlockable);

    Item getItem();

    /// Called when something attacks the owner, before damage is applied. Returns true to cancel the attack.
    default boolean onAttacked(Player owner, DamageSource source) {
        return false;
    }

    /// Called when the owner takes damage.
    default void onHurt(Player owner, DamageSource source, float amount) {
    }

    /// Called when the owner damages an entity.
    default void onAttack(Player owner, LivingEntity target, DamageSource source, float amount) {
    }

    /// Returns the damage the owner deals to the target.
    default float modifyAttackDamage(Player owner, Entity target, DamageSource source, float amount) {
        return amount;
    }

    /// Returns true to save the owner from dying.
    default boolean preventDeath(Player owner, DamageSource source) {
        return false;
    }

    /// Called when the owner kills an entity.
    default void onKill(Player owner, LivingEntity target, DamageSource source) {
    }

    /// Adds extra drops to an entity killed by the owner.
    default void addKillDrops(Player owner, LivingEntity target, List<ItemStack> drops) {
    }

    /// Returns the extra looting levels the owner gets.
    default int getLootingBonus(Player owner) {
        return 0;
    }

    /// Returns true to stop the effect from being applied to the owner.
    default boolean preventEffect(Player owner, MobEffect effect) {
        return false;
    }

    /// Called every tick for each mob targeting the owner.
    default void onTargeted(Player owner, Mob mob) {
    }

    /// Called when the owner finishes eating.
    default void onItemEaten(Player owner, ItemStack stack) {
    }

    /// Returns the speed the owner breaks the block at.
    default float modifyBreakSpeed(Player owner, BlockState state, float speed) {
        return speed;
    }

    /// Called when the owner breaks a block.
    default void onBlockBreak(Player owner, BlockPos pos, BlockState state) {
    }
}
