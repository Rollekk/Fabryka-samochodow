package z12;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Support {
    public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight)
    {
        System.out.println("resizing...");
        var imageType = BufferedImage.TYPE_INT_RGB;
        var scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        var g = scaledBI.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }
}
