package me.axilirate.combatlog;

import me.axilirate.DeathGhost;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLog extends JavaPlugin {

    public DeathGhost deathGhost;



    @Override
    public void onEnable() {

        deathGhost = (DeathGhost) Bukkit.getPluginManager().getPlugin("DeathGhost");

        getServer().getPluginManager().registerEvents(new EventListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
