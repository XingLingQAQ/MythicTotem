package cn.superiormc.mythictotem.managers;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.methods.BuildItem;
import cn.superiormc.mythictotem.methods.DebuildItem;
import cn.superiormc.mythictotem.utils.SchedulerUtil;
import cn.superiormc.mythictotem.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private final Map<String, ItemStack> savedItemMap = new HashMap<>();

    private final Map<String, ConfigurationSection> savedItemFormatMap = new HashMap<>();

    public static ItemManager itemManager;

    public ItemManager() {
        itemManager = this;
        initSavedItems();
    }

    public void initSavedItems() {
        savedItemMap.clear();
        File dir = new File(MythicTotem.instance.getDataFolder() + "/items");
        if(!dir.exists()) {
            dir.mkdir();
        }
        File[] tempList = dir.listFiles();
        if (tempList == null) {
            return;
        }
        for (File file : tempList) {
            if (file.getName().endsWith(".yml")) {
                YamlConfiguration section = YamlConfiguration.loadConfiguration(file);
                String key = file.getName();
                key = key.substring(0, key.length() - 4);
                Object object = section.get("item");
                if (section.getKeys(false).size() == 1 && object != null) {
                    savedItemMap.put(key, MythicTotem.methodUtil.getItemObject(object));
                    MythicTotem.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fLoaded Bukkit Saved Item: " + key + ".yml!");
                } else {
                    savedItemFormatMap.put(key, section);
                    MythicTotem.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fLoaded ItemFormat Saved Item: " + key + ".yml!");
                }
            }
        }
    }

    public void saveMainHandItem(Player player, String key) {
        ItemStack item = player.getInventory().getItemInMainHand();
        File dir = new File(MythicTotem.instance.getDataFolder() + "/items");
        if (!dir.exists()) {
            dir.mkdir();
        }
        YamlConfiguration briefcase = new YamlConfiguration();
        briefcase.set("item", MythicTotem.methodUtil.makeItemToObject(item));
        String yaml = briefcase.saveToString();
        SchedulerUtil.runTaskAsynchronously(() -> {
            Path path = new File(dir.getPath(), key + ".yml").toPath();
            try {
                Files.write(path, yaml.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        savedItemMap.put(key, item);
    }

    public void saveMainHandItemFormat(Player player, String key) {
        File dir = new File(MythicTotem.instance.getDataFolder() + "/items");
        if (!dir.exists()) {
            dir.mkdir();
        }
        YamlConfiguration briefcase = new YamlConfiguration();
        DebuildItem.debuildItem(player.getInventory().getItemInMainHand(), briefcase);
        String yaml = briefcase.saveToString();
        SchedulerUtil.runTaskAsynchronously(() -> {
            Path path = new File(dir.getPath(), key + ".yml").toPath();
            try {
                Files.write(path, yaml.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        savedItemFormatMap.put(key, briefcase);
    }

    public ItemStack getItemByKey(Player player, String key) {
        if (savedItemMap.containsKey(key)) {
            return savedItemMap.get(key).clone();
        }
        if (savedItemFormatMap.containsKey(key) && player != null) {
            return BuildItem.buildItemStack(player, savedItemFormatMap.get(key), savedItemFormatMap.get(key).getInt("amount", 1));
        }
        return null;
    }

    public Map<String, ItemStack> getSavedItemMap() {
        return savedItemMap;
    }

    public Map<String, ConfigurationSection> getSavedItemFormatMap() {
        return savedItemFormatMap;
    }
}
