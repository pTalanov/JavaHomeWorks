package ru.spbau.talanov.task11;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Login window.
 */
public class LoginWindow extends JFrame {

    private static final String PICTURE_PATH = "cat.jpg";
    private final JTextField loginField;
    private final JPasswordField passwordField;
    private final JProgressBar progressBar = new JProgressBar();


    /**
     * Window initialization is done here.
     */
    public LoginWindow() {
        super("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginField = new JTextField();
        passwordField = new JPasswordField();
        createLayout();
        setUpPicture();
        setUpLoginLabel();
        setUpLoginFiled();
        setUpPasswordLabel();
        setUpPasswordField();
        setUpButtons();
        setUpProgressBar();
        pack();

    }

    private void setUpButtons() {
        JPanel buttonsPanel = createButtonsPanel();
        addClearButton(buttonsPanel);
        addRegisterButton(buttonsPanel);
        addLoginButton(buttonsPanel);
    }

    private void setUpProgressBar() {
        GridBagConstraints progressBarConstraints = new GridBagConstraints();
        progressBarConstraints.fill = GridBagConstraints.HORIZONTAL;
        progressBarConstraints.gridwidth = 8;
        progressBarConstraints.insets = new Insets(5, 5, 5, 5);
        progressBarConstraints.gridx = 0;
        progressBarConstraints.gridy = 7;
        getContentPane().add(progressBar, progressBarConstraints);
    }

    private void addLoginButton(@NotNull JPanel buttonsPanel) {
        JButton loginButton = new JButton("Login");
        loginButton.setVerticalAlignment(SwingConstants.BOTTOM);
        buttonsPanel.add(loginButton);
        ActionListener loginListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new Thread() {

                    public void run() {
                        progressBar.setMinimum(0);
                        progressBar.setMaximum(29);
                        for (int i = 0; i < 30; i++) {
                            updateProgress(i);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        LoginWindow.this.setVisible(false);
                        LoginWindow.this.dispose();
                        MainWindow mainWindow = new MainWindow(loginField.getText());
                        mainWindow.setVisible(true);
                    }

                    private void updateProgress(int i) {
                        final int i1 = i;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setValue(i1);
                            }
                        });
                    }

                }.start();
            }
        };
        loginButton.addActionListener(loginListener);
    }

    private void addRegisterButton(@NotNull JPanel buttonsPanel) {
        final JButton registerButton = new JButton("Register");
        registerButton.setVerticalAlignment(SwingConstants.BOTTOM);
        buttonsPanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(registerButton, "OK");
            }
        });

        buttonsPanel.add(Box.createHorizontalStrut(5));
    }

    private void addClearButton(@NotNull JPanel buttonsPanel) {
        JButton clearButton = new JButton("Clear");
        clearButton.setVerticalAlignment(SwingConstants.BOTTOM);
        buttonsPanel.add(clearButton);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginField.setText("");
                passwordField.setText("");
            }
        });

        buttonsPanel.add(Box.createHorizontalStrut(5));
    }

    @NotNull
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        GridBagConstraints buttonsPanelConstraints = new GridBagConstraints();
        buttonsPanelConstraints.insets = new Insets(5, 5, 5, 5);
        buttonsPanelConstraints.anchor = GridBagConstraints.SOUTH;
        buttonsPanelConstraints.gridwidth = 2;
        buttonsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanelConstraints.gridx = 3;
        buttonsPanelConstraints.gridy = 5;
        getContentPane().add(buttonsPanel, buttonsPanelConstraints);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        return buttonsPanel;
    }

    private void setUpPasswordField() {
        GridBagConstraints passwordFieldConstraints = new GridBagConstraints();
        passwordFieldConstraints.insets = new Insets(0, 0, 5, 5);
        passwordFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        passwordFieldConstraints.gridx = 4;
        passwordFieldConstraints.gridy = 1;
        getContentPane().add(passwordField, passwordFieldConstraints);
    }

    private void setUpPasswordLabel() {
        JLabel passwordLabel = new JLabel("Password:");
        GridBagConstraints passwordLabelConstraints = new GridBagConstraints();
        passwordLabelConstraints.insets = new Insets(0, 0, 5, 5);
        passwordLabelConstraints.anchor = GridBagConstraints.EAST;
        passwordLabelConstraints.gridx = 3;
        passwordLabelConstraints.gridy = 1;
        getContentPane().add(passwordLabel, passwordLabelConstraints);
    }

    private void setUpLoginFiled() {
        GridBagConstraints loginFieldContstraints = new GridBagConstraints();
        loginFieldContstraints.insets = new Insets(5, 0, 5, 5);
        loginFieldContstraints.fill = GridBagConstraints.HORIZONTAL;
        loginFieldContstraints.gridx = 4;
        loginFieldContstraints.gridy = 0;
        getContentPane().add(loginField, loginFieldContstraints);
        loginField.setColumns(10);
    }

    private void setUpLoginLabel() {
        JLabel loginLabel = new JLabel("Login:");
        GridBagConstraints loginConstraints = new GridBagConstraints();
        loginConstraints.insets = new Insets(0, 0, 5, 5);
        loginConstraints.anchor = GridBagConstraints.EAST;
        loginConstraints.gridx = 3;
        loginConstraints.gridy = 0;
        getContentPane().add(loginLabel, loginConstraints);
    }

    private void createLayout() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
    }

    private void setUpPicture() {
        final JLabel picture = new JLabel("");
        GridBagConstraints pictureConstraints = new GridBagConstraints();
        pictureConstraints.gridheight = 10;
        pictureConstraints.insets = new Insets(2, 15, 10, 5);
        pictureConstraints.fill = GridBagConstraints.BOTH;
        pictureConstraints.gridx = 0;
        pictureConstraints.gridy = 0;

        ImageIcon img = new ImageIcon(PICTURE_PATH);
        picture.setIcon(new ImageIcon(img.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH)));

        final int originalWidth = img.getIconWidth();
        final int originalHeight = img.getIconHeight();

        picture.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                ImageIcon img = new ImageIcon(PICTURE_PATH);

                int newWidth;
                int newHeight;

                if (((double) picture.getWidth() / originalWidth) > ((double) picture.getHeight() / originalHeight)) {
                    newWidth = (int) (originalWidth * ((double) picture.getHeight() / originalHeight));
                    newHeight = picture.getHeight();
                } else {
                    newHeight = (int) (originalHeight * ((double) picture.getWidth() / originalWidth));
                    newWidth = picture.getWidth();
                }

                picture.setIcon(new ImageIcon(img.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                //do nothing
            }

            @Override
            public void componentShown(ComponentEvent e) {
                //do nothing
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                //do nothing
            }
        });


        getContentPane().add(picture, pictureConstraints);
    }
}
