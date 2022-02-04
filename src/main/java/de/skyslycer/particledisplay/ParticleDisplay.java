package de.skyslycer.particledisplay;

import de.skyslycer.particledisplay.commands.ParticleDisplayCommand;
import de.skyslycer.particledisplay.image.PixelRaster;
import java.util.HashMap;
import java.util.List;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticleDisplay extends JavaPlugin {

    private final HashMap<String, PixelRaster> rasters = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            getRasters().forEach((name, raster) -> {
                for (int x = 0; x < raster.getImage().getWidth(); x++) {
                    for (int y = 0; y < raster.getImage().getHeight(); y++) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            DustOptions color = new DustOptions(Color.fromRGB(raster.getImage().getRGB(x, y) & 0x00FFFFFF), 5);
                            try {
                                player.spawnParticle(Particle.REDSTONE, raster.getX() + (raster.getDistance() * x), raster.getY() - (raster.getDistance() * y), raster.getZ(), 0, 0, 0, 0, color);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                }
            });
        }, 20L, 5L);

        CommandManager manager = new CommandManager(this, true);
        manager.getCompletionHandler().register("#images", input -> getRasters().keySet().stream().toList());
        manager.getCompletionHandler().register("#size", input -> List.of("150"));
        manager.getCompletionHandler().register("#distance", input -> List.of("0.5"));
        manager.register(new ParticleDisplayCommand(this));
    }

    public HashMap<String, PixelRaster> getRasters() {
        return rasters;
    }

}
