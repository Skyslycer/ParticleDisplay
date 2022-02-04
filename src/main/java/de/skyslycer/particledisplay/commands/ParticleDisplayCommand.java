package de.skyslycer.particledisplay.commands;

import de.skyslycer.particledisplay.ParticleDisplay;
import de.skyslycer.particledisplay.image.PixelRaster;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

@Command("particledisplay")
@Alias("pd")
public class ParticleDisplayCommand extends CommandBase {

    private final ParticleDisplay plugin;

    public ParticleDisplayCommand(ParticleDisplay plugin) {
        this.plugin = plugin;
    }

    @SubCommand("add")
    @Completion({"#empty", "#size", "#distance", "#empty"})
    public void addCommand(Player player, String name, Integer size, Float distance, String url) {
        if (plugin.getRasters().containsKey(name)) {
            player.sendMessage("That name already exists!");
            return;
        }

        Location location = player.getLocation();
        URL parsedUrl;
        BufferedImage image;

        try {
            parsedUrl = new URL(url);
            image = ImageIO.read(parsedUrl);
        } catch (MalformedURLException exception) {
            player.sendMessage("The given URL isn't valid!");
            return;
        } catch (IOException exception) {
            player.sendMessage("The given URL doesn't point to an image!");
            return;
        }

        plugin.getRasters().put(name, new PixelRaster(
                name,
                Scalr.resize(image, Method.QUALITY, size, size),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockY(),
                distance
        ));
        player.sendMessage("Successfully created ParticleDisplay with the name " + name);
    }

    @SubCommand("remove")
    @Completion("#images")
    public void removeCommand(CommandSender sender, String name) {
        if (plugin.getRasters().remove(name) != null) {
            sender.sendMessage("You successfully removed the image " + name);
        } else {
            sender.sendMessage("There is no such image!");
        }
    }

}