package cn.superiormc.mythictotem.hooks.economy;

import cn.superiormc.mythictotem.managers.ErrorManager;
import me.TechsCode.UltraEconomy.UltraEconomy;
import me.TechsCode.UltraEconomy.UltraEconomyAPI;
import org.bukkit.entity.Player;

public class EconomyUltraEconomyHook extends AbstractEconomyHook {

    private final UltraEconomyAPI ueAPI;

    public EconomyUltraEconomyHook() {
        super("UltraEconomy");
        ueAPI = UltraEconomy.getAPI();
    }

    @Override
    public double getEconomy(Player player, String currencyID) {
        if (!UltraEconomy.getAPI().getCurrencies().name(currencyID).isPresent()) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not find currency " + currencyID + " in UltraEconomy plugin!");
            return 0;
        }
        if (UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).isPresent()) {
            return UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).get().getBalance(UltraEconomy.getAPI().getCurrencies().name(currencyID).get()).getOnHand();
        }
        return 0;
    }

    @Override
    public void takeEconomy(Player player, double value, String currencyID) {
        if (!UltraEconomy.getAPI().getCurrencies().name(currencyID).isPresent()) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not find currency " + currencyID + " in UltraEconomy plugin!");
            return;
        }
        if (UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).isPresent()) {
            UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).get().getBalance(UltraEconomy.getAPI().getCurrencies().name(currencyID).get()).removeHand((float) value);
        }
    }

    @Override
    public void giveEconomy(Player player, double value, String currencyID) {
        if (!UltraEconomy.getAPI().getCurrencies().name(currencyID).isPresent()) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not find currency " + currencyID + " in UltraEconomy plugin!");
            return;
        }
        if (UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).isPresent()) {
            UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).get().getBalance(UltraEconomy.getAPI().getCurrencies().name(currencyID).get()).addHand((float) value);
        }
    }

    @Override
    public boolean isEnabled() {
        if (ueAPI == null) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not hook into UltraEconomy plugin!");
            return false;
        }
        return true;
    }
}
