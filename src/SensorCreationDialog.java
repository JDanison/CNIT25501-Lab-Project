import javax.swing.*;
import java.awt.*;

public class SensorCreationDialog extends JDialog {
    private JTextField typeField;
    private JTextField thresholdField;
    private JTextField unitField;
    private boolean confirmed = false;

    public SensorCreationDialog() {
        setTitle("New Sensor");
        setSize(300, 200);
        setModal(true);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Type (e.g., Temperature):"));
        typeField = new JTextField();
        add(typeField);

        add(new JLabel("Threshold:"));
        thresholdField = new JTextField();
        add(thresholdField);

        add(new JLabel("Unit (e.g., °C):"));
        unitField = new JTextField();
        add(unitField);

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
        add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));
        add(cancelButton);
    }

    // Renamed method to avoid conflict
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