package fr.sn4kdev.tokens.object;

import fr.sn4kdev.tokens.MainClass;
import fr.sn4kdev.tokens.database.DataUtils;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class PlayerObject {

    private UUID uuid;
    private int tokens;

    public PlayerObject(Player player) {
        this.uuid = player.getUniqueId();
    }

    public boolean exist() {
        AtomicBoolean fetchedValue = new AtomicBoolean();
        try {
            DataUtils.getInstance().executeQuery("SELECT * FROM " + MainClass.getPlugin().getConfig().getString("database.table") + " WHERE UUID =?", ps ->
                    ps.setString(1, getUUID().toString()), rs -> {
                if (rs.next()) { fetchedValue.set(true); }
                return rs;
            });
        } catch (Exception e) {
            MainClass.getPlugin().log.log(Level.SEVERE, "Error while checking player database integration !");
        }
        return fetchedValue.get();
    }

    public void loadAccount() {
        try {
            CompletableFuture.runAsync(() -> {
                if (!exist()) {
                    DataUtils.getInstance().executeUpdate("INSERT INTO " + MainClass.getPlugin().getConfig().getString("database.table") + "(UUID,TOKENS) VALUES(?,?)", ps -> {
                        ps.setString(1, getUUID().toString());
                        ps.setInt(2, 0);
                    });
                } else {
                    DataUtils.getInstance().executeQuery("SELECT * FROM " + MainClass.getPlugin().getConfig().getString("database.table") + " WHERE UUID =?", ps ->
                            ps.setString(1, getUUID().toString()), rs -> {
                        if (rs.next()) {
                            setTokens(rs.getInt("TOKENS"));
                        }

                        return rs;
                    });
                }
            });
        } catch (Exception e) {
            MainClass.getPlugin().log.log(Level.SEVERE, "Error while loading player account");
        }

    }

    public void saveAccount() {
        try {
            if (exist()) {
                DataUtils.getInstance().executeUpdate("UPDATE " + MainClass.getPlugin().getConfig().getString("database.table") +" SET TOKENS=? WHERE UUID=?", ps -> {
                    ps.setInt(1, this.tokens);
                    ps.setString(2, this.uuid.toString());
                });
            }
        } catch (Exception e) {
            MainClass.getPlugin().log.log(Level.SEVERE, "Error while saving player account");
        }
    }

    public UUID getUUID() { return this.uuid; }

    public int getTokens() { return this.tokens; }
    public void setTokens(int amount) { this.tokens = amount; }

}