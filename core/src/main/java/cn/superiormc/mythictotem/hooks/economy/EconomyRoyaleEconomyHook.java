package cn.superiormc.mythictotem.hooks.economy;

import cn.superiormc.mythictotem.managers.ErrorManager;
import me.qKing12.RoyaleEconomy.API.Currency;
import me.qKing12.RoyaleEconomy.API.MultiCurrencyHandler;
import org.bukkit.entity.Player;

public class EconomyRoyaleEconomyHook extends AbstractEconomyHook {

    public EconomyRoyaleEconomyHook() {
        super("RoyaleEconomy");
    }

    @Override
    public double getEconomy(Player player, String currencyID) {
        Currency reCurrency = MultiCurrencyHandler.findCurrencyById(currencyID);
        if (reCurrency == null) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not find currency " +
                    currencyID + " in RoyaleEconomy plugin!");
            return 0;
        }
        return reCurrency.getAmount(player.getUniqueId().toString());
    }

    @Override
    public void takeEconomy(Player player, double value, String currencyID) {
        Currency reCurrency = MultiCurrencyHandler.findCurrencyById(currencyID);
        if (reCurrency == null) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not find currency " +
                    currencyID + " in RoyaleEconomy plugin!");
            return;
        }
        reCurrency.removeAmount(player.getUniqueId().toString(), value);
    }

    @Override
    public void giveEconomy(Player player, double value, String currencyID) {
        Currency reCurrency = MultiCurrencyHandler.findCurrencyById(currencyID);
        if (reCurrency == null) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not find currency " +
                    currencyID + " in RoyaleEconomy plugin!");
            return;
        }
        reCurrency.addAmount(player.getUniqueId().toString(), value);
    }
}
