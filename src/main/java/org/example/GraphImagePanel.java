package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class GraphImagePanel extends JPanel {
    private BufferedImage image;

    public GraphImagePanel(BufferedImage image) {
        this.setBounds(0,0,Constants.REQUESTS_GRAPH_WINDOW_WIDTH,Constants.REQUESTS_GRAPH_WINDOW_HEIGHT);
        this.setLayout(null);
        this.image = image;
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, Constants.REQUESTS_GRAPH_WINDOW_WIDTH-50, Constants.REQUESTS_GRAPH_WINDOW_HEIGHT-50, null);
    }

}
