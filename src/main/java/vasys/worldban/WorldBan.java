package vasys.worldban;

import org.bukkit.plugin.java.JavaPlugin;

public final class WorldBan extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Enabling WorldBan v${project.version}");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
