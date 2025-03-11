import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;

public class mazeDFS extends JFrame {

    // attribute
    private static final int SIZE = 27; // groesse des labyrinths
    private static final int CELL_SIZE = 25; // groesse der einzelnen "zellen" (in pixel)
    private static final int DELAY = 20; // verzoegerung fuer die visualisierung (in millisekunden)

    // labyrinth-attribute
    private final JPanel[][] maze;  // 2d-array als "spielbrett"
    private final boolean[][] visited; 

    // UI-attribute
    private final JButton generateButton;
    private final JButton solveButton;
    private boolean solving = false; // wird grad geloest?
    private boolean generating = false; // wird grad erstellt?


    public mazeDFS() {
        maze = new JPanel[SIZE][SIZE];
        visited = new boolean[SIZE][SIZE];

        // fenster erstellen
        setTitle("DFS-Maze generator and solver");
        setSize(SIZE * CELL_SIZE, SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // programm clean beenden wenn fenster geschlossen wird
        setLayout(new BorderLayout());

        JPanel mazePanel = new JPanel(new GridLayout(SIZE, SIZE));
        JPanel buttonPanel = new JPanel();

        /*
        * jede zelle zu einer mauer machen,
        * spaeter wird das labyrinth eingezeichnet
         */
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JPanel cell = new JPanel();
                cell.setBackground(Color.BLACK);
                maze[i][j] = cell;
                mazePanel.add(cell);
            }
        }

        // 2 buttons erstellen, einer zum generieren und einer zum loesen
        generateButton = new JButton("Generate");
        solveButton = new JButton("Solve");

        generateButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMazeButton(e);
            }
        });

        solveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMazeButton(e);
            }
        });


        buttonPanel.add(generateButton);
        buttonPanel.add(solveButton);

        add(mazePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void generateMazeButton(ActionEvent e) {
        /*
        * ueberprüfen, ob bereits generiert oder geloest wird
        * wenn ja --> nix tun
        * wenn nein --> generieren
         */
        
        if (!solving && !generating) {
            new Thread(new Runnable() {
                public void run() {
                    generateMaze();
                }
            }).start();
        } else {
            //wenn bereits eins erstellt wird oder eins geloest wird, mach nichts
            return;
        }
    }


    private void generateMaze() {
        generating = true; // jerzt wird generiert

        // alle zellen wieder auf "nicht besucht"/"mauer" zurücksetzen
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                visited[i][j] = false;
                maze[i][j].setBackground(Color.BLACK);
            }
        }

        // stack den dfs-alg. initieren
        Stack<int[]> stack = new Stack<>();
        Random random = new Random();

        // startpunkt des dfs-alg. zur generierung, hier: oben rechts
        int startX = 1;
        int startY = SIZE - 2;

        visited[startX][startY] = true;
        stack.push(new int[]{startX, startY});
        updateCell(startX, startY, Color.WHITE);

        // richtungen in 2er-Schritten, um Wände zu überspringen
        int[][] directions = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};

        while (!stack.isEmpty()) {
            // ".peek()" is wie ".pop()", nur dass es das oberste element nur abliest und nicht entfernt
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

            ArrayList<int[]> neighbors = new ArrayList<>();
            for (int[] direction : directions) {
                int neighborsX = x + direction[0];
                int neighborsY = y + direction[1];

                // innerhalb vom spielfeld und noch nicht besucht?
                if (neighborsX > 0 && neighborsY > 0 && neighborsX < SIZE - 1 && neighborsY < SIZE - 1 && visited[neighborsX][neighborsY] == false) {
                    neighbors.add(new int[]{neighborsX, neighborsY});
                }
            }

            // zufaelligen nachbarn auswaehlen
            if (neighbors.isEmpty() == false) {
                int[] next = neighbors.get(random.nextInt(neighbors.size()));
                int nextX = next[0];
                int nextY = next[1];

                updateCell((x + nextX) / 2, (y + nextY) / 2, Color.WHITE);  // mauer entfernen, damit die beiden zellen verbunden werden
                updateCell(nextX, nextY, Color.WHITE);  // weiss faerben

                visited[nextX][nextY] = true;
                stack.push(next);
            } else {
                stack.pop();
            }

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        generating = false; // generieren fertig

    }


    private void solveMazeButton(ActionEvent e) {
        /*
        * ueberprüfen, ob bereits generiert oder geloest wird
        * wenn ja --> nix tun
        * wenn nein --> loesen
         */
        if (!solving && !generating) {
            new Thread(new Runnable() {
                public void run() {
                    solveMaze();
                }
            }).start();
        } else {
            // wenn bereits eins erstellt wird oder eins geloest wird, mach nichts
            return;
        }
    }

    private void solveMaze() {
        solving = true;

        // dfs stack fuer loesungsdfs
        Stack<int[]> stack = new Stack<>();
        boolean[][] visited = new boolean[SIZE][SIZE];

        // start
        int startX = 1;
        int startY = 1;

        // ziel
        int endX = SIZE - 2; // -2, damit da SIZE - 1 die aeussere mauer ist
        int endY = SIZE - 2;

        stack.push(new int[]{startX, startY});
        visited[startX][startY] = true;
        updateCell(startX, startY, Color.MAGENTA);

        // beim loesen muessen keine waende uebersprungen werden, deswegen nur +-1
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; 

        while (!stack.isEmpty()) {
            // "peek" erklaerung in der generatingMaze funktion
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

            // moved checkt, ob sich weiterbewegt werden kann oder nicht
            boolean moved = false;
            if (x == endX && y == endY) {
                // beenden wenn ende erreicht ist
                updateCell(endX, endY, Color.RED);
                break;
            }

            for (int[] direction : directions) {
                int neighborX = x + direction[0];
                int neighborY = y + direction[1];

                // gucken, ob zelle weg ist (white) und im bereich liegt
                if (neighborX >= 0 && neighborY >= 0 && neighborX < SIZE && neighborY < SIZE && visited[neighborX][neighborY] == false && maze[neighborX][neighborY].getBackground().equals(Color.WHITE)) {
                    stack.push(new int[]{neighborX, neighborY});
                    visited[neighborX][neighborY] = true;
                    updateCell(neighborX, neighborY, Color.MAGENTA);
                    moved = true;
                    break;
                }
            }

            if (!moved) {
                stack.pop(); // zurueckgehen wenn keine wege mehr verfuegbar sind (dfs halt)
                updateCell(x, y, Color.GRAY); // ungueltiger weg grau makieren
            }

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        solving = false;
    }

    // aendern der farbe von zelle
    private void updateCell(int x, int y, Color color) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                maze[x][y].setBackground(color);
            }
        });
    }

    public static void main(String[] args) {
        System.out.println("Maze Size: " + SIZE);
        new mazeDFS();
    }
}
