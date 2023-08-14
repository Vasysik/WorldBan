package vasys.worldban;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WorldBan extends JavaPlugin implements Listener {

    HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
    List<String> worlds = new ArrayList<>();
    World lobbyWorld = getServer().getWorld(getConfig().getString("lobby"));
    Location lobby = new Location(lobbyWorld, getConfig().getDouble("lobbyCordX"), getConfig().getDouble("lobbyCordY"), getConfig().getDouble("lobbyCordZ"));
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
    public void onPlayerTeleportEvent(PlayerTeleportEvent event){
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
            if (sender instanceof Player) {
                Player send = (Player) sender;
                if (!send.hasPermission("worldban.worldban")) {
                    return false;
                }
            }
            Player player = getServer().getPlayer(args[0]);
            PermissionAttachment attachment = player.addAttachment(this);

            if (getConfig().getBoolean("worldListIsWhiteList")) {
                if (worlds.contains(args[1])) {
                    String permission = "worldban.world." + args[1];
                    if (getConfig().getString(args[1]) != null) permission = getConfig().getString(args[0]);
                    attachment.setPermission(permission, false);
                }
            }
            else {
                if (worlds.contains(args[1])) {
                    String permission = "worldban.world." + args[1];
                    if (getConfig().getString(args[1]) != null) permission = getConfig().getString(args[0]);
                    attachment.setPermission(permission, true);
                }
            }
        }
        return true;
    }
}
