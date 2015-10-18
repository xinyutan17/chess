package chess;

public class ChessGame {
    static boolean playing;

    static boolean defaultGame;
    static boolean white;
    static boolean checkmate, stalemate;
    static boolean capturing, checking, doubleChecking;
    static boolean kingsideCastling, queensideCastling;
    static boolean promoting;

    public static void startNewGame() {
        white = true;
        checkmate = stalemate = false;
        capturing = checking = doubleChecking = false;
        kingsideCastling = queensideCastling = false;
        promoting = false;

        ChessBoard.chessBoard.addMouseListener(ChessBoard.mouseListener);
        ChessBoard.chessBoard.addMouseMotionListener(ChessBoard.mouseMotionListener);
        ChessBoard.reset();

        MoveFinder.update();
    }

    public static void endGame() {
        playing = false;
        // Stop timers
        GameRecord.white.timer.stop();
        GameRecord.white.delayTimer.stop();
        GameRecord.black.timer.stop();
        GameRecord.black.delayTimer.stop();
        // Make pieces unable to move
        ChessBoard.chessBoard.removeMouseListener(ChessBoard.mouseListener);
        ChessBoard.chessBoard.removeMouseMotionListener(ChessBoard.mouseMotionListener);
    }
}