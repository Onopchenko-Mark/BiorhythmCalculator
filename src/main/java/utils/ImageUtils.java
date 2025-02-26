package utils;

import java.awt.Image;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.View;

public class ImageUtils {
    public static JLabel createImage(String name, String uri, int width, int height) {
	JLabel image = new JLabel();
	try {
	    URI imageURI = new URI(uri);
	    ImageIcon imageIcon = new ImageIcon(imageURI.toURL());
	    image.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
	} catch (Exception e) {
	    View.showErrorMessage("Error! Image " + name + "could not be loaded!");
	}
	return image;
    }
}
