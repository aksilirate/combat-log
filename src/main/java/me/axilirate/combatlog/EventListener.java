package me.axilirate.combatlog;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import java.util.ArrayList;

public class EventListener implements Listener {

    public ArrayList<String> loggedPlayers = new ArrayList<>();

    private final CombatLog combatLog;

    public EventListener(CombatLog combatLog) {
        this.combatLog = combatLog;
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (combatLog.getDeathGhost() != null){
            if (combatLog.getDeathGhost().isPlayerDead(player.getUniqueId().toString())) {
                return;
            }
        }



        if (loggedPlayers.contains(player.getName())) {
            event.getPlayer().setHealth(0);
        }


    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (event.getDamage() <= 0.0){
            return;
        }

        if (event.isCancelled()){
            return;
        }

        final Player player = (Player) event.getEntity();
        final Player damager = (Player) event.getDamager();


        if (loggedPlayers.contains(player.getName())) {
            return;
        }

        if (loggedPlayers.contains(damager.getName())) {
            return;
        }


        loggedPlayers.add(player.getName());
        loggedPlayers.add(damager.getName());


        player.sendMessage(ChatColor.RED + "You're now in Combat! Leaving the server in the next " + combatLog.combatLogTime + " seconds will kill you");
        damager.sendMessage(ChatColor.RED + "You're now in Combat! Leaving the server in the next " + combatLog.combatLogTime + " seconds will kill you");

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(combatLog, () -> {


            if (loggedPlayers.contains(player.getName())) {
                loggedPlayers.remove(player.getName());
                player.sendMessage(ChatColor.GREEN + "You're out of Combat and safe to leave");

            }

            if (loggedPlayers.contains(damager.getName())) {
                loggedPlayers.remove(damager.getName());
                damager.sendMessage(ChatColor.GREEN + "You're out of Combat and safe to leave");
            }

        }

                , 20 * combatLog.combatLogTime);


    }


}
