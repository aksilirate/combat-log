package me.axilirate.combatlog;

import me.axilirate.DeathGhost;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLog extends JavaPlugin {

    public FileConfiguration configuration = this.getConfig();;

    public int combatLogTime;

    @Override
    public void onEnable() {



        configuration.addDefault("combat-log-time", 30);
        configuration.options().copyDefaults(true);
        saveConfig();

        combatLogTime = configuration.getInt("combat-log-time");

        getServer().getPluginManager().registerEvents(new EventListener(this), this);

    }





    public DeathGhost getDeathGhost(){
        return (DeathGhost) Bukkit.getPluginManager().getPlugin("DeathGhost");
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
