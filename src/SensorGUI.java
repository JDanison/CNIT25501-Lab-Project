import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SensorGUI extends JFrame implements SensorEventListener {
    private DefaultTableModel tableModel;
    private JTable sensorTable;
    private JTextArea logArea;
    private JTextArea warningLogArea;
    private JTextField searchField;
    private JComboBox<String> sensorFilterCombo;
    private List<LogEntry> allLogs = new ArrayList<>();
    private List<LogEntry> warningLogs = new ArrayList<>();

    public SensorGUI() {
        setTitle("Sensor Alarm System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main split pane
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setDividerLocation(400);
        mainSplitPane.setResizeWeight(0.6);

        // Top panel - Table and filters
        JPanel topPanel = new JPanel(new BorderLayout());

        // Filter panel with Create Sensor button
        JPanel filterPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel(" Search: "));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        sensorFilterCombo = new JComboBox<>(new String[]{"All"}); // Initialize with "All"
        searchPanel.add(sensorFilterCombo);

        JButton createSensorButton = new JButton("Create Sensor");
        createSensorButton.addActionListener(e -> createSensor());
        filterPanel.add(searchPanel, BorderLayout.CENTER);
        filterPanel.add(createSensorButton, BorderLayout.EAST);

        // Sensor table
        tableModel = new DefaultTableModel(new Object[]{"Sensor", "Value", "Status"}, 0);
        sensorTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        sensorTable.setRowSorter(sorter);
        sensorTable.setDefaultRenderer(Object.class, new StatusCellRenderer());
        JScrollPane tableScroll = new JScrollPane(sensorTable);

        topPanel.add(filterPanel, BorderLayout.NORTH);
        topPanel.add(tableScroll, BorderLayout.CENTER);

        // Bottom panel - Logs
        JPanel logPanel = new JPanel(new GridLayout(2, 1));

        // All events log
        JPanel eventLogPanel = new JPanel(new BorderLayout());
        logArea = new JTextArea();
        setupLogArea(logArea);
        eventLogPanel.add(new JLabel(" All Events:"), BorderLayout.NORTH);
        eventLogPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        // Warning log
        JPanel warningLogPanel = new JPanel(new BorderLayout());
        warningLogArea = new JTextArea();
        setupLogArea(warningLogArea);
        warningLogPanel.add(new JLabel(" Alerts Only:"), BorderLayout.NORTH);
        warningLogPanel.add(new JScrollPane(warningLogArea), BorderLayout.CENTER);

        logPanel.add(eventLogPanel);
        logPanel.add(warningLogPanel);

        mainSplitPane.setTopComponent(topPanel);
        mainSplitPane.setBottomComponent(logPanel);
        add(mainSplitPane, BorderLayout.CENTER);

        // Event listeners
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateFilter(); }
            public void removeUpdate(DocumentEvent e) { updateFilter(); }
            public void changedUpdate(DocumentEvent e) { updateFilter(); }
        });

        sensorFilterCombo.addActionListener(e -> updateFilter());
    }

    private void createSensor() {
        SensorCreationDialog sensorDialog = new SensorCreationDialog();
        sensorDialog.setVisible(true);
        if (sensorDialog.isConfirmed()) {
            String sensorType = sensorDialog.getSensorType();

            // Add sensor type to combo box if not exists
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) sensorFilterCombo.getModel();
            boolean exists = false;
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).equals(sensorType)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                model.addElement(sensorType);
            }

            // Create and add sensor
            Sensor sensor = new Sensor(
                    sensorType,
                    sensorDialog.getThreshold(),
                    sensorDialog.getUnit()
            );
            sensor.addListener(new AdvancedAlarmSystem());
            sensor.addListener(this);
            sensor.startGeneratingData();
        }
    }

    private void setupLogArea(JTextArea area) {
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    private void updateFilter() {
        String searchText = searchField.getText();
        String selectedSensor = (String) sensorFilterCombo.getSelectedItem();

        RowFilter<DefaultTableModel, Integer> rf = RowFilter.andFilter(Arrays.asList(
                RowFilter.regexFilter("(?i)" + searchText, 0),
                RowFilter.regexFilter(selectedSensor.equals("All") ? ".*" : selectedSensor, 0)
        ));
        ((TableRowSorter<DefaultTableModel>) sensorTable.getRowSorter()).setRowFilter(rf);
        updateLogDisplays();
    }

    @Override
    public void onSensorEvent(SensorEvent event) {
        double deviation = event.getValue() - event.getThreshold();
        double absDeviation = Math.abs(deviation);
        String direction = deviation > 0 ? "above" : "below";
        String status;
        String message;

        if (absDeviation > 10) {
            status = "CRITICAL";
            message = "CRITICAL ALERT! " + event.getType() + " is " + direction + " threshold by " + String.format("%.2f", absDeviation);
        } else if (absDeviation > 5) {
            status = "WARNING";
            message = "WARNING! " + event.getType() + " is moderately " + direction + " threshold. Deviation: " + String.format("%.2f", absDeviation);
        } else if (absDeviation > 2) {
            status = "MILD";
            message = "MILD ALERT: " + event.getType() + " is slightly " + direction + " threshold. Deviation: " + String.format("%.2f", absDeviation);
        } else {
            status = "NORMAL";
            message = "Normal reading: " + event.getType() + " at " + String.format("%.2f", event.getValue()) + " " + event.getUnit() + " (" + direction + " threshold)";
        }

        // Update table (value already includes unit)
        SwingUtilities.invokeLater(() -> {
            boolean found = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(event.getType())) {
                    tableModel.setValueAt(String.format("%.2f %s", event.getValue(), event.getUnit()), i, 1);
                    tableModel.setValueAt(status, i, 2);
                    found = true;
                    break;
                }
            }
            if (!found) {
                tableModel.addRow(new Object[]{
                        event.getType(),
                        String.format("%.2f %s", event.getValue(), event.getUnit()),
                        status
                });
            }
        });

        // Log entry
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        LogEntry entry = new LogEntry(timestamp, event.getType(), status, message);

        SwingUtilities.invokeLater(() -> {
            allLogs.add(entry);
            if (!status.equals("NORMAL")) {
                warningLogs.add(entry);
            }
            updateLogDisplays();
        });
    }

    private void updateLogDisplays() {
        String selectedSensor = (String) sensorFilterCombo.getSelectedItem();
        logArea.setText(filterLogs(allLogs, selectedSensor));
        warningLogArea.setText(filterLogs(warningLogs, selectedSensor));
        logArea.setCaretPosition(logArea.getDocument().getLength());
        warningLogArea.setCaretPosition(warningLogArea.getDocument().getLength());
    }

    private String filterLogs(List<LogEntry> entries, String filter) {
        StringBuilder sb = new StringBuilder();
        for (LogEntry entry : entries) {
            if (filter.equals("All") || entry.sensorType.equals(filter)) {
                sb.append("[").append(entry.timestamp).append("] ")
                        .append(entry.message).append("\n");
            }
        }
        return sb.toString();
    }

    private static class LogEntry {
        String timestamp;
        String sensorType;
        String status;
        String message;

        public LogEntry(String timestamp, String sensorType, String status, String message) {
            this.timestamp = timestamp;
            this.sensorType = sensorType;
            this.status = status;
            this.message = message;
        }
    }

    private static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) table.getModel().getValueAt(row, 2);
            switch (status) {
                case "CRITICAL": c.setBackground(Color.RED); break;
                case "WARNING": c.setBackground(Color.ORANGE); break;
                case "MILD": c.setBackground(Color.YELLOW); break;
                default: c.setBackground(Color.GREEN);
            }
            return c;
        }
    }
}