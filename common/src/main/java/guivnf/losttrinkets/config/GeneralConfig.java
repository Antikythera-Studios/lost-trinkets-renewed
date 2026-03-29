package guivnf.losttrinkets.config;

import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.handler.UnlockManager;

import java.util.ArrayList;
import java.util.List;

public class GeneralConfig {

    public boolean unlockEnabled = true;
    public long unlockCooldown = 2400L;
    public List<String> blackList = new ArrayList<>();
    public List<String> nonRandom = new ArrayList<>();

    public int startSlots = 1;
    public int maxSlots = 40;
    public int slotCost = 15;
    public int slotUpFactor = 3;

    public boolean killingUnlockEnabled = true;
    public int killing = 120;
    public boolean bossKillingUnlockEnabled = true;
    public int bossKilling = 10;

    public boolean farmingUnlockEnabled = true;
    public int farming = 140;

    public boolean oresMiningUnlockEnabled = true;
    public int oresMining = 100;

    public boolean tradingUnlockEnabled = true;
    public int trading = 30;

    public boolean woodCuttingUnlockEnabled = true;
    public int woodCutting = 170;

    public int calcCost(Trinkets trinkets) {
        int slots = trinkets.getSlots();
        if (slots >= this.maxSlots) {
            return -1;
        }
        if (slots < this.startSlots) {
            return 0;
        }
        return this.slotCost + ((slots - this.startSlots) * this.slotUpFactor);
    }

    public void refresh() {
        UnlockManager.refresh();
    }
}
