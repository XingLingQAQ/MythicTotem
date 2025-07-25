package cn.superiormc.mythictotem.hooks.economy;

import cn.superiormc.mythictotem.managers.ErrorManager;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.entity.Player;

public class EconomyPlayerPointsHook extends AbstractEconomyHook {

    private final PlayerPoints playerPoints;

    public EconomyPlayerPointsHook() {
        super("PlayerPoints");
        playerPoints = PlayerPoints.getInstance();
    }

    @Override
    public double getEconomy(Player player, String currencyID) {
        return playerPoints.getAPI().look(player.getUniqueId());
    }

    @Override
    public void takeEconomy(Player player, double value, String currencyID) {
        playerPoints.getAPI().take(player.getUniqueId(), (int) value);
    }

    @Override
    public void giveEconomy(Player player, double value, String currencyID) {
        playerPoints.getAPI().give(player.getUniqueId(), (int) value);
    }

    @Override
    public boolean isEnabled() {
        if (playerPoints == null) {
            ErrorManager.errorManager.sendErrorMessage("§cCan not hook into PlayerPoints plugin, " +
                    "maybe your are using old version, please try update it to newer version!");
            return false;
        }
        return true;
    }
}
