import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Patnactka extends JFrame implements ActionListener {
    private JPanel panel;
    private JButton[] buttons;
    private final int size = 4;
    private final int tileCount = size * size;
    private ArrayList<Integer> tileList;

    /**
     * Class constructor. Sets the basic properties of the window.
     * Initializes the game panel, buttons, and card list.
     * Displays a welcome dialog with instructions.
     */
    public Patnactka() {
        setTitle("Patnáctka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(size, size));
        buttons = new JButton[tileCount];
        tileList = new ArrayList<>();

        JOptionPane.showMessageDialog(this, "0 represents the place of an empty field!");

        initializeTiles();
        shuffleTiles();

        for (int i = 0; i < tileCount; i++) {
            JButton button = new JButton(String.valueOf(tileList.get(i)));
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            buttons[i] = button;
            button.addActionListener(this);
            panel.add(button);
        }
        add(panel);
        setVisible(true);
    }

    /**
     * Initializes the list of tiles with numbers from 1 to tileCount and marks an empty tile with 0.
     */
    private void initializeTiles() {
        for (int i = 0; i < tileCount; i++) {
            tileList.add(i + 1);
        }
        tileList.set(tileCount - 1, 0);
    }

    /**
     * method for shuffling buttons
     */
    private void shuffleTiles() {
        Collections.shuffle(tileList);
    }

    /**
     * Checks if the cards in the current arrangement are lined up in a winning manner and displays a congratulatory dialog if the player has won.
     */
    private void checkForWin() {
        ArrayList<Integer> currentTiles = new ArrayList<>();
        for (JButton button : buttons) {
            currentTiles.add(Integer.parseInt(button.getText()));
        }
        currentTiles.remove(Integer.valueOf(0));

        ArrayList<Integer> sortedTiles = new ArrayList<>(currentTiles);
        Collections.sort(sortedTiles);

        if (currentTiles.equals(sortedTiles)) {
            JOptionPane.showMessageDialog(this, "Congratulations! You've won!");
        }
    }

    /**
     * Implements the ActionListener interface.
     * Gets information about the button that was pressed.
     * Finds the index of the empty field and the index of the pressed button.
     * If the buttons are next to each other, it will swap them, update the graphic display and check if the player has won.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        int emptyTileIndex = tileList.indexOf(0);
        int clickedIndex = -1;

        for (int i = 0; i < tileCount; i++) {
            if (buttons[i] == clickedButton) {
                clickedIndex = i;
                break;
            }
        }

        if (isAdjacent(emptyTileIndex, clickedIndex)) {
            Collections.swap(tileList, emptyTileIndex, clickedIndex);
            updateButtons();
            checkForWin();
        }
    }

    /**
     * Determines if two buttons are next to each other in the game grid.
     * Returns true if they are adjacent.
     * @param empty
     * @param clicked
     * @return
     */
    private boolean isAdjacent(int empty, int clicked) {
        return (Math.abs(empty - clicked) == 1 && (Math.min(empty, clicked) / size == Math.max(empty, clicked) / size)
                || (Math.abs(empty - clicked) == size));
    }

    /**
     * Updates the text on the buttons according to the current tab layout.
     */
    private void updateButtons() {
        for (int i = 0; i < tileCount; i++) {
            buttons[i].setText(String.valueOf(tileList.get(i)));
        }
    }
}


