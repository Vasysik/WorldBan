package vasys.worldban;

import org.bukkit.plugin.java.JavaPlugin;

public final class WorldBan extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Enabling WorldBan " + getPluginMeta().getVersion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
