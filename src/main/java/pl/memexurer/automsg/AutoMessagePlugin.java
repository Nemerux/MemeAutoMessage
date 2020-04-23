package pl.memexurer.automsg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AutoMessagePlugin extends JavaPlugin {
    private Bossy bossyApi;
    private int timeElapsed;
    private int currentIndex;
    private int delay;
    private List<String> messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.messages = getConfig().getStringList("messages");
        this.delay = getConfig().getInt("delay");

        this.bossyApi = new Bossy(this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (timeElapsed >= delay) {
                if (currentIndex >= messages.size()) {
                    currentIndex = 0;
                }

                timeElapsed = 0;
                currentIndex++;
            }
            timeElapsed++;

            for (Player p : Bukkit.getOnlinePlayers()) {
                bossyApi.set(p, ChatColor.translateAlternateColorCodes('&', messages.get(currentIndex)), (int) (300 - ((float) timeElapsed / delay * 300)));
            }
        }, 0L, 20L);
    }
}
