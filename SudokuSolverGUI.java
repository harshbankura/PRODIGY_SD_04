import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI extends JFrame implements ActionListener {

    private JTextField[][] gridFields = new JTextField[9][9];
    private JButton solveButton;
    private JPanel gridPanel;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gridPanel = new JPanel(new GridLayout(9, 9));

        initUI();

        add(gridPanel, BorderLayout.CENTER);
    }

    private void initUI() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridFields[i][j] = new JTextField(1);
                gridPanel.add(gridFields[i][j]);
            }
        }

        solveButton = new JButton("Solve");
        solveButton.addActionListener(this);

        add(solveButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton) {
            int[][] sudokuGrid = new int[9][9];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String value = gridFields[i][j].getText();
                    sudokuGrid[i][j] = value.isEmpty() ? 0 : Integer.parseInt(value);
                }
            }

            if (solveSudoku(sudokuGrid)) {
                updateUI(sudokuGrid);
            } else {
                JOptionPane.showMessageDialog(this, "No solution exists.");
            }
        }
    }

    private boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solveSudoku(grid)) {
                                return true;
                            }
                            grid[row][col] = 0; // Backtrack
                        }
                    }
                    return false; // No valid number found, backtrack
                }
            }
        }
        return true; // All cells filled
    }

    private boolean isSafe(int[][] grid, int row, int col, int num) {
        // Check row, column, and 3x3 subgrid
        for (int x = 0; x < 9; x++) {
            if (grid[row][x] == num || grid[x][col] == num || grid[row - row % 3 + x / 3][col - col % 3 + x % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private void updateUI(int[][] solvedGrid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridFields[i][j].setText(String.valueOf(solvedGrid[i][j]));
                gridFields[i][j].setEditable(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuSolverGUI solverGUI = new SudokuSolverGUI();
            solverGUI.setVisible(true);
        });
    }
}
