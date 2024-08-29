package com.binggre.rpgsharpachievements.objects.itemgroups;

import com.binggre.rpgsharpachievements.commands.AdminCommand;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGItemAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.socket.SocketType;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.FileUtil;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.Serializer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemGroup {

    public static final String COLLECTION = "Achievement" + ItemGroup.class.getSimpleName();
    private static final Map<String, ItemGroup> groups;

    static {
        groups = new HashMap<>();
    }

    public static void registerAll() {
        groups.clear();
        List<ItemGroup> read = (List<ItemGroup>) FileUtil.read(ItemGroup.class, COLLECTION, true);
        read.forEach(itemGroup -> {
            itemGroup.deserialize();
            itemGroup.register();
        });
    }

    public static Map<String, ItemGroup> getGroups() {
        return groups;
    }

    private final String name;
    private String serializeItemStacks;
    private transient List<ItemStack> itemStacks;

    public ItemGroup(String name) {
        this.name = name;
        this.itemStacks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void refreshServers() {
        RPGSharpAPI.getSocketClient()
                .send(SocketType.CONSOLE_COMMAND, AdminCommand.RELOAD_ITEM_GROUP);
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

    public List<ItemStack> getItemStacks() {
        return itemStacks;
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

    public void write() {
        this.serialize();
        FileUtil.write(this, COLLECTION, "name", true);
        this.refreshServers();
    }
}