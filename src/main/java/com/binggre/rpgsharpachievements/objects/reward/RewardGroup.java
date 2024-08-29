package com.binggre.rpgsharpachievements.objects.reward;

import com.binggre.rpgsharpachievements.commands.AdminCommand;
import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGItemAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.socket.SocketType;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.FileUtil;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.Serializer;
import com.hj.rpgsharp.rpg.objects.RPGPlayer;
import com.hj.rpgsharp.rpg.plugins.mailbox.objects.Mail;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardGroup {

    public static final String COLLECTION = "Achievement" + RewardGroup.class.getSimpleName();
    private static final Map<String, RewardGroup> groups;

    static {
        groups = new HashMap<>();
    }

    public static void registerAll() {
        groups.clear();
        List<RewardGroup> read = (List<RewardGroup>) FileUtil.read(RewardGroup.class, COLLECTION, true);
        read.forEach(rewardGroup -> {
            rewardGroup.deserialize();
            rewardGroup.register();
        });
    }

    public static Map<String, RewardGroup> getGroups() {
        return groups;
    }

    private final String name;
    private String serializeItemStacks;
    private transient List<ItemStack> itemStacks;

    public RewardGroup(String name) {
        this.name = name;
        this.itemStacks = new ArrayList<>();
    }

    public void serialize() {
        if (itemStacks != null && itemStacks.isEmpty()) {
            this.serializeItemStacks = null;
            return;
        }
        assert this.itemStacks != null;
        this.serializeItemStacks = Serializer.serializeItems(this.itemStacks);
    }

    public void deserialize() {
        if (this.serializeItemStacks == null) {
            return;
        }
        this.itemStacks = Serializer.deserializeItems(this.serializeItemStacks);
        this.updateItemStacks(this.itemStacks);
    }

    public void setItemStacks(Inventory inventory) {
        this.itemStacks.clear();
        for (ItemStack content : inventory.getContents()) {
            if (content == null) {
                continue;
            }
            this.itemStacks.add(content);
        }
    }

    private void updateItemStacks(List<ItemStack> itemStacks) {
        RPGItemAPI rpgItemAPI = RPGSharpAPI.getRPGItemAPI();
        int size = itemStacks.size();

        for (int i = 0; i < size; i++) {
            ItemStack itemStack = itemStacks.get(i);
            boolean hasDataCode = rpgItemAPI.hasDataCode(itemStack);

            if (!hasDataCode) {
                continue;
            }
            ItemStack updatedItem = rpgItemAPI.update(itemStack);
            itemStacks.set(i, updatedItem);
        }
    }

    public void refreshServers() {
        RPGSharpAPI.getSocketClient()
                .send(SocketType.CONSOLE_COMMAND, AdminCommand.RELOAD_REWARD_GROUP);
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public void register() {
        groups.put(this.name, this);
        this.refreshServers();
    }

    public void unregister() {
        groups.remove(this.name);
        FileUtil.delete(COLLECTION, "name", this.name, true);
        this.refreshServers();
    }

    public void write() {
        this.serialize();
        FileUtil.write(this, COLLECTION, "name", true);
        this.refreshServers();
    }

    public void sendMail(Player player) {
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        int level = playerAchievement.getLevel();
        RPGPlayer rpgPlayer = RPGSharpAPI.getRPGPlayerAPI().getRPGPlayer(player);
        Mail mail = Mail.createMail("길라잡이 보상", level + "번째 길라잡이를 완료했습니다.", 0, this.itemStacks);
        rpgPlayer.getRPGMail().addMail(mail);
        rpgPlayer.write();
    }
}