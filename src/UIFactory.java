import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UIFactory {

    // Метод для відображення тексту ТЗ (Лістинг 3.1)
    public static JTextPane createTzPane() {
        JTextPane jTextPane = new JTextPane();
        jTextPane.setEditable(false);
        String str = "/tz.htm"; // Шлях до файлу ТЗ у корені src
        URL url = UIFactory.class.getResource(str);
        if (url != null) {
            try {
                jTextPane.setPage(url);
            } catch (IOException e) {
                System.err.println("Помилка читання файлу " + str);
            }
        } else {
            jTextPane.setText("Файл tz.htm не знайдено у корені пакету.");
        }
        return jTextPane;
    }

    // Метод для відображення фото (Лістинг 3.2)
    public static JPanel createPhotoPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                URL url = getClass().getResource("/resource/photo.jpg");
                
                if (url == null) {
                    g2d.drawString("Фото /resource/photo.jpg не знайдено", 20, 20);
                    return;
                }
                
                try {
                    BufferedImage img = ImageIO.read(url);
                    double k = (double) img.getHeight() / img.getWidth();
                    int width = getWidth();
                    int height = getHeight();
                    
                    if ((double) height / width > k) {
                        height = (int) (width * k);
                    } else {
                        width = (int) (height / k);
                    }
                    
                    Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    // Центрування зображення
                    int x = (getWidth() - width) / 2;
                    int y = (getHeight() - height) / 2;
                    g2d.drawImage(scaledImg, x, y, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}