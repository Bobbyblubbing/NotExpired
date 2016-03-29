package nex.gui;

import nex.main.Database;
import nex.main.Item;
import nex.util.io;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jsomani on 3/28/2016.
 */
public class MainGUI extends JFrame {
    public static final String NEX_VERSION = "v0.0.1-beta";
    private JLabel titleLabel = new JLabel("NotExpired", JLabel.CENTER);
    private JLabel subtitleLabel = new JLabel(NEX_VERSION, JLabel.CENTER);
    protected Font headingFont = new Font("Segoe UI", Font.BOLD, 72);
    protected Font smallFont = new Font("Segoe UI", Font.BOLD, 12);
    //protected Font largeFont = new Font("Segoe UI", Font.BOLD, 36);
    private JButton settingsButton = new JButton("Settings");
    private JButton checkItem = new JButton("Check Item");
    private JButton quitButton = new JButton("Quit");
    public String currentDatabasePath = "";
    Database currentDatabase = new Database("Null Database");

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
                runItemCheck();
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

    public void runItemCheck() {
        getCurrentDatabase().addItem(new Item(21,"test"));
        Date resultDate;
        Date dateExpire;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        if(currentDatabase.getDatabase().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Current database is empty. Download or create one.");
        }
        else {
            try {
                Item item = (Item)JOptionPane.showInputDialog(null,"Select your item from the current database","Select item",JOptionPane.PLAIN_MESSAGE,null,getCurrentDatabase().getDatabase().toArray(),null);
                dateExpire = dateFormat.parse(JOptionPane.showInputDialog(null,"Please enter the date your item will expire: (MM/DD/YYYY)"));
                System.out.println(dateFormat.format(dateExpire));
                Calendar c = Calendar.getInstance();
                c.setTime(dateExpire);
                c.add(Calendar.DATE, item.getDaysToExpire()); // number of days to add
                dateExpire = c.getTime();  //this is now the new date
                System.out.println(dateFormat.format(dateExpire));

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null,"Unable to parse date from input.");
            }
        }

    }

    public String getCurrentDatabasePath() {
        return currentDatabasePath;
    }

    public void setCurrentDatabasePath(String currentDatabasePath) {
        this.currentDatabasePath = currentDatabasePath;
    }

    public Database getCurrentDatabase() {
        return currentDatabase;
    }

    public void setCurrentDatabase(Database currentDatabase) {
        this.currentDatabase = currentDatabase;
    }

    private void openSettings() {
        JLabel settingsTitleLabel = new JLabel("Settings", JLabel.CENTER);
        JLabel settingsSubtitleLabel = new JLabel("NEX "+NEX_VERSION, JLabel.CENTER);
        JButton setCurrentDBButton = new JButton("Set Current Database");
        JButton newDatabase = new JButton("New Database");
        JButton editDBButton = new JButton("Edit Database");
        JButton backButton = new JButton("Back");
        JFrame settingsFrame = new JFrame("Settings");
        settingsTitleLabel.setFont(headingFont);
        settingsSubtitleLabel.setFont(smallFont);
        settingsFrame.getContentPane().setBackground(Color.GRAY);
        settingsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        settingsFrame.setVisible(true);
        settingsFrame.setLayout(new GridLayout(6,1));
        settingsFrame.add(settingsTitleLabel);
        settingsFrame.add(settingsSubtitleLabel);
        settingsFrame.add(setCurrentDBButton);
        settingsFrame.add(newDatabase);
        settingsFrame.add(backButton);
        settingsFrame.add(editDBButton);
        settingsFrame.pack();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.dispose();
            }
        });
        setCurrentDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setDialogTitle("Select Path");
                jf.showOpenDialog(null);
                setCurrentDatabasePath(jf.getSelectedFile().getAbsolutePath());
                setCurrentDatabase(io.readDatabase(getCurrentDatabasePath()));
            }
        });
        newDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newDBName = JOptionPane.showInputDialog(null, "Name new database:");
                JFileChooser jf = new JFileChooser();
                jf.setDialogTitle("Select Path");
                jf.showOpenDialog(null);
                setCurrentDatabasePath(jf.getSelectedFile().getAbsolutePath());
                io.saveDatabase(getCurrentDatabasePath(), new Database(newDBName));
            }
        });
        editDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentDatabase().addItem(new Item(21,"test"));
                int editMenu = JOptionPane.showOptionDialog(null,"What would you like to edit in '"+getCurrentDatabase().getDbName() + "'?","Edit DB",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,new String[] {"DB Name","DB Items"},null);
                if(editMenu == 0) {
                    String newDBName = JOptionPane.showInputDialog(null, "Rename database:");
                    getCurrentDatabase().setDbName(newDBName);
                }
                else if(editMenu == 1) {
                    int editMenu2 = JOptionPane.showOptionDialog(null, "Choose a selection,", "Edit DB", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Add Item", "Remove Item", "Edit Item"}, null);
                    if (editMenu2 == 0) {
                        String newItemName = JOptionPane.showInputDialog(null, "Name this item:");
                        String newItemDaysToExpire = JOptionPane.showInputDialog(null, "How many days does it take for this item to expire? (on avg.)");
                        try {
                            getCurrentDatabase().addItem(new Item(Integer.parseInt(newItemDaysToExpire), newItemName));
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "You typed something incorrectly.");
                        }
                        JOptionPane.showMessageDialog(null, "New item, " + newItemName + "created!");
                    } else if (editMenu2 == 1) {
                        Object[] dbToArray = getCurrentDatabase().getDatabase().toArray();
                       Object objectToRemove = JOptionPane.showInputDialog(null, "Select item to remove", "Edit DB | Remove", JOptionPane.INFORMATION_MESSAGE, null, dbToArray, null);
                        getCurrentDatabase().getDatabase().remove(objectToRemove); //direct removal as this is an object not an item as declared in the Database class
                    }
                    else if(editMenu2 == 2) {
                        Object[] dbToArray = getCurrentDatabase().getDatabase().toArray();
                        Item itemChoice = (Item)JOptionPane.showInputDialog(null, "Select item to remove", "Edit DB | Edit", JOptionPane.INFORMATION_MESSAGE, null, dbToArray, null);
                        String newItemName = JOptionPane.showInputDialog(null, "Rename this item: (previously: "+itemChoice.getName() +")");
                        String newItemDaysToExpire = JOptionPane.showInputDialog(null, "Change how many days it takes for this item to expire: (previously: "+itemChoice.getDaysToExpire() +")");
                        itemChoice.setName(newItemName);
                        try {
                            itemChoice.setDaysToExpire(Integer.parseInt(newItemDaysToExpire));
                        }
                        catch(NumberFormatException f) {
                            JOptionPane.showConfirmDialog(null, "You typed something incorrectly.");
                        }
                    }
                }

            }
        });



    }

    public static void main(String[] args) {
        MainGUI m = new MainGUI();
        m.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        m.pack();
        m.setResizable(false);
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        JOptionPane.showMessageDialog(null,"Note: Be sure to always save your databases.\nThe Autosave system may not catch your work.");
        Thread autoSaveThread = new Thread() {
            @Override
            public void run() {
                    io.autoSaveDatabase(m.getCurrentDatabasePath(), m.getCurrentDatabase());
                try {
                    Thread.sleep(7500); //save every 7.5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        autoSaveThread.run();
    }
    }

