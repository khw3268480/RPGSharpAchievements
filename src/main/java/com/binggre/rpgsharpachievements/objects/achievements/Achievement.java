package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.RPGSharpAchievements;
import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.enums.AchieveType;
import com.binggre.rpgsharpachievements.objects.exceptions.DuplicateException;
import com.binggre.rpgsharpachievements.objects.exceptions.NotFoundAchieveTypeException;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import com.google.gson.Gson;
import com.hj.rpgsharp.rpg.apis.database.RPGSharpDB;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.CommandUtil;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.HexColorUtil;
import net.kyori.adventure.title.Title;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Achievement {

    protected final int level;
    protected final String typeName;
    protected final String objectName;
    protected final int amount;
    protected final String rewardCommand;
    protected final String rewardGroup;
    protected final List<String> description;

    public Achievement(int level, String typeName, String objectName, int amount, String rewardGroup, String rewardCommand) {
        this.level = level;
        this.typeName = typeName;
        this.rewardGroup = rewardGroup;
        this.objectName = objectName;
        this.amount = amount;
        this.rewardCommand = rewardCommand;
        this.description = new ArrayList<>();
    }

    protected void processComplete(PlayerAchievement playerAchievement) {
        Player player = playerAchievement.toPlayer();
        String completeMessage = "&b 길라잡이 클리어 !!".replace("&", "§");
        playerAchievement.getTaskBar().setTitle(completeMessage);
        if (rewardCommand != null && !rewardCommand.isEmpty()) {
            String command = this.rewardCommand.replace("<nickname>", player.getName());
            CommandUtil.runCommand(player, command, true);
        }
        if (rewardGroup != null && !rewardGroup.isEmpty()) {
            RewardGroup reward = RewardGroup.getGroups().get(rewardGroup);
            reward.sendMail(player);
        }
        playerAchievement.next();
        playerAchievement.write();
    }

    public List<String> getDescription() {
        return new ArrayList<>(description);
    }

    public int getAmount() {
        return amount;
    }

    public int getLevel() {
        return level;
    }

    public AchieveType getType() {
        return AchieveType.findByName(this.typeName);
    }

    public String getObjectName() {
        return objectName;
    }

    public String getRewardCommand() {
        return rewardCommand;
    }

    public static final String COLLECTION = Achievement.class.getSimpleName();
    private static final Map<Integer, Achievement> achievements = new HashMap<>();

    public static Map<Integer, Achievement> getAchievements() {
        return achievements;
    }

    @Override
    public String toString() {
        return "Achievement{" +
            "level=" + level +
            ", typeName='" + typeName + '\'' +
            ", objectName='" + objectName + '\'' +
            ", amount=" + amount +
            ", rewardCommand='" + rewardCommand + '\'' +
            ", rewardGroup='" + rewardGroup + '\'' +
            ", description=" + description +
            '}';
    }

    public static void registerAll() {
        achievements.clear();
        RPGSharpDB rpgSharpDB = RPGSharpDB.getRPGSharpDB(COLLECTION);
        List<Document> documents = rpgSharpDB.downloadAll();
        for (Document document : documents) {
            String typeName = document.getString("typeName");
            AchieveType type = AchieveType.findByName(typeName);
            Achievement achievement;
            switch (type) {
                case HUNT -> achievement = cast(document, MonsterHuntAchievement.class);
                case GUILD_JOIN -> achievement = cast(document, GuildJoinAchievement.class);
                case RAID_COMPLETE -> achievement = cast(document, RaidAchievement.class);
                case PICKUP -> achievement = cast(document, ItemPickUpAchievement.class);
                case GUILD_TERRITORY_JOIN -> achievement = cast(document, GuildTerritoryJoinAchievement.class);
                case LEVEL_UP -> achievement = cast(document, LevelUpAchievement.class);
                case VERSUS_TIER_ACHIEVE -> achievement = cast(document, VersusPromoteAchievement.class);
                case VERSUS_WIN -> achievement = cast(document, VersusWinAchievement.class);
                case MINING -> achievement = cast(document, MiningAchievement.class);
                case CHANGE_JOB -> achievement = cast(document,ChangeJobAchievement.class);
                case EQUIP -> achievement = cast(document,EquipAchievement.class);
                case FIELD_BOSS_CLEAR -> achievement = cast(document,FieldBossClearAchievement.class);
                case BABEL_TOWER_RAID_CLEAR -> achievement = cast(document,BabelTowerRaidClearAchievement.class);
                case AREA_ENTER -> achievement = cast(document,AreaEnterAchievement.class);
                case COLLECTION_ADD -> achievement = cast(document,CollectionAddAchievement.class);
                default ->
                        throw new NotFoundAchieveTypeException("존재하지 않는 typeName입니다. (type name = " + typeName + ") 로드가 중지되었습니다.");
            }
            int level = achievement.getLevel();
            if (achievements.containsKey(level)) {
                throw new DuplicateException("중복 도전 과제가 발견되었습니다. (level " + level + ") 로드가 중지되었습니다.");
            }
            HexColorUtil.format(achievement.getDescription());
            achievements.put(level, achievement);
        }
    }

    private static Achievement cast(Document document, Class<? extends Achievement> clazz) {
        Gson gson = new Gson();
        String json = document.toJson();
        return gson.fromJson(json, clazz);
    }
}