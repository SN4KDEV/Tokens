package fr.sn4kdev.tokens.api;

import fr.sn4kdev.tokens.MainClass;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TokenAPI {

    public static int getTokens(Player player) {
        int tokens = 0;
        if (MainClass.getDataMap().containsKey(player)) {
            return MainClass.getDataMap().get(player).getTokens();
        }
        return tokens;
    }

    public static void addTokens(Player player, int tokens) {
        if (MainClass.getDataMap().containsKey(player)) {
            MainClass.getDataMap().get(player).setTokens(MainClass.getDataMap().get(player).getTokens() + tokens);
        }
    }

    public static void setTokens(Player player, int tokens) {
        if (MainClass.getDataMap().containsKey(player)) {
            MainClass.getDataMap().get(player).setTokens(tokens);
        }
    }

    public static void removeTokens(Player player, int tokens) {
        if (MainClass.getDataMap().containsKey(player)) {
            MainClass.getDataMap().get(player).setTokens(MainClass.getDataMap().get(player).getTokens() - tokens);
        }
    }

    public static HashMap<Player, Integer> getLeaderboard() {
        Arrays.sort(MainClass.getDataMap().entrySet().toArray(), new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<Player, Integer>) o2).getValue().compareTo(((Map.Entry<Player, Integer>) o1).getValue());
            }
        });
        return new HashMap<Player, Integer>();
    }

}