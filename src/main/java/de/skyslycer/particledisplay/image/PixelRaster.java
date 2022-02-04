package de.skyslycer.particledisplay.image;

import java.awt.image.BufferedImage;

public class PixelRaster {

    private final String name;
    private final BufferedImage image;
    private final int x;
    private final int y;
    private final int z;
    private final float distance;

    public PixelRaster(String name, BufferedImage image, int x, int y, int z, float distance) {
        this.name = name;
        this.image = image;
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public float getDistance() {
        return distance;
    }

}
