package com.binggre.rpgsharpachievements.objects;

import com.binggre.rpgsharpachievements.RPGSharpAchievements;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.FileUtil;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.PlayerUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class PlayerAchievement {

    public static final String COLLECTION = PlayerAchievement.class.getSimpleName();

    private final String uuid;
    private int level;
    private int amount;
    private transient BossBar taskBar;

    public PlayerAchievement(String uuid) {
        this.uuid = uuid;
        this.level = 1;
        this.amount = 0;
        if (getNow() == null) {
            clearTaskBar();
            return;
        }
        this.taskBar = createTaskBar();
    }

    public PlayerAchievement(Player player) {
        this.uuid = player.getUniqueId().toString();
        this.level = 1;
        this.amount = 0;
        if (getNow() == null) {
            clearTaskBar();
            return;
        }
        this.taskBar = createTaskBar();
    }

    private void changeNowTaskBar() {
        if (getNow() == null) {
            return;
        }
        if (taskBar == null) {
            return;
        }
        String title = createTaskBarTitle(getNow()).replace("&", "§");
        taskBar.setTitle(title);
        taskBar.setProgress((double) amount / getNow().getAmount());
    }

    private BossBar createTaskBar() {
        if (getNow() == null) {
            return null;
        }
        String title = createTaskBarTitle(getNow()).replace("&", "§");
        BossBar taskBar = Bukkit.createBossBar(title, BarColor.GREEN,
            BarStyle.SEGMENTED_10);
        taskBar.setProgress((double) amount / getNow().getAmount());
        return taskBar;
    }

    public String createTaskBarTitle(Achievement achievement) {
        List<String> descriptions = Objects.requireNonNull(achievement).getDescription();
        String description = String.join("\n", Objects.requireNonNull(descriptions));
        return description.replace("<amount>", String.valueOf(amount))
            .replace("<max_amount>", String.valueOf(achievement.getAmount()));
    }

    public void sendComplete() {
        Player player = toPlayer();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
            new TextComponent("§a길라잡이를 완료했어요!"));
        PlayerUtil.playSound(player, "UI_TOAST_CHALLENGE_COMPLETE", 1, 1);
    }

    public String getUUID() {
        return uuid;
    }

    @Nullable
    public Achievement getNow() {
        return Achievement.getAchievements().get(this.level);
    }

    public void next() {
        this.amount = 0;
        this.level++;
        if (getNow() == null) {
            clearTaskBar();
            return;
        }
        Bukkit.getScheduler().runTaskLater(RPGSharpAchievements.getInstance(),
            this::changeNowTaskBar, 60);
    }

    public int addAmount() {
        taskBar.setProgress((double) ++amount / Objects.requireNonNull(getNow()).getAmount());
        return amount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAmount() {
        return amount;
    }

    public void clearTaskBar() {
        if (taskBar == null) {
            return;
        }
        taskBar.removeAll();
    }

    public BossBar getTaskBar() {
        return taskBar;
    }

    public void register() {
        PlayerAchievementLoader.getInstance().getPlayers().put(this.uuid, this);
        Player player = Bukkit.getPlayer(UUID.fromString(this.uuid));
        if (getNow() != null) {
            if (this.taskBar == null) {
                this.taskBar = createTaskBar();
            }
            this.taskBar.setVisible(true);
            this.taskBar.addPlayer(Objects.requireNonNull(player));
        }
    }

    public void unregister() {
        clearTaskBar();
        PlayerAchievementLoader.getInstance().getPlayers().remove(this.uuid);
    }

    public void write() {
        this.write(false);
    }

    public void write(boolean sync) {
        FileUtil.write(this, COLLECTION, "uuid", sync);
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(UUID.fromString(this.uuid));
    }
}