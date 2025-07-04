package cn.superiormc.mythictotem.objects.checks;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.managers.ConfigManager;
import cn.superiormc.mythictotem.utils.CommonUtil;
import cn.superiormc.mythictotem.utils.TextUtil;
import com.google.common.base.Enums;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomMob;
import io.th0rgal.oraxen.api.OraxenBlocks;
import io.th0rgal.oraxen.api.OraxenFurniture;
import io.th0rgal.oraxen.mechanics.provided.gameplay.block.BlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanic;
import net.Indyuce.mmoitems.MMOItems;
import net.momirealms.craftengine.bukkit.api.CraftEngineBlocks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public class ObjectMaterialCheck {

    private final String materialString;

    private final Location location;

    private Entity entity;

    private final int id;

    public ObjectMaterialCheck(@NotNull String materialString, @NotNull Location location, int id) {
        this.materialString = materialString;
        this.location = location;
        this.id = id;
    }

    public boolean checkMaterial() {
        Block block;
        String[] tempVal1 = materialString.split(":");
        if (materialString.equals("none")) {
            if (ConfigManager.configManager.getBoolean("debug", false)) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fSkipped none block.");
            }
            return true;
        } else if (materialString.startsWith("minecraft:")) {
            try {
                Material material = Material.getMaterial(tempVal1[1].toUpperCase());
                EntityType entityType = Enums.getIfPresent(EntityType.class, "ZOMBIE").orNull();
                if (material != null) {
                    block = location.getBlock();
                    if (ConfigManager.configManager.getBoolean("debug", false)) {
                        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                                materialString + ", real block: " + block.getType().name() + ", location: " + location + ", ID: " + id + ".");
                    }
                    return material == block.getType();
                } else if (!MythicTotem.freeVersion && entityType != null) {
                    Location tempLocation = location.clone().add(0.5, 0, 0.5);
                    double checkDistance = 0.5;
                    if (tempVal1.length >= 3) {
                        checkDistance = Double.parseDouble(tempVal1[tempVal1.length - 1]);
                    }
                    Collection<Entity> entities = CommonUtil.getNearbyEntity(tempLocation, checkDistance);
                    if (ConfigManager.configManager.getBoolean("debug", false)) {
                        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                                materialString + ", find entities amount: " + entities.size() + ".");
                    }
                    for (Entity singleEntity: entities) {
                        if (ConfigManager.configManager.getBoolean("debug", false)) {
                            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                                    materialString + ", find entity: " + singleEntity.getType() + ".");
                        }
                        if (singleEntity.getType() == entityType) {
                            this.entity = singleEntity;
                            return true;
                        }
                    }
                    return false;
                }
                return false;
            } catch (IllegalArgumentException | NullPointerException ignored) {
            }
        } else if (materialString.startsWith("itemsadder:")) {
            try {
                block = location.getBlock();
                if (materialString.split(":").length != 3) {
                    Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cError: Your itemsadder material does not meet" +
                            " the format claimed in plugin Wiki!");
                    return false;
                }
                CustomBlock iaBlock = CustomBlock.byAlreadyPlaced(block);
                if (iaBlock == null) {
                    return false;
                }
                return (tempVal1[1] + ":" + tempVal1[2]).
                        equals(iaBlock.getNamespacedID());
            } catch (NullPointerException ignored) {
            }
        } else if (materialString.startsWith("itemsadder_furniture:") && !MythicTotem.freeVersion) {
            try {
                if (tempVal1.length < 3) {
                    Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cError: Your itemsadder_furniture material does not meet" +
                            " the format claimed in plugin Wiki!");
                    return false;
                }
                Location tempLocation = location.clone().add(0.5, 0, 0.5);
                double checkDistance = 0.5;
                if (tempVal1.length >= 4) {
                    checkDistance = Double.parseDouble(tempVal1[tempVal1.length - 1]);
                }
                Collection<Entity> entities = CommonUtil.getNearbyEntity(tempLocation, checkDistance);
                if (ConfigManager.configManager.getBoolean("debug", false)) {
                    Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                            materialString + ", find entities amount: " + entities.size() + ".");
                }
                for (Entity singleEntity : entities) {
                    if (ConfigManager.configManager.getBoolean("debug", false)) {
                        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                                materialString + ", find entity: " + singleEntity.getType() + ".");
                    }
                    this.entity = singleEntity;
                    if (singleEntity instanceof Player) {
                        continue;
                    }
                    CustomFurniture iaEntity = CustomFurniture.byAlreadySpawned(singleEntity);
                    if (iaEntity == null) {
                        continue;
                    }
                    if ((tempVal1[1] + ":" + tempVal1[2]).
                            equals(iaEntity.getNamespacedID())) {
                        return true;
                    }
                }
                return false;
            } catch (NullPointerException ignored) {
            }
        } else if (materialString.startsWith("itemsadder_mob:") && !MythicTotem.freeVersion) {
            try {
                if (tempVal1.length < 3) {
                    Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cError: Your itemsadder_mob material does not meet" +
                            " the format claimed in plugin Wiki!");
                    return false;
                }
                Location tempLocation = location.clone().add(0.5, 0, 0.5);
                double checkDistance = 0.5;
                if (tempVal1.length >= 4) {
                    checkDistance = Double.parseDouble(tempVal1[tempVal1.length - 1]);
                }
                Collection<Entity> entities = CommonUtil.getNearbyEntity(tempLocation, checkDistance);
                if (ConfigManager.configManager.getBoolean("debug", false)) {
                    Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                            materialString + ", find entities amount: " + entities.size() + ".");
                }
                for (Entity singleEntity : entities) {
                    if (ConfigManager.configManager.getBoolean("debug", false)) {
                        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                                materialString + ", find entity: " + singleEntity.getType() + ".");
                    }
                    if (singleEntity instanceof Player) {
                        continue;
                    }
                    if (singleEntity instanceof ArmorStand) {
                        this.entity = singleEntity;
                        CustomMob iaEntity = CustomMob.byAlreadySpawned(singleEntity);
                        if (iaEntity == null) {
                            continue;
                        }
                        if ((tempVal1[1] + ":" + tempVal1[2]).
                                equals(iaEntity.getNamespacedID())) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (NullPointerException ignored) {
            }
        } else if (materialString.startsWith("oraxen:")) {
            try {
                block = location.getBlock();
                NoteBlockMechanic tempVal4 = OraxenBlocks.getNoteBlockMechanic(block);
                StringBlockMechanic tempVal2 = OraxenBlocks.getStringMechanic(block);
                BlockMechanic tempVal3 = OraxenBlocks.getBlockMechanic(block);
                if (tempVal3 != null && (tempVal1[1]).equals(tempVal3.getItemID())) {
                    return true;
                } else if (tempVal2 != null && (tempVal1[1]).equals(tempVal2.getItemID())) {
                    return true;
                } else if (tempVal4 != null && (tempVal1[1]).equals(tempVal4.getItemID())) {
                    return true;
                }
            } catch (NullPointerException ignored) {
            }
        } else if (materialString.startsWith("oraxen_furniture:") && !MythicTotem.freeVersion) {
            try {
                if (tempVal1.length < 2) {
                    Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cError: Your oraxen_furniture material does not meet" +
                            " the format claimed in plugin Wiki!");
                    return false;
                }
                Location tempLocation = location.clone().add(0.5, 0, 0.5);
                double checkDistance = 0.5;
                if (tempVal1.length >= 3) {
                    checkDistance = Double.parseDouble(tempVal1[tempVal1.length - 1]);
                }
                Collection<Entity> entities = CommonUtil.getNearbyEntity(tempLocation, checkDistance);
                if (ConfigManager.configManager.getBoolean("debug", false)) {
                    Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                            materialString + ", find entities amount: " + entities.size() + ".");
                }
                for (Entity singleEntity : entities) {
                    if (ConfigManager.configManager.getBoolean("debug", false)) {
                        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fShould be: " +
                                materialString + ", find entity: " + singleEntity.getType() + ".");
                    }
                    this.entity = singleEntity;
                    if (singleEntity instanceof Player) {
                        continue;
                    }
                    FurnitureMechanic furnitureMechanic = OraxenFurniture.getFurnitureMechanic(singleEntity);
                    if (furnitureMechanic == null) {
                        continue;
                    }
                    if (tempVal1[1].equals(furnitureMechanic.getItemID())) {
                        return true;
                    }
                }
                return false;
            } catch (NullPointerException ignored) {
            }
        } else if (materialString.startsWith("mmoitems:")) {
            block = location.getBlock();
            Optional<net.Indyuce.mmoitems.api.block.CustomBlock> opt = MMOItems.plugin.getCustomBlocks().
                    getFromBlock(block.getBlockData());
            return opt.filter(customBlock -> customBlock.getId() == Integer.parseInt(materialString.split(":")[1])).isPresent();
        } else {
            block = location.getBlock();
            try {
                return (Material.getMaterial(tempVal1[1].toUpperCase()) == block.getType());
            } catch (IllegalArgumentException | NullPointerException ignored) {
            }
        }
        return false;
    }

    public Entity getEntityNeedRemove() {
        return entity;
    }
}
