package com.binggre.rpgsharpachievements.objects.enums;

import java.util.HashMap;
import java.util.Map;

public enum AchieveType {

    LEVEL_UP("레벨 달성"),//o
    RAID_COMPLETE("레이드 클리어"),//o
    HUNT("처치"),//o
    PICKUP("획득"),
    VERSUS_WIN("대전 승리"), //o
    VERSUS_TIER_ACHIEVE("대전 티어 달성"), //o
    GUILD_JOIN("길드 가입"),//o
    GUILD_TERRITORY_JOIN("길드 영토 이동"),
    MINING("채광"),//o
    CHANGE_JOB("직업 변경"),
    EQUIP("무기 장착"),
    FIELD_BOSS_CLEAR("필드 보스 처치"),
    BABEL_TOWER_RAID_CLEAR("바벨탑 클리어"),
    AREA_ENTER("구역 입장"),
    COLLECTION_ADD("컬렉션 추가");


    private final String name;

    private static final Map<String, AchieveType> names;

    static {
        names = new HashMap<>();
        for (AchieveType value : values()) {
            names.put(value.getName(), value);
        }
    }

    AchieveType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AchieveType findByName(String name) {
        return names.get(name);
    }
}