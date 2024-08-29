package com.binggre.rpgsharpachievements.commands.handlers.userhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.*;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import com.binggre.rpgsharpcollectionbook.objects.CollectionBook;
import com.binggre.rpgsharpcollectionbook.objects.CollectionType;
import com.binggre.rpgsharpcollectionbook.objects.PlayerCollectionBook;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGPlayerAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.CommandUtil;
import com.hj.rpgsharp.rpg.objects.RPGPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CompletionHandler extends SimpleCommandHandler {
    private static final CollectionBook EMPTY_COLLECTIONBOOK = null;

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        RPGPlayer rpgPlayer = RPGSharpAPI.getRPGPlayerAPI().getRPGPlayer((Player) sender);
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c플레이어 전용 명령어 입니다.");
            return false;
        }
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get((Player) sender);
        Achievement now = playerAchievement.getNow();

        if ( now instanceof CollectionAddAchievement collectionAddAchievement){
            for(CollectionBook collectionNames: PlayerCollectionBook.get(player).getCollections(CollectionType.ITEM)){
                String collectionName = collectionNames.getName();
                System.out.println(String.format("%s", collectionName));
                if ( collectionAddAchievement.getCollectionNames().contains(collectionName)){
                    collectionAddAchievement.complete(playerAchievement);
                    return true;
                }
            }
        }


        if ( now instanceof ChangeJobAchievement changeJobAchievement){
            if (changeJobAchievement.getJobs().contains(rpgPlayer.getJob(false))){
                playerAchievement.sendComplete();
                changeJobAchievement.complete(playerAchievement);
                return false;
            }
        }
        if ( now instanceof LevelUpAchievement levelUpAchievement){
            if ( levelUpAchievement.getGoalsLevel() <= rpgPlayer.getLevel()){
                levelUpAchievement.complete(playerAchievement);
                return false;
            }
        }
        if ( now instanceof FieldBossClearAchievement fieldBossClearAchievement){
            int level = rpgPlayer.getLevel();
            switch (playerAchievement.getLevel()){
                case 26: {
                    if (level >= 100){
                        fieldBossClearAchievement.complete(playerAchievement);
                        return false;
                    }
                }
                case 33: {
                    if (level >= 100){
                        fieldBossClearAchievement.complete(playerAchievement);
                        return false;
                    }
                }
                case 49: {
                    if (level >= 150){
                        fieldBossClearAchievement.complete(playerAchievement);
                        return false;
                    }
                }
                case 66: {
                    if ( level >= 200){
                        fieldBossClearAchievement.complete(playerAchievement);
                        return false;
                    }
                }
                case 75: {
                    if (level >= 200){
                        fieldBossClearAchievement.complete(playerAchievement);
                        return false;
                    }
                }
                case 93: {
                    if ( level>= 215){
                        fieldBossClearAchievement.complete(playerAchievement);
                        return false;
                    }
                }
            }
        }

//        if ( now instanceof CollectionAddAchievement collectionAddAchievement){
//            List<CollectionBook> collections = PlayerCollectionBook.get(player).getCollections(CollectionType.ITEM);
//            CollectionBook collectionBook = collections.stream()
//                    .filter(book -> book.getName().equals(collectionAddAchievement.getObjectName()))
//                    .findFirst()
//                    .orElse(EMPTY_COLLECTIONBOOK);
//            if (collectionBook != EMPTY_COLLECTIONBOOK){
//                playerAchievement.sendComplete();
//            }
//        }

        if (!(now instanceof ItemPickUpAchievement achievement)) {
            return false;
        }
        if (!achievement.complete(playerAchievement)) {
            sender.sendMessage("§c모두 획득하지 않았습니다.");
            return false;
        } else {
            playerAchievement.sendComplete();
        }
        return false;
    }
}