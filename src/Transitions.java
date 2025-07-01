import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;

public class Transitions extends JPanel {
    private float progress = 0.0f; // Progress value for the transition
    private Timer fadeTimer;
    private Timer textTimer;
    private String message = "";
    private String displayedMessage = ""; // Message to be displayed with the punched-in effect
    private Font customFont;
    private int charIndex = 0; // Index of the next character to display

    public Transitions() {
        setBackground(Color.WHITE);

        // Load the custom font
        try {
            InputStream fontStream = getClass().getResourceAsStream("/fonts/MGS1.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(72f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.PLAIN, 20); // Fallback font
        }
    }

    public void endScreen(String message, JFrame window, PongGame game)  {
        window.setVisible(false);
        window.remove(game);
        window.add(game.getWinLoseScreen());
        window.setVisible(true);

        progress = 0.0f; // Reset progress

        // Timer for fade transition
        fadeTimer = new Timer(20, new ActionListener() { // Adjusted timer interval for smoother transition
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 0.01f; // Adjusted progress increment for 2-second fade transition
                if (progress >= 1.0f) {
                    progress = 1.0f;
                    ((Timer) e.getSource()).stop(); // Stop the timer when transition is complete
                }
                repaint();
            }
        });
        fadeTimer.start();

        // Timer for text reveal effect
        textTimer = new Timer(250, new ActionListener() { // Adjust this interval to control text speed
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < message.length()) {
                    displayedMessage += message.charAt(charIndex);
                    charIndex++;
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when all characters are displayed
                }
                repaint();
            }
        });
        textTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Interpolate between white and black based on progress
        int red = (int) (255 * (1 - progress));
        int green = (int) (255 * (1 - progress));
        int blue = (int) (255 * (1 - progress));
        Color transitionColor = new Color(red, green, blue);

        g2d.setColor(transitionColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the message
        if (!displayedMessage.isEmpty()) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(customFont); // Set the custom font
            FontMetrics fm = g2d.getFontMetrics();
            double totalWidth = fm.stringWidth(displayedMessage);
            double x = (getWidth() - totalWidth) / 2;
            double y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

            // Draw each character individually with extra spacing
            double extraSpacing = 7; // Adjust this value as needed
            for (char c : displayedMessage.toCharArray()) {
                String charStr = String.valueOf(c);
                int charWidth = fm.stringWidth(charStr);

                // Draw wireframe text
                FontRenderContext frc = g2d.getFontRenderContext();
                TextLayout textLayout = new TextLayout(charStr, customFont, frc);
                Shape outline = textLayout.getOutline(AffineTransform.getTranslateInstance(x, y));
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.draw(outline);

                x += charWidth + extraSpacing;
            }
        }
    }


}