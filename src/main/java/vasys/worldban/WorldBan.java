package vasys.worldban;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldBan extends JavaPlugin implements Listener {

    List<String> worlds = new ArrayList<>();
    //World lobbyWorld = getServer().getWorld(getConfig().getString("lobby"));
    //Location lobby = new Location(lobbyWorld, getConfig().getDouble("lobbyCordX"), getConfig().getDouble("lobbyCordY"), getConfig().getDouble("lobbyCordZ"));
    Location lobby = Bukkit.getWorld(getConfig().getString("lobby")).getSpawnLocation();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        worlds = getConfig().getStringList("worldList");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String world = player.getWorld().getName();
        if (worlds.contains(world)) {
            String permission = "worldban.world." + world;
            if (getConfig().getString(world) != null) permission = getConfig().getString(world);
            if (getConfig().getBoolean("worldListIsWhiteList")) {
                if (!player.hasPermission(permission)){
                    player.teleport(lobby);
                    player.sendRawMessage(getConfig().getString("noWorldPermission"));
                }
            }
            else {
                if (player.hasPermission(permission)){
                    player.teleport(lobby);
                    player.sendRawMessage(getConfig().getString("noWorldPermission"));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        String world = player.getWorld().getName();
        if (worlds.contains(world)) {
            String permission = "worldban.world." + world;
            if (getConfig().getString(world) != null) permission = getConfig().getString(world);
            if (getConfig().getBoolean("worldListIsWhiteList")) {
                if (!player.hasPermission(permission)){
                    player.teleport(lobby);
                    player.sendRawMessage(getConfig().getString("noWorldPermission"));
                }
            }
            else {
                if (player.hasPermission(permission)){
                    player.teleport(lobby);
                    player.sendRawMessage(getConfig().getString("noWorldPermission"));
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (command.getName().equalsIgnoreCase("worldban")) {
            Player send = null;
            UUID uuid = getServer().getOfflinePlayer(args[0]).getUniqueId();
            String uuidStr = uuid.toString();
            if (getServer().getPlayer(sender.getName()) != null) send = getServer().getPlayer(sender.getName());
            if (sender instanceof Player) {
                if (!send.hasPermission("worldban.worldban") & send != null) {
                    send.sendRawMessage(getConfig().getString("noPermission"));
                    return true;
                }
            }
            if (args.length != 2) {
                return false;
            }
            if (worlds.contains(args[1])) {
                String permission = "worldban.world." + args[1];
                if (getConfig().getString(args[1]) != null) permission = getConfig().getString(args[1]);
                String comand = "";
                if (getConfig().getBoolean("worldListIsWhiteList")) {
                    comand = "lp user " + uuidStr + " permission set " + permission + " false";
                }
                else {
                    comand = "lp user " + uuidStr + " permission set " + permission + " true";
                }
                if (send != null) send.sendRawMessage(args[0] + getConfig().getString("banPlayerMessage") + args[1]);
                Player player = getServer().getPlayer(uuid);
                if (player != null) {
                    player.sendRawMessage(getConfig().getString("banMessage") + args[1]);
                    player.teleport(lobby);
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comand);
            }
        }
        if (command.getName().equalsIgnoreCase("worldpardon")) {
            Player send = null;
            UUID uuid = getServer().getOfflinePlayer(args[0]).getUniqueId();
            String uuidStr = uuid.toString();
            if (getServer().getPlayer(sender.getName()) != null) send = getServer().getPlayer(sender.getName());
            if (sender instanceof Player) {
                if (!send.hasPermission("worldban.worldpardon") & send != null) {
                    send.sendRawMessage(getConfig().getString("noPermission"));
                    return true;
                }
            }
            if (args.length != 2) {
                return false;
            }
            if (worlds.contains(args[1])) {
                String permission = "worldban.world." + args[1];
                if (getConfig().getString(args[1]) != null) permission = getConfig().getString(args[1]);
                String comand = "";
                if (getConfig().getBoolean("worldListIsWhiteList")) {
                    comand = "lp user " + uuidStr + " permission set " + permission + " true";
                }
                else {
                    comand = "lp user " + uuidStr + " permission set " + permission + " false";
                }
                if (send != null) send.sendRawMessage(args[0] + getConfig().getString("pardonPlayerMessage") + args[1]);
                Player player = getServer().getPlayer(uuid);
                if (player != null) {
                    player.sendRawMessage(getConfig().getString("pardonMessage") + args[1]);
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comand);
            }
        }
        return true;
    }
}
