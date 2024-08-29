package com.binggre.rpgsharpachievements.objects;

import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerAchievementLoader {

    private static PlayerAchievementLoader instance = null;

    public static PlayerAchievementLoader getInstance() {
        if (instance == null) {
            instance = new PlayerAchievementLoader();
        }
        return instance;
    }

    private final Map<String, PlayerAchievement> players;

    private PlayerAchievementLoader() {
        this.players = new HashMap<>();
    }

    public PlayerAchievement readOnly(String uuid) {
        return (PlayerAchievement) FileUtil.read(PlayerAchievement.class, PlayerAchievement.COLLECTION, "uuid", uuid, false);
    }

    public PlayerAchievement read(Player player) {
        String uuid = player.getUniqueId().toString();
        PlayerAchievement obj = readOnly(uuid);
        return obj == null ? new PlayerAchievement(uuid) : obj;
    }

    public PlayerAchievement get(String nickname) {
        return get(Objects.requireNonNull(Bukkit.getPlayer(nickname)));
    }

    public PlayerAchievement get(Player player) {
        return this.players.get(player.getUniqueId().toString());
    }

    public Map<String, PlayerAchievement> getPlayers() {
        return players;
    }
}