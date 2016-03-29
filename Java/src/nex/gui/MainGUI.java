package nex.gui;

import nex.main.Database;
import nex.util.io;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jsomani on 3/28/2016.
 */
public class MainGUI extends JFrame {
    public static final String NEX_VERSION = "v0.1.0";
    private JLabel titleLabel = new JLabel("NotExpired", JLabel.CENTER);
    private JLabel subtitleLabel = new JLabel(NEX_VERSION, JLabel.CENTER);
    protected Font headingFont = new Font("Segoe UI", Font.BOLD, 72);
    protected Font smallFont = new Font("Segoe UI", Font.BOLD, 12);
    protected Font largeFont = new Font("Segoe UI", Font.BOLD, 36);
    private JButton settingsButton = new JButton("Settings");
    private JButton checkItem = new JButton("Check Item");
    private JButton quitButton = new JButton("Quit");
    public String currentDatabasePath = "";
    Database currentDatabse = new Database();

    public MainGUI() {
        super("NotExpired " + NEX_VERSION);
        getContentPane().setBackground(Color.GRAY);
        setLayout(new GridLayout(5, 1));
        titleLabel.setFont(headingFont);
        subtitleLabel.setFont(smallFont);
        add(titleLabel);
        add(subtitleLabel);
        add(checkItem);
        add(settingsButton);
        add(quitButton); //taking a new way to use action listener
        checkItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // STOPSHIP: 3/28/2016  runItemCheck();
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }

    public String getCurrentDatabasePath() {
        return currentDatabasePath;
    }

    public void setCurrentDatabasePath(String currentDatabasePath) {
        this.currentDatabasePath = currentDatabasePath;
    }

    public Database getCurrentDatabse() {
        return currentDatabse;
    }

    public void setCurrentDatabse(Database currentDatabse) {
        this.currentDatabse = currentDatabse;
    }

    private void openSettings() {
        JLabel settingsTitleLabel = new JLabel("Settings", JLabel.CENTER);
        JLabel settingsSubtitleLabel = new JLabel("NEX "+NEX_VERSION, JLabel.CENTER);
        JButton addDatabase = new JButton("Add Database");
        JButton editDatabase = new JButton("New Database");
        JButton backButton = new JButton("Back");
        JFrame settingsFrame = new JFrame("Settings");
        settingsTitleLabel.setFont(headingFont);
        settingsSubtitleLabel.setFont(smallFont);
        settingsFrame.getContentPane().setBackground(Color.GRAY);
        settingsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        settingsFrame.setVisible(true);
        settingsFrame.setLayout(new GridLayout(5,1));
        settingsFrame.add(settingsTitleLabel);
        settingsFrame.add(settingsSubtitleLabel);
        settingsFrame.add(addDatabase);
        settingsFrame.add(editDatabase);
        settingsFrame.add(backButton);
        settingsFrame.pack();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        addDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        editDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setDialogTitle("Select Path");
                jf.showOpenDialog(null);
                setCurrentDatabasePath(jf.getSelectedFile().getAbsolutePath());
                io.saveDatabase(getCurrentDatabasePath(), currentDatabse);

            }
        });



    }

    public static void main(String[] args) {
        MainGUI m = new MainGUI();
        m.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        m.pack();
        m.setResizable(false);
        m.setVisible(true);
    }

}
