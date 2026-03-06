import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    
    public InfoPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Верхній рядок: Фотографію беремо з нашої фабрики
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(UIFactory.createPhotoPanel(), gbc);

        // Нижній рядок: Інформація
        JTextArea infoText = new JTextArea(
            "Розробник: Вєрьовкін Павло\n" +
            "варіант 4, BeanFeast (Посівна компанія)\n" +
            "номер телефону +380994521325\n" +
            "Email: 24pavel.verevkin@gmail.com"
        );
        infoText.setEditable(false);
        infoText.setFont(new Font("SansSerif", Font.BOLD, 14));
        infoText.setBackground(getBackground());

        gbc.gridy = 1;
        gbc.weighty = 0.0; // Текст не розтягуємо по вертикалі
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(infoText, gbc);
    }
}