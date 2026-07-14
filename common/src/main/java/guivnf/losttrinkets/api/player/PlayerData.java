package guivnf.losttrinkets.api.player;

import net.minecraft.nbt.CompoundTag;
import guivnf.losttrinkets.api.trinket.Trinkets;

public class PlayerData {
    private final Trinkets trinkets = new Trinkets(this);
    public long unlockDelay;
    public boolean allowFlying;
    public boolean wasFlying;
    private boolean sync;

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("trinkets", this.trinkets.serializeNBT());
        nbt.putLong("unlock_delay", this.unlockDelay);
        nbt.putBoolean("allow_flying", this.allowFlying);
        nbt.putBoolean("was_flying", this.wasFlying);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.trinkets.deserializeNBT(nbt.getCompoundOrEmpty("trinkets"));
        this.unlockDelay = nbt.getLongOr("unlock_delay", 0L);
        this.allowFlying = nbt.getBooleanOr("allow_flying", false);
        this.wasFlying = nbt.getBooleanOr("was_flying", false);
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public boolean isSync() {
        return this.sync;
    }

    public Trinkets getTrinkets() {
        return this.trinkets;
    }
}
