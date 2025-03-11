# Labyrinth erstellen & lösen mit DFS (Depth-First Search)

Dieses Java-Projekt implementiert den DFS-Algorithmus (Depth-First Search) zur Erstellung und Lösung von Labyrinthen. Die Labyrinth-Generierung verwendet eine zufallsbasierte Version des DFS-Algorithmus, während für die Lösung der normale DFS-Algorithmus zum Einsatz kommt. 

Beide Prozesse werden durch eine **Swing-Oberfläche** grafisch dargestellt. Um eine flüssige Animation zu gewährleisten, werden **Threads** verwendet, sodass die Labyrinth-Erstellung und -Lösung live angezeigt werden, anstatt dass das fertige Labyrinth sofort erscheint.

Ich habe mein Bestes gegeben, um alle relevanten Schritte im Code ausführlich zu kommentieren.

## Beispiele

### Labyrinth-Erstellung
![Labyrinth Erstellung](https://github.com/user-attachments/assets/9851745e-95dc-4a29-908d-e2d0d2cc7900)

### Labyrinth-Lösung
![Labyrinth Lösung](https://github.com/user-attachments/assets/e70b3a84-729d-4c51-a9aa-e82bc2511281)

## Installation und Ausführung

### Voraussetzungen:
- **Java 8+**

### Optional:
- Eine **IDE** für eine einfachere Modifikation des Codes.

### Schritte zur Installation und Ausführung:

1. **Projekt klonen oder als ZIP herunterladen**.
2. Den Ordner im Terminal öffnen.
3. Im Terminal den Code kompilieren und ausführen:

   ```bash
   javac mazeDFS.java
   java mazeDFS


Alternativ in einer IDE öffnen und dann die Mainmethode auführen.

Der Button "Generate" erstellt ein neues Labyrinth, der Button "Solve" sucht den kürzesten Weg. Während der Erstellung und während der Lösung kann kein weiterer Button verwendet werden.

## Code-Überblick

Die wichtigsten Komponenten des Codes:
generateMaze() → Erstellt ein zufälliges Labyrinth mit DFS
solveMaze() → Findet einen Weg durch das Labyrinth mit DFS
updateCell(x, y, Color color) → Aktualisiert die Farbe einer Zelle


