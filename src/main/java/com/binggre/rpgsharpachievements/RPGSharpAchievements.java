package com.binggre.rpgsharpachievements;

import com.binggre.rpgsharpachievements.commands.AdminCommand;
import com.binggre.rpgsharpachievements.commands.UserCommand;
import com.binggre.rpgsharpachievements.listeners.*;
import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.CollectionAddAchievement;
import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroup;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.DBCollection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGSharpAchievements extends JavaPlugin {

    private static RPGSharpAchievements instance = null;

    public static RPGSharpAchievements getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        DBCollection.addCollection(Achievement.COLLECTION, true);
        DBCollection.addCollection(ItemGroup.COLLECTION, true);
        DBCollection.addCollection(RewardGroup.COLLECTION, true);
        this.getCommand("길라잡이").setExecutor(new UserCommand());
        this.getCommand("길라잡이관리").setExecutor(new AdminCommand());
        this.registerAll();
    }

    @Override
    public void onDisable() {
        this.unregisterAll();
    }

    private void unregisterAll() {
        PlayerAchievementLoader playerAchievementLoader = PlayerAchievementLoader.getInstance();
        RPGSharpAPI.getRPGPlayerAPI()
                .getOnlineRPGPlayers()
                .forEach(rpgPlayer -> {
                    PlayerAchievement read = playerAchievementLoader.get(rpgPlayer.toPlayer());
                    read.write(true);
                    read.unregister();
                });
    }

    private void registerAll() {
        ItemGroup.registerAll();
        RewardGroup.registerAll();
        Achievement.registerAll();

        PlayerAchievementLoader playerAchievementLoader = PlayerAchievementLoader.getInstance();
        RPGSharpAPI.getRPGPlayerAPI()
                .getOnlineRPGPlayers()
                .forEach(rpgPlayer -> {
                    PlayerAchievement read = playerAchievementLoader.read(rpgPlayer.toPlayer());
                    read.register();
                    read.write(true);
                });

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RPGSharpReloadListener(), this);
        pm.registerEvents(new CharacterListener(), this);
        pm.registerEvents(new LevelUpListener(), this);
        pm.registerEvents(new MonsterListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new RPGSharpMiningListener(), this);
        pm.registerEvents(new ChangeJobListener(), this);
        pm.registerEvents(new EquipListener(), this);
        pm.registerEvents(new BabelTowerRaidClearListener(), this);
        pm.registerEvents(new AreaEnterListener(), this);
        pm.registerEvents(new CollectionBookListener(), this);
        pm.registerEvents(new FieldBossListener(), this);

        Bukkit.getScheduler().runTask(this, () -> {
            if (pm.isPluginEnabled("RPGSharpDungeon")) {
                pm.registerEvents(new DungeonListener(), this);
            }
            if (pm.isPluginEnabled("Versus")) {
                pm.registerEvents(new VersusListener(), this);
            }
            if (pm.isPluginEnabled("RPGSharpTerritory")) {
                pm.registerEvents(new GuildTerritoryListener(), this);
            }
        });
    }
}