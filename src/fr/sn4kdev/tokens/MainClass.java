package fr.sn4kdev.tokens;

import fr.sn4kdev.tokens.commands.TokensCommand;
import fr.sn4kdev.tokens.database.DataUtils;
import fr.sn4kdev.tokens.database.MySQL;

import fr.sn4kdev.tokens.events.EventHandler;
import fr.sn4kdev.tokens.object.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass extends JavaPlugin {

    private MySQL database;

    public Logger log;

    private static HashMap<Player, PlayerObject> dataMap;

    private static MainClass plugin;

    @Override
    public void onEnable()
    {
        plugin = this;

        log = getLogger();

        saveDefaultConfig();

        database = new MySQL(
                getConfig().getString("database.host"),
                getConfig().getInt("database.port"),
                getConfig().getString("database.database"),
                getConfig().getString("database.user"),
                getConfig().getString("database.password")
        );
        dataMap = new HashMap<Player, PlayerObject>();

        try {
            DataUtils.getInstance().executeUpdate("CREATE TABLE IF NOT EXISTS " + getConfig().getString("database.table") + "(UUID VARCHAR(36) NOT NULL, TOKENS INT(8) NOT NULL, PRIMARY KEY (UUID)) COLLATE=utf8mb4_0900_ai_ci", ps -> {
                log.log(Level.INFO, "MySQL components loaded !");
            });
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }

        Bukkit.getOnlinePlayers().stream().forEach((k) -> {
            dataMap.put(k, new PlayerObject(k));
            dataMap.get(k).loadAccount();
        });

        getServer().getPluginManager().registerEvents(new EventHandler(), this);
        getCommand("tokens").setExecutor(new TokensCommand());

        super.onEnable();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().stream().forEach((k) -> {
            if (dataMap.containsKey(k)) dataMap.get(k).saveAccount();
        });
        super.onDisable();
    }

    public MySQL getDatabase() { return database; }

    public static HashMap<Player, PlayerObject> getDataMap() { return dataMap; }

    public static MainClass getPlugin() { return plugin; }

}
