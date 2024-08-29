package com.binggre.rpgsharpachievements.commands.handlers.adminhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGPlayerAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.socket.SocketType;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.NumberUtil;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.PlayerUtil;
import com.hj.rpgsharp.rpg.objects.RPGPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PlayerLevelEditHandler extends SimpleCommandHandler {

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("/길라잡이관리 레벨설정 <닉네임> <레벨>");
            return false;
        }
        RPGPlayerAPI rpgPlayerAPI = RPGSharpAPI.getRPGPlayerAPI();
        String nickname = args[1];
        int level = NumberUtil.parseInt(args[2]);
        if (level == -1) {
            sender.sendMessage("§c숫자를 입력해 주세요.");
            return false;
        }
        String uuid = rpgPlayerAPI.getUUID(nickname);
        if (uuid == null) {
            sender.sendMessage(nickname + "§c님은 존재하지 않는 플레이어입니다.");
            return false;
        }
        PlayerAchievementLoader playerLoader = PlayerAchievementLoader.getInstance();
        if (PlayerUtil.isOnline(nickname)) {
            RPGPlayer rpgPlayer = rpgPlayerAPI.getRPGPlayer((Object) nickname);

            if (rpgPlayer == null) {
                String cmd = String.format("길라잡이관리 레벨설정 %s %s", nickname, level);
                RPGSharpAPI.getSocketClient().send(SocketType.CONSOLE_COMMAND, cmd);
            } else {
                PlayerAchievement playerAchievement = playerLoader.get(nickname);
                updateLevelAndWrite(playerAchievement, level);
            }
        } else {
            PlayerAchievement playerAchievement = playerLoader.readOnly(uuid);
            updateLevelAndWrite(playerAchievement, level);
        }
        sender.sendMessage(String.format("%s님의 길라잡이 레벨을 %s으(로) 설정했습니다.", nickname, level));
        return true;
    }

    private void updateLevelAndWrite(PlayerAchievement playerAchievement, int level) {
        playerAchievement.setLevel(level);
        playerAchievement.write();
    }
}
