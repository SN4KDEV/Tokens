package fr.sn4kdev.tokens.commands;

import fr.sn4kdev.tokens.MainClass;
import fr.sn4kdev.tokens.api.TokenAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TokensCommand implements CommandExecutor {

    class MessageHelper {
        public Object getValue(String path) { return MainClass.getPlugin().getConfig().get(path); }
        public String convert(String baseString) { return baseString.replace("&", "§"); }
    }

    protected void sendMessage(CommandSender invoker, String path) {
        ((List<String>)new MessageHelper().getValue(path)).stream().forEach(v -> {
            invoker.sendMessage(new MessageHelper().convert(v));
        });
    }

    protected void sendMessage(CommandSender invoker, Player receiver, String path) {
        ((List<String>)new MessageHelper().getValue(path)).stream().forEach(v -> {
            invoker.sendMessage(new MessageHelper().convert(v)
                    .replace("[=]", receiver.getName())
                    .replace("(=)", String.valueOf(MainClass.getDataMap().get(receiver).getTokens())));
        });
    }

    @Override
    public boolean onCommand(CommandSender invoker, Command command, String label, String[] args) {

        if (args.length <= 0) {
            if (invoker instanceof ConsoleCommandSender) {
                sendMessage(invoker, "command.tokens.console");
                return true;
            }
            try {
                sendMessage(invoker, ((Player) invoker), "command.tokens.get-tokens-other");
            } catch (Exception e) {
                sendMessage(invoker, "command.tokens.error");
            }
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            sendMessage(invoker, "command.tokens.help");
            return true;
        }
        else
        if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            if (!invoker.hasPermission("tokens.get")) {
                sendMessage(invoker, "command.tokens.permission");
                return true;
            }
            try {
                Player receiver = Bukkit.getPlayer(args[1]);
                sendMessage(invoker, receiver, "command.tokens.get-tokens-other");
            } catch (Exception e) {
                sendMessage(invoker, "command.tokens.error");
            }
            return true;
        }
        else
        if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            if (!invoker.hasPermission("tokens.add")) {
                sendMessage(invoker, "command.tokens.permission");
                return true;
            }
            try {
                Player receiver = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                TokenAPI.addTokens(receiver, amount);
                sendMessage(invoker, receiver, "command.tokens.add-tokens.invoker");
                sendMessage(receiver, receiver, "command.tokens.add-tokens.receiver");
            } catch (Exception e) {
                sendMessage(invoker, "command.tokens.error");
            }
            return true;
        }
        else
        if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            if (!invoker.hasPermission("tokens.remove")) {
                sendMessage(invoker, "command.tokens.permission");
                return true;
            }
            try {
                Player receiver = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                TokenAPI.removeTokens(receiver, amount);
                sendMessage(invoker, receiver, "command.tokens.remove-tokens.invoker");
                sendMessage(receiver, receiver, "command.tokens.remove-tokens.receiver");
            } catch (Exception e) {
                sendMessage(invoker, "command.tokens.error");
            }
            return true;
        }
        else
        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            if (!invoker.hasPermission("tokens.set")) {
                sendMessage(invoker, "command.tokens.permission");
                return true;
            }
            try {
                Player receiver = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                TokenAPI.setTokens(receiver, amount);
                sendMessage(invoker, receiver, "command.tokens.set-tokens.invoker");
                sendMessage(receiver, receiver, "command.tokens.set-tokens.receiver");
            } catch (Exception e) {
                sendMessage(invoker, "command.tokens.error");
            }
            return true;
        }
        else sendMessage(invoker, "command.tokens.invalid-syntax");

        return false;
    }

}