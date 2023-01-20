package fr.sn4kdev.tokens.events;

import fr.sn4kdev.tokens.MainClass;
import fr.sn4kdev.tokens.object.PlayerObject;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!MainClass.getDataMap().containsKey(e.getPlayer())) MainClass.getDataMap().put(e.getPlayer(), new PlayerObject(e.getPlayer()));
        MainClass.getDataMap().get(e.getPlayer()).loadAccount();
    }

    @org.bukkit.event.EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        MainClass.getDataMap().get(e.getPlayer()).saveAccount();
    }

}
