/*
 * MineSweeperView.java
 */
package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * The application's main frame.
 */
public class MineSweeperView extends FrameView {

    MineSweepController mineSweepController;
    JButton gameButtons[][];
    TimeTimer refreshTime;
    private int helpCount;

    public MineSweeperView(SingleFrameApplication app) {
        super(app);
        initComponents();
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                }
            }
        });
        jTextField1.setHorizontalAlignment(JTextField.CENTER);
        jTextField2.setHorizontalAlignment(JTextField.CENTER);
        jTextField3.setHorizontalAlignment(JTextField.CENTER);
    }

    @Action
    public void showAboutBoxServer() {
        JFrame mainFrame = MineSweeperApp.getApplication().getMainFrame();
        aboutBox = new MineSweeperAboutBoxServer(mainFrame);
        aboutBox.setLocationRelativeTo(mainFrame);
        MineSweeperApp.getApplication().show(aboutBox);
    }

    @Action
    public void showAboutBoxClient() {
        JFrame mainFrame = MineSweeperApp.getApplication().getMainFrame();
        aboutBox = new MineSweeperAboutBoxClient(mainFrame);
        aboutBox.setLocationRelativeTo(mainFrame);
        MineSweeperApp.getApplication().show(aboutBox);
    }

    @Action
    public void showAboutBox() {
        refreshTime.stop();
        JFrame mainFrame = MineSweeperApp.getApplication().getMainFrame();
        HighScore highScore = new HighScore(mineSweepController.getWidth(), mineSweepController.getHeight(), mineSweepController.getMines(), (long) (mineSweepController.getGameTable().getMiliSecondsInGame() * (getHelpCount())), "quest");
        aboutBox = new MineSweeperAboutBox(mainFrame, "YOU ARE DEAD!", highScore);
        aboutBox.setLocationRelativeTo(mainFrame);
        MineSweeperApp.getApplication().show(aboutBox);
    }

    @Action
    public void showAboutBoxWin() {
        refreshTime.stop();
        JFrame mainFrame = MineSweeperApp.getApplication().getMainFrame();
        HighScore highScore = new HighScore(mineSweepController.getWidth(), mineSweepController.getHeight(), mineSweepController.getMines(), (long) (mineSweepController.getGameTable().getMiliSecondsInGame() * (getHelpCount())), "quest");
        aboutBox = new MineSweeperAboutBoxWin(mainFrame, "YOU WON", highScore);
        aboutBox.setLocationRelativeTo(mainFrame);
        MineSweeperApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(minesweeper.MineSweeperApp.class).getContext().getResourceMap(MineSweeperView.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                mainPanelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 383, Short.MAX_VALUE)
        );

        menuBar.setBackground(resourceMap.getColor("menuBar.background")); // NOI18N
        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setBackground(resourceMap.getColor("fileMenu.background")); // NOI18N
        fileMenu.setForeground(resourceMap.getColor("fileMenu.foreground")); // NOI18N
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItemZacatecnik.text")); // NOI18N
        jMenuItem1.setName("jMenuItemZacatecnik"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed1(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem3);

        jMenuItem2.setText(resourceMap.getString("jMenuItemExpert.text")); // NOI18N
        jMenuItem2.setName("jMenuItemExpert"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem2);

        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem4);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(minesweeper.MineSweeperApp.class).getContext().getActionMap(MineSweeperView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setBackground(resourceMap.getColor("jMenu1.background")); // NOI18N
        jMenu1.setForeground(resourceMap.getColor("jMenu1.foreground")); // NOI18N
        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText(resourceMap.getString("jMenuItem6.text")); // NOI18N
        jMenuItem6.setName("jMenuItem6"); // NOI18N
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        menuBar.add(jMenu1);

        helpMenu.setBackground(resourceMap.getColor("helpMenu.background")); // NOI18N
        helpMenu.setForeground(resourceMap.getColor("helpMenu.foreground")); // NOI18N
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setBackground(resourceMap.getColor("statusPanel.background")); // NOI18N
        statusPanel.setName("statusPanel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        jButton1.setBackground(resourceMap.getColor("jButtonHelp.background")); // NOI18N
        jButton1.setFont(resourceMap.getFont("jButtonHelp.font")); // NOI18N
        jButton1.setForeground(resourceMap.getColor("jButtonHelp.foreground")); // NOI18N
        jButton1.setText(resourceMap.getString("jButtonHelp.text")); // NOI18N
        jButton1.setBorder(new javax.swing.border.MatteBorder(null));
        jButton1.setName("jButtonHelp"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setBackground(resourceMap.getColor("jTextField1.background")); // NOI18N
        jTextField1.setFont(resourceMap.getFont("jTextField1.font")); // NOI18N
        jTextField1.setForeground(resourceMap.getColor("jTextField1.foreground")); // NOI18N
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setBorder(new javax.swing.border.MatteBorder(null));
        jTextField1.setName("jTextField1"); // NOI18N

        jTextField2.setBackground(resourceMap.getColor("jTextFieldMines.background")); // NOI18N
        jTextField2.setFont(resourceMap.getFont("jTextFieldMines.font")); // NOI18N
        jTextField2.setForeground(resourceMap.getColor("jTextFieldMines.foreground")); // NOI18N
        jTextField2.setBorder(new javax.swing.border.MatteBorder(null));
        jTextField2.setName("jTextFieldMines"); // NOI18N

        jLabel1.setBackground(resourceMap.getColor("jLabel1.background")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField3.setBackground(resourceMap.getColor("jTextField3.background")); // NOI18N
        jTextField3.setFont(resourceMap.getFont("jTextField3.font")); // NOI18N
        jTextField3.setForeground(resourceMap.getColor("jTextField3.foreground")); // NOI18N
        jTextField3.setBorder(new javax.swing.border.MatteBorder(null));
        jTextField3.setName("jTextField3"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181)
                .addComponent(statusAnimationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusMessageLabel)
                            .addComponent(statusAnimationLabel))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.createGame(9, 9, 10);
        //mineSweepController = new MineSweepController(9,9,10)
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.createGame(30, 16, 99);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mainPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_mainPanelComponentResized
        if (mineSweepController != null) {
            resizeButtons();
        }
    }//GEN-LAST:event_mainPanelComponentResized

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if ((mineSweepController != null) && (mineSweepController.isHelp() == false)) {
            mineSweepController.refresh(gameButtons, true);
            helpCount++;
        }
        jTextField3.setText(Float.toString(getHelpCount()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.createGame(16, 16, 40);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        this.createGame(35, 25, 215);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed1
        this.createGame(9, 9, 10);
    }//GEN-LAST:event_jMenuItem1ActionPerformed1

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        //showAboutBoxServer();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        //showAboutBoxClient();
    }//GEN-LAST:event_jMenuItem6ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

    private void createGame(int width, int height, int mines) {
        helpCount = 0;
        jTextField2.setText(Integer.toString(mines));
        if (mineSweepController != null) {
            for (int i = 0; i < mineSweepController.getWidth(); i++) {
                for (int j = 0; j < mineSweepController.getHeight(); j++) {
                    mainPanel.remove(gameButtons[i][j]);
                }
            }
        }
        mineSweepController = new MineSweepController(width, height, mines);
        this.createButtons(width, height);
        if (refreshTime != null) {
            refreshTime.stop();
        }
        refreshTime = new TimeTimer(jTextField1, mineSweepController);
        jTextField3.setText("1.0");
    }

    private void createButtons(int width, int height) {
        gameButtons = new JButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JButton tlacitko = new JButton("");
                tlacitko.setMargin(new Insets(0, 0, 0, 0));
                tlacitko.setName("button" + i + "next" + j);
                tlacitko.setBackground(new Color(0, 0, 150));
                final int x = i;
                final int y = j;

                tlacitko.addMouseListener(new java.awt.event.MouseAdapter() {

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        pressedMouse(evt, x, y);
                    }
                });

                tlacitko.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        pressedButton(x, y);
                    }
                });

                mainPanel.add(tlacitko);
                mainPanel.repaint();
                gameButtons[x][y] = tlacitko;
            }
        }
        resizeButtons();
    }

    private void pressedButton(int x, int y) {
        mineSweepController.pressedButton(x, y, gameButtons);
        check();
    }

    private void pressedMouse(java.awt.event.MouseEvent evt, int x, int y) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            mineSweepController.rightClick(x, y, gameButtons);
            jTextField2.setText(Integer.toString(mineSweepController.minesLeft()));
            check();
        }
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON2) {
            mineSweepController.middleClick(x, y, gameButtons);
        }
    }

    private void check() {
        if (checkDead() == false) {
            checkWin();
        }
    }

    private boolean checkDead() {
        if (mineSweepController.isDead()) {
            showAboutBox();
            createGame(mineSweepController.getWidth(), mineSweepController.getHeight(), mineSweepController.getMines());
            return true;
        }
        return false;
    }

    private void checkWin() {
        if (mineSweepController.findAllEmpty().isEmpty()) {
            showAboutBoxWin();
            createGame(mineSweepController.getWidth(), mineSweepController.getHeight(), mineSweepController.getMines());
        }
    }

    private void resizeButtons() {
        for (int i = 0; i < mineSweepController.getWidth(); i++) {
            for (int j = 0; j < mineSweepController.getHeight(); j++) {
                gameButtons[i][j].setBounds(i * (mainPanel.getWidth() / mineSweepController.getWidth()),
                        j * (mainPanel.getHeight() / mineSweepController.getHeight()),
                        (mainPanel.getWidth() / mineSweepController.getWidth()),
                        (mainPanel.getHeight() / mineSweepController.getHeight()));
                int size = (int) (mainPanel.getHeight() / (mineSweepController.getHeight()));
                if (size > 70) {
                    size = 70;
                }
                gameButtons[i][j].setFont(new Font("Serif", Font.BOLD, size));
            }
        }
        jTextField1.setLocation(mainPanel.getWidth() / 2, mainPanel.getHeight() / 2);
        jTextField1.setBounds(mainPanel.getWidth() / 2, mainPanel.getHeight() / 2, jTextField1.getWidth(), jTextField1.getHeight());
    }

    private float getHelpCount() {
        return (1 + (float) this.helpCount / 5);
    }
}
