package code.panel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import code.Setting;
import code.dialog.DialogCalculateScoreLastTerm3;
import code.objects.Button;
import code.objects.ConversionTable;
import code.text_field.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Font;

public class PanelCalculateLastTerm3 extends JPanel {
        // Properties
        private JFrame applicationFrame;
        private ConversionTable conversionTable;
        private JPanel mainPanel;
        private PanelString panelTitle;
        private PanelString panelScore1, panelScore2;
        private PanelString panelCoefficient1, panelCoefficient2;
        private TextField textField1, textField2, textField3, textField4;
        private Button buttonSubmit;

        // Constructor
        public PanelCalculateLastTerm3(int x, int y, int width, int height, Font font, ConversionTable convertionTable,
                        JFrame applicationFrame) {
                this.applicationFrame = applicationFrame;
                this.conversionTable = convertionTable;
                // Create defaulr font
                if (font == null) {
                        font = new Font(Setting.FONT_NAME_01,
                                        Setting.FONT_STYLE_01,
                                        Setting.FONT_SIZE_SMALL);
                }

                // Create panels
                panelTitle = new PanelString(0, 0, "Tính toán đối với môn 3 thành phần điểm", width,
                                new Font(Setting.FONT_NAME_01,
                                                Setting.FONT_STYLE_01,
                                                Setting.FONT_SIZE_MEDIUM),
                                PanelString.TOP_LEFT,
                                15);
                panelScore1 = new PanelString(0, panelTitle.getHeight() + 20, "Điểm thứ nhất: ", width / 8, font,
                                PanelString.TOP_LEFT, 15);
                panelScore2 = new PanelString(0, panelScore1.getY() + panelScore1.getHeight() + 10, "Điểm thứ hai: ",
                                width / 8,
                                font,
                                PanelString.TOP_LEFT, 15);
                textField1 = new TextField(panelScore1.getWidth(), panelScore1.getY(), width / 10,
                                panelScore1.getHeight(),
                                TextField.TOP_LEFT, "9.04", 2, 15, 15);
                textField2 = new TextField(panelScore2.getWidth(), panelScore2.getY(), width / 10,
                                panelScore2.getHeight(),
                                TextField.TOP_LEFT, "9.04", 2, 15, 15);
                panelCoefficient1 = new PanelString(textField1.getX() + textField1.getWidth() + width / 15,
                                panelScore1.getY(),
                                "Hệ số của điểm thứ nhất (%): ", width / 5 + 30, font, PanelString.TOP_LEFT, 15);
                panelCoefficient2 = new PanelString(textField2.getX() + textField2.getWidth() + width / 15,
                                panelScore2.getY(),
                                "Hệ số của điểm thứ hai (%): ", width / 5 + 30, font, PanelString.TOP_LEFT, 15);
                textField3 = new TextField(panelCoefficient1.getX() + panelCoefficient1.getWidth(),
                                panelCoefficient1.getY(),
                                width / 10, panelCoefficient1.getHeight(),
                                TextField.TOP_LEFT, "20", 2, 15, 15);
                textField4 = new TextField(panelCoefficient2.getX() + panelCoefficient2.getWidth(),
                                panelCoefficient2.getY(),
                                width / 10, panelCoefficient2.getHeight(),
                                TextField.TOP_LEFT, "20", 2, 15, 15);

                buttonSubmit = new Button("Tính toán");
                buttonSubmit.setSizeButton(width / 10,
                                panelScore2.getY() + panelScore2.getHeight() - panelScore1.getY(), true);
                buttonSubmit.setLocation(textField3.getX() + textField3.getWidth() + width / 5, panelScore1.getY(),
                                Button.TOP_LEFT);
                buttonSubmit.setFont(font);

                // mainPanel
                mainPanel = new JPanel();
                mainPanel.setLayout(null);
                mainPanel.setSize(width, height);
                mainPanel.setBounds(0, 0, mainPanel.getWidth(), mainPanel.getHeight());

                // Relative between panels
                mainPanel.add(panelTitle);
                mainPanel.add(panelScore1);
                mainPanel.add(panelScore2);
                mainPanel.add(textField1);
                mainPanel.add(textField2);
                mainPanel.add(panelCoefficient1);
                mainPanel.add(panelCoefficient2);
                mainPanel.add(textField3);
                mainPanel.add(textField4);
                mainPanel.add(buttonSubmit);
                buttonSubmit.addActionListener(new ButtonHandler());

                // Properties of this panel
                setLayout(null);
                setSize(width, height);
                setBounds(x, y, getWidth(), getHeight());

                // Add mainPanel to this panel
                add(mainPanel);
        }

