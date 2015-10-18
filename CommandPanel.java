package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CommandPanel extends JPanel {
    static CountdownTimer runningTimer;
    static boolean paused;

    static JPanel playerPanel;
    static JLabel whiteLabel, dashLabel, blackLabel;

    static JPanel commandPanel;
    static JButton offerDraw, resign, pauseOrResume, flipBoard;

    public CommandPanel() {
        runningTimer = null;
        paused = false;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Player Panel
        playerPanel = new JPanel();
        add(playerPanel);

        whiteLabel = new JLabel ("White");
        whiteLabel.setOpaque(true);
        whiteLabel.setBackground(Color.green);
        whiteLabel.setFont(new Font("Serif", Font.BOLD, 20));
        playerPanel.add(whiteLabel);

        dashLabel = new JLabel("-");
        dashLabel.setFont(new Font("Serif", Font.BOLD, 20));
        playerPanel.add(dashLabel);

        blackLabel = new JLabel ("Black");
        blackLabel.setOpaque(true);
        blackLabel.setFont(new Font("Serif", Font.BOLD, 20));
        playerPanel.add(blackLabel);

        // Command Panel
        commandPanel = new JPanel();
        add(commandPanel);

        offerDraw = new JButton("Offer Draw");
        offerDraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String player, opponent;
                if (ChessGame.white) {
                    player = StartScreen.getWhiteName();
                    opponent = StartScreen.getBlackName();
                } else {
                    player = StartScreen.getBlackName();
                    opponent = StartScreen.getWhiteName();
                }
                int answer = JOptionPane.showConfirmDialog(ChessBoard.chessBoard, player + ", do you really want to offer a draw?",
                        "Select an option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.YES_OPTION) {
                    int opponentAnswer = JOptionPane.showConfirmDialog(ChessBoard.chessBoard, 
                            opponent + ", do you accept " + player + "'s draw offer?",
                            "Select an option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (opponentAnswer == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(ChessBoard.chessBoard, "Draw by mutual agreement.\nGame over.");
                        ChessGame.endGame();
                    }
                }
            }
        });
        commandPanel.add(offerDraw);

        resign = new JButton("Resign");
        resign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String player;
                if (ChessGame.white) {
                    player = StartScreen.getWhiteName();
                } else {
                    player = StartScreen.getBlackName();
                }
                int answer = JOptionPane.showConfirmDialog(ChessBoard.chessBoard, player + ", do you really want to resign?",
                        "Select an option", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (answer == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(ChessBoard.chessBoard, player + " resigns.\nGame over.");
                    ChessGame.endGame();
                }
            }
        });
        commandPanel.add(resign);

        pauseOrResume = new JButton("Pause/Resume");
        pauseOrResume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!paused) {
                    if (ChessGame.white && GameRecord.white.timer.isRunning()) {
                        runningTimer = GameRecord.white.timer;
                    } else if (ChessGame.white && GameRecord.white.delayTimer.isRunning()) {
                        runningTimer = GameRecord.white.delayTimer;
                    } else if (!ChessGame.white && GameRecord.black.timer.isRunning()) {
                        runningTimer = GameRecord.black.timer;
                    } else if (!ChessGame.white && GameRecord.black.delayTimer.isRunning()) {
                        runningTimer = GameRecord.black.delayTimer;
                    }
                    if (runningTimer != null) {
                        runningTimer.stop();
                        paused = true;

                        ChessBoard.chessBoard.removeMouseListener(ChessBoard.mouseListener);
                        ChessBoard.chessBoard.removeMouseMotionListener(ChessBoard.mouseMotionListener);
                    }
                } else {
                    runningTimer.restart();
                    paused = false;

                    ChessBoard.chessBoard.addMouseListener(ChessBoard.mouseListener);
                    ChessBoard.chessBoard.addMouseMotionListener(ChessBoard.mouseMotionListener);
                }
            }
        });
        commandPanel.add(pauseOrResume);

        flipBoard = new JButton("Flip Board");
        flipBoard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChessBoard.flipBoard();
            }
        });
        commandPanel.add(flipBoard);
    }
}