package ru.mrbrikster.baseplugin.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class ClickIconAction {

    @Getter private Menu menu;
    @Getter private Player player;
    @Getter private Icon icon;

    public void close() {
        menu.setAllowClose(true);
        menu.getBukkitBasePlugin().getScheduler().schedule(player::closeInventory, 50, TimeUnit.MILLISECONDS);
    }

    public void reply(String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sound(Sound sound) {
        player.playSound(player.getLocation(), sound, 1L, 1L);
    }

    public interface ClickHandler {

        void onClick(ClickIconAction clickIconAction);

    }

}

