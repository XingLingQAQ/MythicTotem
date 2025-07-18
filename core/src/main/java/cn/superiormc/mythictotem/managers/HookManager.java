package cn.superiormc.mythictotem.managers;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.hooks.economy.*;
import cn.superiormc.mythictotem.hooks.items.*;
import cn.superiormc.mythictotem.hooks.protection.*;
import cn.superiormc.mythictotem.utils.CommonUtil;
import cn.superiormc.mythictotem.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HookManager {

    public static HookManager hookManager;

    private Map<String, AbstractEconomyHook> economyHooks;

    private Map<String, AbstractItemHook> itemHooks;

    private Map<String, AbstractProtectionHook> protectionHooks;

    public HookManager() {
        hookManager = this;
        initProtectionHook();
        initEconomyHook();
        initItemHook();
    }

    private void initEconomyHook() {
        economyHooks = new HashMap<>();
        if (CommonUtil.checkPluginLoad("Vault")) {
            registerNewEconomyHook("Vault", new EconomyVaultHook());
        }
        if (CommonUtil.checkPluginLoad("PlayerPoints")) {
            registerNewEconomyHook("PlayerPoints", new EconomyPlayerPointsHook());
        }
        if (CommonUtil.checkPluginLoad("CoinsEngine")) {
            registerNewEconomyHook("CoinsEngine", new EconomyCoinsEngineHook());
        }
        if (CommonUtil.checkPluginLoad("UltraEconomy")) {
            registerNewEconomyHook("UltraEconomy", new EconomyUltraEconomyHook());
        }
        if (CommonUtil.checkPluginLoad("EcoBits")) {
            registerNewEconomyHook("EcoBits", new EconomyEcoBitsHook());
        }
        if (CommonUtil.checkPluginLoad("PEconomy")) {
            registerNewEconomyHook("PEconomy", new EconomyPEconomyHook());
        }
        if (CommonUtil.checkPluginLoad("RedisEconomy")) {
            registerNewEconomyHook("RedisEconomy", new EconomyRedisEconomyHook());
        }
        if (CommonUtil.checkPluginLoad("RoyaleEconomy")) {
            registerNewEconomyHook("RoyaleEconomy", new EconomyRoyaleEconomyHook());
        }
        if (CommonUtil.checkPluginLoad("VotingPlugin")) {
            registerNewEconomyHook("VotingPlugin", new EconomyVotingPluginHook());
        }
    }

    private void initItemHook() {
        itemHooks = new HashMap<>();
        if (CommonUtil.checkPluginLoad("ItemsAdder")) {
            registerNewItemHook("ItemsAdder", new ItemItemsAdderHook());
        }
        if (CommonUtil.checkPluginLoad("Oraxen")) {
            registerNewItemHook("Oraxen", new ItemOraxenHook());
        }
        if (CommonUtil.checkPluginLoad("MMOItems")) {
            registerNewItemHook("MMOItems", new ItemMMOItemsHook());
        }
        if (CommonUtil.checkPluginLoad("EcoItems")) {
            registerNewItemHook("EcoItems", new ItemEcoItemsHook());
        }
        if (CommonUtil.checkPluginLoad("EcoArmor")) {
            registerNewItemHook("EcoArmor", new ItemEcoArmorHook());
        }
        if (CommonUtil.checkPluginLoad("MythicMobs")) {
            registerNewItemHook("MythicMobs", new ItemMythicMobsHook());
        }
        if (CommonUtil.checkPluginLoad("eco")) {
            registerNewItemHook("eco", new ItemecoHook());
        }
        if (CommonUtil.checkPluginLoad("NeigeItems")) {
            registerNewItemHook("NeigeItems", new ItemNeigeItemsHook());
        }
        if (CommonUtil.checkPluginLoad("ExecutableItems")) {
            registerNewItemHook("ExecutableItems", new ItemExecutableItemsHook());
        }
        if (CommonUtil.checkPluginLoad("Nexo")) {
            registerNewItemHook("Nexo", new ItemNexoHook());
        }
        if (CommonUtil.checkPluginLoad("CraftEngine")) {
            registerNewItemHook("CraftEngine", new ItemCraftEngineHook());
        }
    }

    private void initProtectionHook() {
        protectionHooks = new HashMap<>();
        if (CommonUtil.checkPluginLoad("WorldGuard")) {
            registerNewProtectionHook("WorldGuard", new ProtectionWorldGuardHook());
        }
        if (CommonUtil.checkPluginLoad("Residence")) {
            registerNewProtectionHook("Residence", new ProtectionResidenceHook());
        }
        if (CommonUtil.checkPluginLoad("GriefPrevention")) {
            registerNewProtectionHook("GriefPrevention", new ProtectionGriefPreventionHook());
        }
        if (CommonUtil.checkPluginLoad("Lands")) {
            registerNewProtectionHook("Lands", new ProtectionLandsHook());
        }
        if (CommonUtil.checkPluginLoad("HuskTowns")) {
            registerNewProtectionHook("HuskTowns", new ProtectionHuskTownsHook());
        }
        if (CommonUtil.checkPluginLoad("HuskClaims")) {
            registerNewProtectionHook("HuskClaims", new ProtectionHuskClaimsHook());
        }
        if (CommonUtil.checkPluginLoad("PlotSquared")) {
            registerNewProtectionHook("PlotSquared", new ProtectionPlotSquaredHook());
        }
        if (CommonUtil.checkPluginLoad("Towny")) {
            registerNewProtectionHook("Towny", new ProtectionTownyHook());
        }
        if (CommonUtil.checkPluginLoad("BentoBox")) {
            registerNewProtectionHook("BentoBox", new ProtectionBentoBoxHook());
        }
        if (CommonUtil.checkPluginLoad("Dominion")) {
            registerNewProtectionHook("Dominion", new ProtectionDominionHook());
        }
    }

    public void registerNewEconomyHook(String pluginName,
                                       AbstractEconomyHook economyHook) {
        if (!economyHooks.containsKey(pluginName)) {
            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fHooking into " + pluginName + "...");
            economyHooks.put(pluginName, economyHook);
        }
    }

    public void registerNewItemHook(String pluginName,
                                    AbstractItemHook itemHook) {
        if (!itemHooks.containsKey(pluginName)) {
            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fHooking into " + pluginName + "...");
            itemHooks.put(pluginName, itemHook);
        }
    }

    public void registerNewProtectionHook(String pluginName,
                                    AbstractProtectionHook protectionHook) {
        if (!protectionHooks.containsKey(pluginName)) {
            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fHooking into " + pluginName + "...");
            protectionHooks.put(pluginName, protectionHook);
        }
    }

    public boolean getPrice(Player player,
                            String pluginName,
                            String currencyID,
                            double value,
                            boolean take) {
        if (value < 0) {
            return false;
        }
        if (!economyHooks.containsKey(pluginName)) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Can not hook into "
                    + pluginName + " plugin, maybe we do not support this plugin, or your server didn't correctly load " +
                    "this plugin!");
            return false;
        }
        AbstractEconomyHook economyHook = economyHooks.get(pluginName);
        if (player.hasPermission("ultimateshop.bypassprice")) {
            return true;
        }
        return economyHook.isEnabled() && economyHook.checkEconomy(player, value, take, currencyID);
    }

    public boolean getPrice(Player player, String vanillaType, int value, boolean take) {
        vanillaType = vanillaType.toLowerCase();
        if (vanillaType.equals("exp")) {
            if (player.getTotalExperience() >= value) {
                if (take) {
                    player.giveExp(-value);
                }
                return true;
            }
            return false;
        }
        else if (vanillaType.equals("levels")) {
            if (player.getLevel() >= value) {
                if (take) {
                    player.giveExpLevels(-value);
                }
                return true;
            }
            return false;
        }
        ErrorManager.errorManager.sendErrorMessage("§cError: You set economy type to "
                + vanillaType + " in shop config, however for now UltimateShop does not support it!");
        return false;
    }

    public ItemStack getHookItem(Player player, String pluginName, String itemID) {
        if (!itemHooks.containsKey(pluginName)) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Can not hook into "
                    + pluginName + " plugin, maybe we do not support this plugin, or your server didn't correctly load " +
                    "this plugin!");
            return null;
        }
        AbstractItemHook itemHook = itemHooks.get(pluginName);
        return itemHook.getHookItemByID(player, itemID);
    }

    public void giveEconomy(String pluginName, String currencyName, Player player, double value) {
        if (!economyHooks.containsKey(pluginName)) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Can not hook into "
                    + pluginName + " plugin, maybe we do not support this plugin, or your server didn't correctly load " +
                    "this plugin!");
            return;
        }
        AbstractEconomyHook economyHook = economyHooks.get(pluginName);
        if (!economyHook.isEnabled()) {
            return;
        }
        economyHook.giveEconomy(player, value, currencyName);
    }

    public void giveEconomy(String vanillaType, Player player, int value) {
        vanillaType = vanillaType.toLowerCase();
        if (vanillaType.equals("exp")) {
            player.giveExp(value);
            return;
        } else if (vanillaType.equals("levels")) {
            player.giveExpLevels(value);
            return;
        }
        ErrorManager.errorManager.sendErrorMessage("§cError: You set economy type to "
                + vanillaType + " in shop config, however for now UltimateShop does not support it!");
        return;
    }

    public void takeEconomy(String pluginName, String currencyName, Player player, double value) {
        if (!economyHooks.containsKey(pluginName)) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Can not hook into "
                    + pluginName + " plugin, maybe we do not support this plugin, or your server didn't correctly load " +
                    "this plugin!");
            return;
        }
        AbstractEconomyHook economyHook = economyHooks.get(pluginName);
        if (!economyHook.isEnabled()) {
            return;
        }
        economyHook.takeEconomy(player, value, currencyName);
    }

    public String getHookItemID(String pluginName, ItemStack hookItem) {
        if (!hookItem.hasItemMeta()) {
            return null;
        }
        if (!itemHooks.containsKey(pluginName)) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Can not hook into "
                    + pluginName + " plugin, maybe we do not support this plugin, or your server didn't correctly load " +
                    "this plugin!");
            return null;
        }
        AbstractItemHook itemHook = itemHooks.get(pluginName);
        return itemHook.getIDByItemStack(hookItem);
    }

    public String[] getHookItemPluginAndID(ItemStack hookItem) {
        for (AbstractItemHook itemHook : itemHooks.values()) {
            String itemID = itemHook.getIDByItemStack(hookItem);
            if (itemID != null) {
                return new String[]{itemHook.getPluginName(), itemHook.getIDByItemStack(hookItem)};
            }
        }
        return null;
    }

    public boolean getProtectionCanUse(Player player, Location location) {
        if (MythicTotem.freeVersion || player == null || player.isOp() || player.hasPermission("mythictotem.bypass.protection")) {
            return true;
        }
        for (AbstractProtectionHook protectionHook : protectionHooks.values()) {
            if (!protectionHook.canUse(player, location)) {
                return false;
            }
        }
        return true;
    }
}
