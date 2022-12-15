package code.panel;

import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import code.Setting;
import code.dialog.DialogUpdateMapRelative;
import code.file_handler.WriteFile;
import code.objects.Plan;
import code.objects.Subject;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class PanelMapRelativeSubjects extends JPanel {
    // Constants panel's root location
    public static final int TOP_LEFT = 0;
    public static final int TOP_CENTER = 1;
    public static final int TOP_RIGHT = 2;
    public static final int CENTER_LEFT = 3;
    public static final int CENTER_CENTER = 4;
    public static final int CENTER_RIGHT = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM_CENTER = 7;
    public static final int BOTTOM_RIGHT = 8;

    public static final Color COLOR_SUBJECT_ENTERED = Setting.COLOR_VIOLET_03;
    public static final Color COLOR_SUBJECT_EXITED_1 = Setting.COLOR_GRAY_05;
    public static final Color COLOR_LINE_ENTERED = Setting.COLOR_BLUE_02;
    public static final Color COLOR_LINE_EXITED = Setting.COLOR_BLACK;
    public static final Color COLOR_STROKE_PANEL_SUBJECT_EXITED = Setting.COLOR_BLACK;
    public static final Color COLOR_STROKE_PANEL_SUBJECT_ENTERED = COLOR_LINE_ENTERED;

    // Properties
    private int width, height; // size of this panel
    private int xPos, yPos, rootLocationType; // location of top-left point
    private Plan plan; // data plan
    private PanelSubject4[] panelSubjects = null;
    private int indexPlan;
    private ArrayList<LinkedList<Line2D>> lines= null;
    private ArrayList<LinkedList<Integer>> indexes = null; // Save index of parent-subjects of each subject
    private int indexSubjectEntering = -1;

    // Constructor
    public PanelMapRelativeSubjects(int x, int y, int width, int height, Plan plan, int indexPlan, int rootLocationType) {
        // Properties, Objects
        this.width = width;
        this.height = height;
        this.plan = plan;
        this.indexPlan = indexPlan;
        this.rootLocationType = rootLocationType;
        setLayout(null);
        setSize(width, height);
        setLocation(x, y, rootLocationType);

        // Draw panel
        int maxRow = plan.getMaxLevel() + 1;
        int maxColumn = plan.getMaxNumberSubjectInSameLevel() + 1;
        int heightPerSubjectPanel = height / maxRow;
        int widthPerSubjectPanel = width / maxColumn;

        // Draw panel subjects
        panelSubjects = new PanelSubject4[plan.getSubjects().size()];
        int count = 0;
        int[] tempLocation = new int[maxRow];
        for (int level = 0; level < maxRow; level++) {
            tempLocation[level] = (maxColumn - plan.getNumberSubjectLevelX(level))/2;
        }
        for (Subject subject : plan.getSubjects()) {
            int level = subject.getLevel();
            panelSubjects[count] = new PanelSubject4(tempLocation[level] * widthPerSubjectPanel + 15 + (level % 2)*widthPerSubjectPanel/2, 
                                                    level * heightPerSubjectPanel + 15, 
                                                    subject, widthPerSubjectPanel/10*8, heightPerSubjectPanel/3-18, null,
                                                    PanelSubject4.TOP_LEFT);
            panelSubjects[count].setToolTipText(String.format("%s - %s", subject.getCode(), subject.getName()));
            panelSubjects[count].setBackgroundColorPanelSubject(COLOR_STROKE_PANEL_SUBJECT_EXITED, subject.getColor());
            panelSubjects[count].addMouseListener(new MouseHandler());
            add(panelSubjects[count]);                                        
            count++;
            tempLocation[level]++;   
        }

        // Create and draw lines
        lines = new ArrayList<LinkedList<Line2D>>(plan.getSubjects().size());
        indexes = new ArrayList<LinkedList<Integer>>(plan.getSubjects().size());
        for (int i = 0; i < plan.getSubjects().size(); i++) {
            lines.add(new LinkedList<Line2D>());
            indexes.add(new LinkedList<Integer>());
        }
        for (int i = 0; i < plan.getSubjects().size(); i++) {
            Subject subject = plan.getSubjects().get(i);
            for (Subject parentSubject : subject.getParentSubjectsByList()) {
                int j = plan.getIndexOfSubject(parentSubject);
                Line2D line = new Line2D.Float(panelSubjects[j].getCenterX(), panelSubjects[j].getBottomY(),
                                                panelSubjects[i].getCenterX(), panelSubjects[i].getY());
                lines.get(i).add(line);
                lines.get(j).add(line);
                indexes.get(i).add(j);
                indexes.get(j).add(i);                         
            }
        }
    }


    // Update content data
    public void updateContent() {
       
    }

    // Get rootLocationType
    public int getRootLocationType() {
        return this.rootLocationType;
    }

    // Set index subject entering
    public void setIndexSubjectPanelEntering(int index) {
        this.indexSubjectEntering = index;
    }

    // Get plan
    public Plan getplan() {
        return this.plan;
    }

    // Set root location tyoe
    public void setLocation(int x, int y, int type) {
        this.rootLocationType = type;
        switch (type) {
            case 0:
                xPos = x;
                yPos = y;
                break;
            case 1:
                xPos = x - width / 2;
                yPos = y;
                break;
            case 2:
                xPos = x - width;
                yPos = y;
                break;
            case 3:
                xPos = x;
                yPos = y - height / 2;
                break;
            case 4:
                xPos = x - width / 2;
                yPos = y - height / 2;
                break;
            case 5:
                xPos = x - width;
                yPos = y - height / 2;
                break;
            case 6:
                xPos = x;
                yPos = y - height;
                break;
            case 7:
                xPos = x - width / 2;
                yPos = y - height;
                break;
            case 8:
                xPos = x - width;
                yPos = y - height;
                break;
        }
        setBounds(xPos, yPos, width, height);
    }

    // Auto called method of JPanel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(COLOR_LINE_EXITED);
        if (indexSubjectEntering == -1) {
            for (int i = 0; i < lines.size(); i++) {
                for (Line2D line : lines.get(i)) {
                    g2.draw(line);
                }
            }
        }
        
        if (indexSubjectEntering > -1) {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(COLOR_LINE_ENTERED);
            for (Line2D line : lines.get(indexSubjectEntering)) {
                g2.draw(line);
            }
        }  
    }

    private class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            for (int i = 0; i < panelSubjects.length; i++) {
                if (e.getSource() == panelSubjects[i]) {
                    DialogUpdateMapRelative dialog = new DialogUpdateMapRelative(Setting.WIDTH / 2,
                            Setting.HEIGHT / 2,
                            Setting.WIDTH / 3 * 2, Setting.HEIGHT / 5 * 4,
                            DialogUpdateMapRelative.CENTER_CENTER, "Update subject", (new String[] {}),
                            plan.getSubjects().get(i));
                    plan.getSubjects().set(i, dialog.getSubject());
                    WriteFile.editSubject(indexPlan, i, plan.getSubjects().get(i));
                }
            }
            updateContent();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            for (int count = 0; count < panelSubjects.length; count++) {
                if (e.getSource() == panelSubjects[count]) {
                    setIndexSubjectPanelEntering(count);
                    panelSubjects[count].setBackgroundColorPanelSubject(
                        COLOR_STROKE_PANEL_SUBJECT_ENTERED,
                        COLOR_SUBJECT_ENTERED);
                    for (int indexParentSubject : indexes.get(indexSubjectEntering)) {
                        panelSubjects[indexParentSubject].setBackgroundColorPanelSubject(
                            COLOR_STROKE_PANEL_SUBJECT_ENTERED, 
                            plan.getSubjects().get(indexParentSubject).getColor());
                    }
                }
            }
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setIndexSubjectPanelEntering(-1);
            for (int count = 0; count < panelSubjects.length; count++) {
                if (e.getSource() == panelSubjects[count]) {
                    panelSubjects[count].setBackgroundColorPanelSubject(
                        COLOR_STROKE_PANEL_SUBJECT_EXITED,
                        plan.getSubjects().get(count).getColor());
                    for (int indexParentSubject : indexes.get(count)) {
                        panelSubjects[indexParentSubject].setBackgroundColorPanelSubject(
                            COLOR_STROKE_PANEL_SUBJECT_EXITED, 
                            plan.getSubjects().get(indexParentSubject).getColor());
                    }
                }
            }
            repaint();
        }
    }
}