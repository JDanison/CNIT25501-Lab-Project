import javax.swing.*;
import java.awt.*;

public class SensorCreationDialog extends JDialog {
    private JTextField typeField;
    private JTextField thresholdField;
    private JTextField unitField;
    private boolean confirmed = false;

    public SensorCreationDialog() {
        setTitle("New Sensor");
        setSize(1200, 800); // Adjusted size
        setModal(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setPreferredSize(new Dimension(400, 200));

        panel.add(new JLabel("Type (e.g., Temperature):"));
        typeField = new JTextField();
        panel.add(typeField);

        panel.add(new JLabel("Threshold:"));
        thresholdField = new JTextField();
        panel.add(thresholdField);

        panel.add(new JLabel("Unit (e.g., °C):"));
        unitField = new JTextField();
        panel.add(unitField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            try {
                Double.parseDouble(thresholdField.getText());
                confirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid threshold value", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panel, gbc);
        setLocationRelativeTo(null);
    }

    public String getSensorType() {
        return typeField.getText();
    }

    public double getThreshold() {
        return Double.parseDouble(thresholdField.getText());
    }

    public String getUnit() {
        return unitField.getText();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}