package chess;
import java.awt.*;
import javax.swing.*;

public class GameRecord extends JPanel {
    static GameRecord gameRecord;
    private static final Color backgroundColor = new Color(230, 230, 230);
    static boolean defaultGame;
    
    static TimePanel white;
    static String wName;
    static int wHours, wMinutes, wSeconds, wDelay, wIncrement;

    static MoveRecord moveRecord;
    static JScrollPane moveRecordScrollPane;
    static JPanel moveRecordColumnHeader;

    static TimePanel black;
    static String bName;
    static int bHours, bMinutes, bSeconds, bDelay, bIncrement;

    public GameRecord() {
        this("White", 0, 5, 0, 3, 2, "Black", 0, 5, 0, 3, 2);
    }

    public GameRecord(String wName1, int wHours1, int wMinutes1, int wSeconds1, int wDelay1, int wIncrement1,
            String bName1, int bHours1, int bMinutes1, int bSeconds1, int bDelay1, int bIncrement1) {
        setLayout(new BorderLayout());
        wName = wName1;
        wHours = wHours1;
        wMinutes = wMinutes1;
        wSeconds = wSeconds1;
        wDelay = wDelay1;
        wIncrement = wIncrement1;
        white = new TimePanel (wName, wHours, wMinutes, wSeconds, wDelay);
        add(white, BorderLayout.SOUTH);

        moveRecord = new MoveRecord();
        moveRecord.setPreferredSize(new Dimension(0, 5000));
        moveRecordScrollPane = new JScrollPane(moveRecord);
        moveRecordColumnHeader = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Get width
                int width = getWidth();
                int moveNumberWidth = width/5;
                int moveWidth = (width-moveNumberWidth)/2;
                // Draw labels
                g.setFont(new Font("Sans Serif", Font.BOLD, 15));
                g.drawString("#", moveNumberWidth/2 - 5, 15);
                g.drawString("White", moveNumberWidth, 15);
                g.drawString("Black", moveNumberWidth + moveWidth, 15);
            }
        };
        moveRecordColumnHeader.setPreferredSize(new Dimension(100, 19));
        moveRecordColumnHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        moveRecordColumnHeader.setOpaque(true);
        moveRecordScrollPane.setColumnHeaderView(moveRecordColumnHeader);
        add(moveRecordScrollPane, BorderLayout.CENTER);

        bName = bName1;
        bHours = bHours1;
        bMinutes = bMinutes1;
        bSeconds = bSeconds1;
        bDelay = bDelay1;
        bIncrement = bIncrement1;
        black = new TimePanel (bName, bHours, bMinutes, bSeconds, bDelay);
        add(black, BorderLayout.NORTH);

        setVisible(true);
    }

    class TimePanel extends JPanel {
        // UI
        private JPanel row1;
        private JLabel nameLabel;

        JPanel row2;
        private JLabel timeLabel;
        private JLabel delayTimeLabel;

        private JPanel row3;
        private JLabel timeTypeLabel, delayLabel;
        // Variables
        private String name;
        public CountdownTimer timer, delayTimer;

        public TimePanel(String nameAlias, int hours, int minutes, int seconds, int delaySeconds) {
            name = nameAlias;
            setBackground(backgroundColor);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            // ROW 1
            row1 = new JPanel();
            row1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            row1.setLayout(new GridLayout(0, 2));
            add(row1);

            nameLabel = new JLabel(name);
            nameLabel.setForeground(Color.black);
            nameLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
            row1.add(nameLabel);

            // ROW 2
            row2 = new JPanel();
            row2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            add(row2);

            timeLabel = new JLabel();
            timeLabel.setHorizontalAlignment(JLabel.LEFT);
            timeLabel.setForeground(Color.black);
            timeLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
            row2.add(timeLabel);

            delayTimeLabel = new JLabel();
            delayTimeLabel.setForeground(Color.black);
            delayTimeLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
            row2.add(delayTimeLabel);

            // ROW 3
            row3 = new JPanel();
            add(row3);

            timeTypeLabel = new JLabel();
            timeTypeLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
            row3.add(timeTypeLabel);

            delayLabel = new JLabel("Delay");
            delayLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
            row3.add(delayLabel);

            timer = new CountdownTimer(10, "", "",
            hours, minutes, seconds, 0.0,
            new Runnable() {            // Change Time
                public void run() {
                    timer.changeTime(0, 0, 0, -CountdownTimer.oneCentisecond);
                }
            }, new Runnable() {         // Update Time
                public void run() {
                    if (timer != null) {
                        if (timer.getHours() > 0) {
                            timeLabel.setText(timer.getTime(true, true, false, false));
                            timeTypeLabel.setText(" h/m ");
                        } else if (timer.getMinutes() > 0) {
                            timeLabel.setText(timer.getTime(false, true, true, false));
                            timeTypeLabel.setText(" m/s ");
                        } else {
                            timeLabel.setText(timer.getTime(false, false, true, true));
                            timeTypeLabel.setText(" s/c ");
                        }
                    }
                }
            }, new Runnable() {
                public void run() {     // Close
                    JOptionPane.showMessageDialog(gameRecord, name + " ran out of time.");
                    if (ChessBoard.move) {
                        ChessBoard.startSquare.setPiece(ChessBoard.movePiece);
                        ChessBoard.move = false;
                        ChessBoard.movePiece = null;
                        ChessBoard.startSquare = null;
                        ChessBoard.endSquare = null;
                        ChessBoard.chessBoard.repaint();
                    }
                    ChessGame.endGame();
                }
            })
            {
                public void stop() {
                    super.stop();
                    row2.setBackground(backgroundColor);
                }
            };
            timer.updateTime();

            delayTimer = new CountdownTimer(10, "", "",
            0, 0, delaySeconds, 0.0,
            new Runnable() {            // Change Time
                public void run() {
                    delayTimer.changeTime(0, 0, 0, -CountdownTimer.oneCentisecond);
                }
            }, new Runnable() {         // Update Time
                public void run() {
                    if (delayTimer != null) {
                        delayTimeLabel.setText(delayTimer.getTime(false, false, true, true));
                    }
                }
            }, new Runnable() {
                public void run() {     // Close
                    timer.start();
                }
            })
            {
                public void restart() {
                    super.restart();
                    row2.setBackground(Color.white);
                }
            };
            delayTimer.updateTime();
        }
    }

    class MoveRecord extends JPanel {
        private String[][] moveRecord;
        private int moveCount;
        private String previousMove;

        // [Move number][Move number, White move, Black move, White time, Black time]
        public MoveRecord() {
            moveRecord = new String[1000][5];
            moveCount = 0;
            previousMove = null;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = this.getWidth();
            int moveNumberWidth = width/5;
            int moveWidth = (width-moveNumberWidth)/2;
            g.setFont(new Font("Sans Serif", Font.PLAIN, 12));
            if (moveCount > 0) {   // If a move has been made
                for (int i = 1; i <= moveCount; i++) {
                    int height = i*15;
                    // Move number
                    g.drawString(moveRecord[i][0], moveNumberWidth/2 - 5, height);
                    // White move
                    g.drawString(getDisplayMove(moveRecord[i][1]), moveNumberWidth, height);
                    // Black move
                    if (moveRecord[i][2] != null) {
                        g.drawString(getDisplayMove(moveRecord[i][2]), moveNumberWidth + moveWidth, height);
                    }
                }
            }
        }

        public String getDisplayMove (String fullMove) {
            if (fullMove.charAt(0) == '0') {    // Castling
                return fullMove;
            } else {
                String displayMove = "";
                char pieceType = fullMove.charAt(0);
                if (pieceType != 'P') {                     // piece != pawn
                    displayMove += pieceType;
                }
                if (fullMove.charAt(3) == 'x') {
                    if (pieceType == 'P') {                 // piece = pawn
                        displayMove += fullMove.charAt(1);  // show startSquare's file
                    }
                    displayMove += 'x';
                }
                displayMove += fullMove.substring(4);
                return displayMove;
            }
        }

        public void addMove(Piece movingPiece, Square startSquare, Square endSquare) {
            String move = getStringOfMove(movingPiece, startSquare, endSquare);
            previousMove = move;
            // Add move and time to array
            if (!ChessGame.white) { // Happens after switch turns
                moveCount++;
                moveRecord[moveCount][0] = moveCount + ".";
                moveRecord[moveCount][1] = move;
                moveRecord[moveCount][3] = white.timer.getTime();
            } else {
                moveRecord[moveCount][2] = move;
                moveRecord[moveCount][4] = black.timer.getTime();
            }
            repaint();
        }

        public String getStringOfMove (Piece movePiece, Square startSquare, Square endSquare) {
            String move = "";
            if (ChessGame.kingsideCastling) {
                move += "0-0";
            } else  if (ChessGame.queensideCastling) {
                move += "0-0-0";
            } else {
                // Piece name
                move += movePiece.getName().toUpperCase().charAt(1);
                // Start square
                move += startSquare.getName();
                // Capturing
                if (ChessGame.capturing) {
                    move += 'x';
                } else {
                    move += '-';
                }
                // Endsquare
                move += endSquare.getName();
                // Pawn promoting
                if (ChessGame.promoting) {
                    move += "=" + endSquare.getPiece().getName().toUpperCase().charAt(1);
                }
            }
            // Checking/Checkmating
            if (ChessGame.checkmate) {
                move += "#";
            } else if (ChessGame.doubleChecking) {
                move += "++";
            } else if (ChessGame.checking) {
                move += "+";
            }
            return move;
        }

        public String getPreviousMove() {
            return previousMove;
        }
    }
}