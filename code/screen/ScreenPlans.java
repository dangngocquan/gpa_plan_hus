package code.screen;

import javax.swing.JPanel;
import code.Application;
import code.objects.Button;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ScreenPlans extends JPanel {
    // Properties, Objects and Screens
    private Application applicationFrame;
    private String[] buttonTexts = {
            "Các kế hoạch hiện có", "Tạo kế hoạch mới", "Quay lại"
    };
    private Button[] buttons;
    private ScreenMainMenu parentScreen;
    private JPanel mainScreen;
    private ScreenCreateNewPlan1 screenCreateNewPlan1;
    private ScreenExistingPlans screenExistingPlans;

    // Constructor
    public ScreenPlans(int width, int height, ScreenMainMenu parentScreen, Application frame) {
        // Set basic properties
        this.applicationFrame = frame;
        setLayout(null);
        setBounds(0, 0, width, height);
        setSize(width, height);
        this.parentScreen = parentScreen;

        // Create screens
        mainScreen = new JPanel();
        mainScreen.setLayout(null);
        mainScreen.setSize(width, height);
        mainScreen.setBounds(0, 0, width, height);
        screenCreateNewPlan1 = new ScreenCreateNewPlan1(width, height, this, applicationFrame);
        screenExistingPlans = new ScreenExistingPlans(width, height, this, applicationFrame);

        // Create buttons
        buttons = new Button[buttonTexts.length];
        for (int count = 0; count < buttonTexts.length; count++) {
            buttons[count] = new Button(buttonTexts[count]);
            buttons[count].setFontText(Button.ARIAL_BOLD_28);
            buttons[count].setCorrectSizeButton();
            buttons[count].addMouseListener(new MouseHandler());
        }

        // Set location for each button
        buttons[0].setLocationButton(width / 2, height / 12 * 3, Button.TOP_CENTER);
        buttons[1].setLocationButton(width / 2, height / 12 * 5, Button.TOP_CENTER);
        buttons[2].setLocationButton(width / 2, height / 12 * 7, Button.TOP_CENTER);

        // Add buttons to mainScreen
        for (Button button : buttons) {
            mainScreen.add(button);
        }

        // Add screens to this panel
        add(mainScreen);
        add(screenCreateNewPlan1);
        add(screenExistingPlans);

        // Set visible screens
        mainScreen.setVisible(true);
        screenCreateNewPlan1.setVisible(false);
        screenExistingPlans.setVisible(false);
    }

    // Get application frame
    public Application getApplicationFrame() {
        return this.applicationFrame;
    }

    // Get buttons
    public Button[] getButtons() {
        return this.buttons;
    }

    // Get parent screen
    public ScreenMainMenu getParentScreen() {
        return this.parentScreen;
    }

    // Get mainScreen
    public JPanel getMainScreen() {
        return this.mainScreen;
    }

    // Get ScreenCreateNewPlan1
    public ScreenCreateNewPlan1 getScreenCreateNewPlan1() {
        return this.screenCreateNewPlan1;
    }

    // Get ScreenExistingPlans
    public ScreenExistingPlans getScreenExistingPlans() {
        return this.screenExistingPlans;
    }

    // Auto called method of JPanel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private class MouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            // Press at "Existing plans" button
            if (event.getSource() == buttons[0]) {
                getScreenExistingPlans().setCurscorScroll(0);
                getScreenExistingPlans().updateButton();
                getScreenExistingPlans().setVisible(true);
                getMainScreen().setVisible(false);
            }
            // Press at "Create new plan" button
            else if (event.getSource() == buttons[1]) {
                getScreenCreateNewPlan1().setVisible(true);
                getMainScreen().setVisible(false);
            }
            // Press at "Back" button
            else if (event.getSource() == buttons[2]) {
                getParentScreen().getMainScreen().setVisible(true);
                getParentScreen().getScreenPlans().setVisible(false);
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }
    }
}
