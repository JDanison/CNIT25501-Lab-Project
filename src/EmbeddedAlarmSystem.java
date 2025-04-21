public class EmbeddedAlarmSystem {
    public static void main(String[] args) {
        // Show login dialog
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setVisible(true);
        if (!loginDialog.isLoggedIn()) {
            System.exit(0);
        }

        // Show homepage (SensorGUI)
        SensorGUI gui = new SensorGUI();
        gui.setVisible(true);
    }
}