        // Set background Color
        public void setBackgroundColorPanelSubject(Color color) {
                mainPanel.setBackground(color);

        }

        // Auto called method of JPanel
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
        }

        // Check valid input
        public boolean isValidInput() {
                if (!textField1.getText().matches("[0-9]{1,}")
                                && !textField1.getText().matches("[0-9]{1,}\\.[0-9]{1,}")) {
                        JOptionPane.showMessageDialog(applicationFrame, "Điểm thứ nhất không hợp lệ",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (Double.parseDouble(textField1.getText()) > 10) {
                        JOptionPane.showMessageDialog(applicationFrame, "Điểm thứ nhất đang lớn hơn 10, hư cấu",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (!textField2.getText().matches("[0-9]{1,}")
                                && !textField2.getText().matches("[0-9]{1,}\\.[0-9]{1,}")) {
                        JOptionPane.showMessageDialog(applicationFrame, "Điểm thứ hai không hợp lệ",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (Double.parseDouble(textField2.getText()) > 10) {
                        JOptionPane.showMessageDialog(applicationFrame, "Điểm thứ hai đang lớn hơn 10, hư cấu",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (!textField3.getText().matches("[0-9]{1,}")
                                && !textField3.getText().matches("[0-9]{1,}\\.[0-9]{1,}")) {
                        JOptionPane.showMessageDialog(applicationFrame, "Hệ số của điểm thứ nhất không hợp lệ",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (Double.parseDouble(textField3.getText()) > 100) {
                        JOptionPane.showMessageDialog(applicationFrame,
                                        "Hệ số của điểm thứ nhất đang lớn hơn 100%, hư cấu",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (!textField4.getText().matches("[0-9]{1,}")
                                && !textField4.getText().matches("[0-9]{1,}\\.[0-9]{1,}")) {
                        JOptionPane.showMessageDialog(applicationFrame, "Hệ số của điểm thứ nhaihất không hợp lệ",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (Double.parseDouble(textField4.getText()) > 100) {
                        JOptionPane.showMessageDialog(applicationFrame,
                                        "Hệ số của điểm thứ hai đang lớn hơn 100%, hư cấu",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                } else if (Double.parseDouble(textField3.getText()) + Double.parseDouble(textField4.getText()) >= 100) {
                        JOptionPane.showMessageDialog(applicationFrame,
                                        "Tổng hệ số của điểm thứ nhất và thứ hai đang không hợp lý",
                                        "Invalid input",
                                        JOptionPane.WARNING_MESSAGE);
                        return false;
                }
                return true;
        }

        private class ButtonHandler implements ActionListener {
                public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == buttonSubmit) {
                                if (isValidInput()) {
                                        new DialogCalculateScoreLastTerm3(Setting.WIDTH / 2,
                                                        Setting.HEIGHT / 2, Setting.WIDTH / 5 * 4,
                                                        Setting.HEIGHT / 5 * 4,
                                                        DialogCalculateScoreLastTerm3.CENTER_CENTER, "Result", null,
                                                        Double.parseDouble(textField1.getText()),
                                                        Double.parseDouble(textField3.getText()) / 100.0,
                                                        Double.parseDouble(textField2.getText()),
                                                        Double.parseDouble(textField4.getText()) / 100.0,
                                                        conversionTable);
                                }
                        }
                }
        }
}