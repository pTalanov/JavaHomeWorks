package ru.spbau.talanov.task11;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

/**
 * Main window of application.
 */
public final class MainWindow extends JFrame {

    private final JButton openButton;
    private final JTabbedPane tabbedPane;
    private final JButton aboutButton;
    private final Action closeAction;

    /**
     * @param userName name to greet in title.
     */
    public MainWindow(@NotNull String userName) {
        super("Hello, " + userName + "!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        Action openAction = createOpenAction();
        closeAction = createCloseAction();
        Action aboutAction = createAboutAction();
        Action exitAction = createExitAction();
        menuBar.add(createFileMenu(openAction, aboutAction, exitAction));
        JPanel contentPane = createContentPane();
        openButton = new JButton(openAction);
        setuUpOpenButton(contentPane);
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        setUpTabbedPane(contentPane);
        setUpCloseButton(contentPane);
        aboutButton = new JButton(aboutAction);
        setUpAboutButton(contentPane);
        setUpExitButton(exitAction, contentPane);
        updateCloseActionAvailability();
    }

    private void setUpAboutButton(@NotNull JPanel contentPane) {
        GridBagConstraints aboutButtonConstraints = new GridBagConstraints();
        aboutButtonConstraints.anchor = GridBagConstraints.SOUTH;
        aboutButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        aboutButtonConstraints.insets = new Insets(0, 0, 5, 5);
        aboutButtonConstraints.gridx = 0;
        aboutButtonConstraints.gridy = 2;
        contentPane.add(aboutButton, aboutButtonConstraints);
    }

    private void setUpExitButton(@NotNull Action exitAction, @NotNull JPanel contentPane) {
        JButton exitButton = new JButton(exitAction);
        GridBagConstraints exitButtonConstaints = new GridBagConstraints();
        exitButtonConstaints.anchor = GridBagConstraints.SOUTH;
        exitButtonConstaints.fill = GridBagConstraints.HORIZONTAL;
        exitButtonConstaints.insets = new Insets(0, 0, 5, 5);
        exitButtonConstaints.gridx = 0;
        exitButtonConstaints.gridy = 3;
        contentPane.add(exitButton, exitButtonConstaints);
    }

    private void setUpCloseButton(@NotNull JPanel contentPane) {
        JButton closeButton = new JButton(closeAction);
        closeButton.setHideActionText(true);
        closeButton.setText("Close");
        GridBagConstraints closeButtonConstaints = new GridBagConstraints();
        closeButtonConstaints.anchor = GridBagConstraints.NORTH;
        closeButtonConstaints.fill = GridBagConstraints.HORIZONTAL;
        closeButtonConstaints.insets = new Insets(0, 0, 5, 5);
        closeButtonConstaints.gridx = 0;
        closeButtonConstaints.gridy = 1;
        contentPane.add(closeButton, closeButtonConstaints);
    }

    private void setUpTabbedPane(@NotNull JPanel contentPane) {
        GridBagConstraints tabbedPaneConstaints = new GridBagConstraints();
        tabbedPaneConstaints.gridheight = 4;
        tabbedPaneConstaints.insets = new Insets(0, 0, 5, 0);
        tabbedPaneConstaints.fill = GridBagConstraints.BOTH;
        tabbedPaneConstaints.gridx = 1;
        tabbedPaneConstaints.gridy = 0;
        contentPane.add(tabbedPane, tabbedPaneConstaints);
    }

    private void setuUpOpenButton(@NotNull JPanel contentPane) {
        GridBagConstraints openButtonConstraints = new GridBagConstraints();
        openButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        openButtonConstraints.anchor = GridBagConstraints.NORTH;
        openButtonConstraints.insets = new Insets(0, 0, 5, 5);
        openButtonConstraints.gridx = 0;
        openButtonConstraints.gridy = 0;
        contentPane.add(openButton, openButtonConstraints);
    }

    @NotNull
    private JPanel createContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout contentPaneLayout = new GridBagLayout();
        contentPaneLayout.columnWidths = new int[]{0, 0, 0};
        contentPaneLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        contentPaneLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        contentPaneLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(contentPaneLayout);
        return contentPane;
    }

    @NotNull
    private JMenu createFileMenu(@NotNull Action openAction, @NotNull Action aboutAction, @NotNull Action exitAction) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem(openAction));
        fileMenu.add(new JMenuItem(closeAction));
        fileMenu.add(new JSeparator());
        fileMenu.add(new JMenuItem(aboutAction));
        fileMenu.add(new JMenuItem(exitAction));
        return fileMenu;
    }

    @NotNull
    private Action createExitAction() {
        return new AbstractAction() {

            {
                putValue(Action.NAME, "Exit");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.this.dispose();
            }
        };
    }

    @NotNull
    private Action createAboutAction() {
        return new AbstractAction() {

            {
                putValue(Action.NAME, "About");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(aboutButton, "Task 11.\n");
            }
        };
    }

    @NotNull
    private Action createOpenAction() {
        return new AbstractAction() {
            {
                putValue(Action.NAME, "Open");
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(openButton);
                if (result != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                final File selectedFile = fileChooser.getSelectedFile();

                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    JTextArea textArea = new JTextArea();
                    textArea.read(reader, null);
                    textArea.setEditable(false);
                    final JScrollPane scrollPane =
                            new JScrollPane(textArea, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            tabbedPane.addTab(selectedFile.getName(), scrollPane);
                            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                            updateCloseActionAvailability();
                        }
                    });
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(openButton, "File not found.");
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(openButton, "Error opening file.");
                }
            }
        };
    }

    private void updateCloseActionAvailability() {
        closeAction.setEnabled(tabbedPane != null && tabbedPane.getTabCount() > 0);
    }

    @NotNull
    private Action createCloseAction() {
        return new AbstractAction() {

            {
                putValue(Action.NAME, "Close");
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if ((tabbedPane != null) && (tabbedPane.getTabCount() > 0)) {
                            tabbedPane.remove(tabbedPane.getSelectedIndex());
                        }
                        updateCloseActionAvailability();
                    }
                });
            }
        };
    }

}
