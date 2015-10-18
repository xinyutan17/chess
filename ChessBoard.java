package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessBoard extends JPanel {
    static ChessBoard chessBoard;
    static MouseListener mouseListener;
    static MouseMotionListener mouseMotionListener;

    static Square[][] squaresBoard;
    static Square a8, b8, c8, d8, e8, f8, g8, h8;
    static Square a7, b7, c7, d7, e7, f7, g7, h7;
    static Square a6, b6, c6, d6, e6, f6, g6, h6;
    static Square a5, b5, c5, d5, e5, f5, g5, h5;
    static Square a4, b4, c4, d4, e4, f4, g4, h4;
    static Square a3, b3, c3, d3, e3, f3, g3, h3;
    static Square a2, b2, c2, d2, e2, f2, g2, h2;
    static Square a1, b1, c1, d1, e1, f1, g1, h1;
    
    static boolean move;
    static int mouseX, mouseY;
    static boolean whiteBoardColor;
    static Piece movePiece;
    static Square startSquare, endSquare;
    static Square currentSquare;

    static int width, height;
    static double borderWidth, borderHeight, squareWidth, squareHeight, imageWidth, imageHeight;

    static private JPopupMenu description;
    static private JMenuItem squareDescription, highlightAttackers, pieceDescription, highlightMoves;

    static final int highlightThickness = 3;
    
    public ChessBoard() {
        setBackground(Color.lightGray);
        createSquaresBoard();
        createMouseListeners();
        createPopupMenu();
        reset();
    }

    private static void createSquaresBoard() {
        squaresBoard = new Square[9][9];
        a8 = new Square("a8"); squaresBoard[1][1] = a8;
        b8 = new Square("b8"); squaresBoard[1][2] = b8;
        c8 = new Square("c8"); squaresBoard[1][3] = c8;
        d8 = new Square("d8"); squaresBoard[1][4] = d8;
        e8 = new Square("e8"); squaresBoard[1][5] = e8;
        f8 = new Square("f8"); squaresBoard[1][6] = f8;
        g8 = new Square("g8"); squaresBoard[1][7] = g8;
        h8 = new Square("h8"); squaresBoard[1][8] = h8;

        a7 = new Square("a7"); squaresBoard[2][1] = a7;
        b7 = new Square("b7"); squaresBoard[2][2] = b7;
        c7 = new Square("c7"); squaresBoard[2][3] = c7;
        d7 = new Square("d7"); squaresBoard[2][4] = d7;
        e7 = new Square("e7"); squaresBoard[2][5] = e7;
        f7 = new Square("f7"); squaresBoard[2][6] = f7;
        g7 = new Square("g7"); squaresBoard[2][7] = g7;
        h7 = new Square("h7"); squaresBoard[2][8] = h7;

        a6 = new Square("a6"); squaresBoard[3][1] = a6;
        b6 = new Square("b6"); squaresBoard[3][2] = b6;
        c6 = new Square("c6"); squaresBoard[3][3] = c6;
        d6 = new Square("d6"); squaresBoard[3][4] = d6;
        e6 = new Square("e6"); squaresBoard[3][5] = e6;
        f6 = new Square("f6"); squaresBoard[3][6] = f6;
        g6 = new Square("g6"); squaresBoard[3][7] = g6;
        h6 = new Square("h6"); squaresBoard[3][8] = h6;

        a5 = new Square("a5"); squaresBoard[4][1] = a5;
        b5 = new Square("b5"); squaresBoard[4][2] = b5;
        c5 = new Square("c5"); squaresBoard[4][3] = c5;
        d5 = new Square("d5"); squaresBoard[4][4] = d5;
        e5 = new Square("e5"); squaresBoard[4][5] = e5;
        f5 = new Square("f5"); squaresBoard[4][6] = f5;
        g5 = new Square("g5"); squaresBoard[4][7] = g5;
        h5 = new Square("h5"); squaresBoard[4][8] = h5;

        a4 = new Square("a4"); squaresBoard[5][1] = a4;
        b4 = new Square("b4"); squaresBoard[5][2] = b4;
        c4 = new Square("c4"); squaresBoard[5][3] = c4;
        d4 = new Square("d4"); squaresBoard[5][4] = d4;
        e4 = new Square("e4"); squaresBoard[5][5] = e4;
        f4 = new Square("f4"); squaresBoard[5][6] = f4;
        g4 = new Square("g4"); squaresBoard[5][7] = g4;
        h4 = new Square("h4"); squaresBoard[5][8] = h4;

        a3 = new Square("a3"); squaresBoard[6][1] = a3;
        b3 = new Square("b3"); squaresBoard[6][2] = b3;
        c3 = new Square("c3"); squaresBoard[6][3] = c3;
        d3 = new Square("d3"); squaresBoard[6][4] = d3;
        e3 = new Square("e3"); squaresBoard[6][5] = e3;
        f3 = new Square("f3"); squaresBoard[6][6] = f3;
        g3 = new Square("g3"); squaresBoard[6][7] = g3;
        h3 = new Square("h3"); squaresBoard[6][8] = h3;

        a2 = new Square("a2"); squaresBoard[7][1] = a2;
        b2 = new Square("b2"); squaresBoard[7][2] = b2;
        c2 = new Square("c2"); squaresBoard[7][3] = c2;
        d2 = new Square("d2"); squaresBoard[7][4] = d2;
        e2 = new Square("e2"); squaresBoard[7][5] = e2;
        f2 = new Square("f2"); squaresBoard[7][6] = f2;
        g2 = new Square("g2"); squaresBoard[7][7] = g2;
        h2 = new Square("h2"); squaresBoard[7][8] = h2;

        a1 = new Square("a1"); squaresBoard[8][1] = a1;
        b1 = new Square("b1"); squaresBoard[8][2] = b1;
        c1 = new Square("c1"); squaresBoard[8][3] = c1;
        d1 = new Square("d1"); squaresBoard[8][4] = d1;
        e1 = new Square("e1"); squaresBoard[8][5] = e1;
        f1 = new Square("f1"); squaresBoard[8][6] = f1;
        g1 = new Square("g1"); squaresBoard[8][7] = g1;
        h1 = new Square("h1"); squaresBoard[8][8] = h1;
    }

    private static void createMouseListeners() {
        mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                if (mouseIsInChessBoard()) {
                    currentSquare = findSquareOfMouse();
                    if (e.isMetaDown()) {
                        squareDescription.setText("Square: " + currentSquare.getName());
                        if (currentSquare.hasPiece()) {
                            Piece piece = currentSquare.getPiece();
                            pieceDescription.setText("Piece: " + piece.getName() + " [" + piece.getValue() + "]");
                            highlightMoves.setEnabled(true);
                        } else {
                            pieceDescription.setText("Piece: none");
                            highlightMoves.setEnabled(false);
                        }
                        description.show(chessBoard, mouseX, mouseY);
                    } else {
                        if(currentSquare.hasPiece()) {
                            startSquare = currentSquare;
                            movePiece = currentSquare.getPiece();
                            currentSquare.setPiece(null);
                            move = true;
                        }
                    }
                } else {    // the mouse is moving a piece from captured panel, or it isn't anywhere

                }
            }

            public void mouseReleased(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                if (move) {
                    if (mouseIsInChessBoard()) {
                        endSquare = findSquareOfMouse();
                        if (MoveFinder.isValidMove(movePiece, endSquare)) {
                            move = false;
                            // Stop current player's time
                            if (ChessGame.white) {
                                GameRecord.white.timer.stop();
                                if (ChessGame.playing) {
                                    GameRecord.white.timer.changeTime(0, 0, GameRecord.wIncrement, 0);
                                    GameRecord.white.timer.updateTime();
                                }
                                GameRecord.white.delayTimer.reset();
                            } else {
                                GameRecord.black.timer.stop();
                                if (ChessGame.playing) {
                                    GameRecord.black.timer.changeTime(0, 0, GameRecord.bIncrement, 0);
                                    GameRecord.black.timer.updateTime();
                                }
                                GameRecord.black.delayTimer.reset();
                            }
                            // After white and black both have made a move, playing = true
                            if (!ChessGame.white) {
                                ChessGame.playing = true;
                            }
                            // Checking for 'capturing' and 'castling'
                            if (endSquare.hasPiece()) {
                                ChessGame.capturing = true;
                            }
                            if (movePiece == ChessSet.wk1 && startSquare == ChessBoard.e1) {
                                if (endSquare == ChessBoard.g1) {
                                    ChessGame.kingsideCastling = true;
                                } else if (endSquare == ChessBoard.c1) {    // Queenside castling
                                    ChessGame.queensideCastling = true;
                                }
                            } else if (movePiece == ChessSet.bk1 && startSquare == ChessBoard.e8) {
                                if (endSquare == ChessBoard.g8) {           // Kingside castling
                                    ChessGame.kingsideCastling = true;
                                } else if (endSquare == ChessBoard.c8) {    // Queenside castling
                                    ChessGame.queensideCastling = true;
                                }
                            }
                            // Unhighlight squares, then highlight the start and end squares
                            unhighlightSquares();
                            startSquare.setHighlightColor(Color.black);
                            endSquare.setHighlightColor(Color.black);
                            // Make move
                            movePiece(movePiece, startSquare, endSquare);
                            if (movePiece.getType() == MoveFinder.PAWN) {
                                // Promote pawn
                                if (movePiece.isWhite() && endSquare.getRow() == 1) {
                                    new PromotePawnDialog(Main.main, 0, endSquare);
                                } else if (!movePiece.isWhite() && endSquare.getRow() == 8) {
                                    new PromotePawnDialog(Main.main, 1, endSquare);
                                } else {
                                    if (endSquare.isEnPassantMove()) {
                                        Square pawnTakenSquare = squaresBoard[startSquare.getRow()][endSquare.getCol()];
                                        pawnTakenSquare.getPiece().setInPlay(false);
                                        ChessGame.capturing = true;
                                    }
                                }
                            }
                            chessBoard.repaint();
                            // Switch turns and update
                            ChessGame.white = !ChessGame.white;
                            if (ChessGame.white) {
                                CommandPanel.whiteLabel.setBackground(Color.green);
                                CommandPanel.blackLabel.setBackground(null);
                            } else {
                                CommandPanel.blackLabel.setBackground(Color.green);
                                CommandPanel.whiteLabel.setBackground(null);
                            }
                            MoveFinder.update();
                            // Check for checkmate and stalemate
                            int color;
                            if (ChessGame.white) {
                                color = MoveFinder.WHITE;
                            } else {
                                color = MoveFinder.BLACK;
                            }
                            boolean hasMove = false;
                            Piece[][][] pieces = ChessSet.chessSet;
                            for (int type = 0; type < pieces[color].length; type++) {
                                for (int number = 1; number < pieces[color][type].length; number++) {
                                    Piece piece = pieces[color][type][number];
                                    if (piece!= null) {
                                        if (piece.isInPlay()) {
                                            Square[] moves = piece.getMoves();
                                            if (moves != null) {
                                                for (int move = 0; move < moves.length; move++) {
                                                    if (moves[move] != null) {
                                                        hasMove = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            String player;
                            if (ChessGame.white) {
                                player = "White";
                            } else {
                                player = "Black";
                            }
                            if (!hasMove && ChessGame.checking) {
                                ChessGame.checkmate = true;
                                JOptionPane.showMessageDialog(chessBoard, player + " checkmated.\nGame over.");
                                ChessGame.endGame();
                            } else if (!hasMove && !ChessGame.checking) {
                                ChessGame.stalemate = true;
                                JOptionPane.showMessageDialog(chessBoard, player + " stalemated.\nGame over.");
                                ChessGame.endGame();
                            } else if (ChessGame.doubleChecking) {
                                JOptionPane.showMessageDialog(chessBoard, player + " double checked.");
                            } else if (ChessGame.checking) {
                                JOptionPane.showMessageDialog(chessBoard, player + " checked.");
                            }
                            if (ChessGame.checkmate || ChessGame.stalemate) {
                                if (ChessGame.white) {
                                    CommandPanel.whiteLabel.setBackground(null);
                                } else {
                                    CommandPanel.blackLabel.setBackground(null);
                                }
                            }
                            GameRecord.moveRecord.addMove(movePiece, startSquare, endSquare);
                            ChessGame.capturing = false;
                            ChessGame.kingsideCastling = ChessGame.queensideCastling = false;
                            ChessGame.promoting = false;
                            if (Main.educationalQuestionDifficulty != -1) {
                                QuestionDialog question = new QuestionDialog(null, Main.educationalQuestionDifficulty);
                                if (question.answerIsCorrect()) {
                                    if (!ChessGame.white) {     // After switch turns, so it's opposite
                                        GameRecord.white.timer.changeTime(0, StartScreen.getWhiteHours(),
                                                StartScreen.getWhiteMinutes(), StartScreen.getWhiteSeconds());
                                        GameRecord.white.timer.updateTime();
                                    } else {
                                        GameRecord.black.timer.changeTime(0, StartScreen.getBlackHours(),
                                                StartScreen.getBlackMinutes(), StartScreen.getBlackSeconds());
                                        GameRecord.black.timer.updateTime();
                                    }
                                }
                                question.dispose();
                            }
                            if (ChessGame.playing) {
                                // Start opponent's timer
                                if (ChessGame.white) {
                                    GameRecord.white.delayTimer.restart();
                                } else {
                                    GameRecord.black.delayTimer.restart();
                                }
                            }
                            // Flip board
                            if (Main.flipBoard) {
                                whiteBoardColor = ChessGame.white;
                            }
                        } else {
                            // Illegal move
                            if (endSquare != startSquare) {
                                if (movePiece.isWhite() != ChessGame.white) {
                                    System.out.println("Illegal move: wrong player");
                                } else {
                                    System.out.println("Illegal move: " + GameRecord.moveRecord.getDisplayMove(
                                            GameRecord.moveRecord.getStringOfMove(movePiece, startSquare, endSquare)));
                                }
                            }
                            startSquare.setPiece(movePiece);
                        }
                    } else {
                        startSquare.setPiece(movePiece);
                    }
                }
                move = false;
                movePiece = null;
                startSquare = null;
                endSquare = null;

                chessBoard.repaint();
            }
        };

        mouseMotionListener = new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (move) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    chessBoard.repaint();
                }
            }
        };
    }

    private static void createPopupMenu() {
        description = new JPopupMenu();

        squareDescription = new JMenuItem();
        squareDescription.setEnabled(false);    // does nothing
        description.add(squareDescription);

        highlightAttackers = new JMenuItem("Highlight Attackers");
        highlightAttackers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unhighlightSquares();
                // Highlight attackers in red
                Piece[][][] attackers = ChessSet.chessSet;
                for (int color = 0; color < attackers.length; color++) {
                    for (int type = 0; type < attackers[color].length; type++) {
                        for (int number = 1; number < attackers[color][type].length; number++) {
                            Piece attacker = attackers[color][type][number];
                            if (attacker != null) {
                                Square[] attackedSquares = attacker.getAttackedSquares();
                                for (int i = 0; i < attackedSquares.length; i++) {
                                    if (attackedSquares[i] == currentSquare) {
                                        attacker.getSquare().setHighlightColor(Color.red);
                                    }
                                }
                            }
                        }
                    }
                }
                chessBoard.repaint();
            }
        });
        description.add(highlightAttackers);

        description.addSeparator();

        pieceDescription = new JMenuItem();
        pieceDescription.setEnabled(false); // does nothing
        description.add(pieceDescription);

        highlightMoves = new JMenuItem("Highlight Moves");
        highlightMoves.setEnabled(false);
        description.add(highlightMoves);
        highlightMoves.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentSquare.hasPiece()) {
                    Square[] moves = currentSquare.getPiece().getMoves();
                    if (moves != null) {    // Highlight moves in blue
                        unhighlightSquares();
                        for (int i = 0; i < moves.length; i++) {
                            if (moves[i] != null) {
                                moves[i].setHighlightColor(Color.blue);
                            }
                        }
                        chessBoard.repaint();
                    }
                }
            }
        });


    }

    public static void reset() {
        resetVariables();
        resetPieces();
    }

    private static void resetVariables() {
        move = false;
        mouseX = mouseY = 0;
        whiteBoardColor = true;
        movePiece = null;
        startSquare = endSquare = currentSquare = null;
    }

    private static void resetPieces() {
        whiteBoardColor = true;
        // a8-h8 = Black pieces
        a8.setPiece(ChessSet.br1);
        b8.setPiece(ChessSet.bn1);
        c8.setPiece(ChessSet.bb1);
        d8.setPiece(ChessSet.bq1);
        e8.setPiece(ChessSet.bk1);
        f8.setPiece(ChessSet.bb2);
        g8.setPiece(ChessSet.bn2);
        h8.setPiece(ChessSet.br2);
        // a7-h7 = Black pawns
        a7.setPiece(ChessSet.bp1);
        b7.setPiece(ChessSet.bp2);
        c7.setPiece(ChessSet.bp3);
        d7.setPiece(ChessSet.bp4);
        e7.setPiece(ChessSet.bp5);
        f7.setPiece(ChessSet.bp6);
        g7.setPiece(ChessSet.bp7);
        h7.setPiece(ChessSet.bp8);
        // a2-h2 = White pawns
        a2.setPiece(ChessSet.wp1);
        b2.setPiece(ChessSet.wp2);
        c2.setPiece(ChessSet.wp3);
        d2.setPiece(ChessSet.wp4);
        e2.setPiece(ChessSet.wp5);
        f2.setPiece(ChessSet.wp6);
        g2.setPiece(ChessSet.wp7);
        h2.setPiece(ChessSet.wp8);
        // a1-h1 = White pieces
        a1.setPiece(ChessSet.wr1);
        b1.setPiece(ChessSet.wn1);
        c1.setPiece(ChessSet.wb1);
        d1.setPiece(ChessSet.wq1);
        e1.setPiece(ChessSet.wk1);
        f1.setPiece(ChessSet.wb2);
        g1.setPiece(ChessSet.wn2);
        h1.setPiece(ChessSet.wr2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        resetWidthAndHeight();
        // Top captured pieces
        int topImages;
        if (whiteBoardColor) {
            topImages = ChessSet.BLACK;
        } else {
            topImages = ChessSet.WHITE;
        }
        for (int i = 0; i < 5; i++) {
            int startWidth = (int)(borderWidth + i*imageWidth + (imageWidth-squareWidth)/2);
            int endWidth = (int)(startWidth + squareWidth);
            int startHeight = (int)((imageHeight-squareHeight)/2);
            int endHeight = (int)(startHeight + squareHeight);
            g.drawImage(ChessSet.imageSet[topImages][i], startWidth, startHeight, endWidth, endHeight, 0, 0, 100, 100, null);
        }
        // Draw border
        g.setColor(Color.lightGray);
        g.fillRect(0, (int)(imageHeight), width, (int)(height - 2*imageHeight));
        g.setColor(new Color(139, 101, 8));
        g.fillRect((int)(borderWidth), (int)(imageHeight + borderHeight),
                (int)(width - 2*borderWidth), (int)(height - 2*imageHeight - 2*borderHeight));

        // Draw file and rank headers
        g.setColor(Color.black);
        g.setFont(new Font("Sans Serif", Font.BOLD, (int)(borderWidth/2)));
        final int textSize = 5;
        String file;
        for (int x = 0; x < 8; x++) {
            if (whiteBoardColor) {
                file = (char)('a' + x) + "";
            } else {
                file = (char)('h' - x) + "";
            }
            g.drawString(file, (int)(borderWidth + x*squareWidth + squareWidth/2 - textSize),
                    (int)(imageHeight + borderHeight/2 + textSize));
            g.drawString(file, (int)(borderWidth + x*squareWidth + squareWidth/2 - textSize),
                    (int)(height - imageHeight - borderHeight/2 + textSize));
        }
        String rank;
        for (int x = 0; x < 8; x++) {
            if (whiteBoardColor) {
                rank = (char)('8' - x) + "";
            } else {
                rank = (char)('1' + x) + "";
            }
            g.drawString(rank, (int)(borderWidth/2 - textSize),
                    (int)(imageHeight + borderHeight + x*squareHeight + squareHeight/2 + textSize));
            g.drawString(rank, (int)(width - borderWidth/2 - textSize),
                    (int)(imageHeight + borderHeight + x*squareHeight + squareHeight/2 + textSize));
        }
        // Chess Board
        for (int row = 1; row < squaresBoard.length; row++) {
            for (int col = 1; col < squaresBoard[row].length; col++) {
                Square current;
                if (whiteBoardColor) {
                    current = squaresBoard[row][col];
                } else {
                    current = squaresBoard[9-row][9-col];
                }
                int startWidth = (int)(borderWidth + (col-1)*squareWidth);
                int startHeight = (int)(borderHeight + row*squareHeight);
                // Highlight square with highlight color
                g.setColor(current.getHighlightColor());
                g.fillRect(startWidth, startHeight, (int)(squareWidth), (int)(squareHeight));
                // Fill square with default color
                g.setColor(current.getDefaultColor());
                g.fillRect(startWidth + highlightThickness, startHeight + highlightThickness,
                        (int)(squareWidth) - 2*highlightThickness, (int)(squareHeight) - 2*highlightThickness);
                // Draw piece
                if (current.hasPiece()) {
                    g.drawImage(current.getPiece().getImage(), startWidth, startHeight, (int)(startWidth + squareWidth),
                            (int)(startHeight + squareHeight), 0, 0, 100, 100, null);
                }
            }
        }

        // Moving piece
        if (move) {
            g.drawImage(movePiece.getImage(), (int)(mouseX - squareWidth/2), (int)(mouseY - squareHeight/2),
                    (int)(mouseX + squareWidth/2), (int)(mouseY + squareHeight/2), 0, 0, 100, 100, null);
        }

        // Bottom captured pieces
        int bottomImages;
        if (whiteBoardColor) {
            bottomImages = ChessSet.WHITE;
        } else {
            bottomImages = ChessSet.BLACK;
        }
        for (int i = 0; i < 5; i++) {
            int startWidth = (int)(borderWidth + i*imageWidth + (imageWidth-squareWidth)/2);
            int endWidth = (int)(startWidth + squareWidth);
            int startHeight = (int)(height - (imageHeight-squareHeight)/2 - squareHeight);
            int endHeight = (int)(startHeight + squareHeight);
            g.drawImage(ChessSet.imageSet[bottomImages][i], startWidth, startHeight, endWidth, endHeight, 0, 0, 100, 100, null);
        }
    }

    private static void resetWidthAndHeight() {
        width = chessBoard.getWidth();
        height = chessBoard.getHeight();
        borderWidth = 30;
        borderHeight = 30;
        squareWidth = (width - 2*borderWidth)/8.0;
        squareHeight = (height - 2*borderHeight)/10.0;
        imageWidth = (width - 2*borderWidth)/5;
        imageHeight = squareHeight;
    }

    private static boolean mouseIsInChessBoard() {
        if (borderWidth < mouseX && mouseX < (width-borderWidth) &&
                (imageHeight + borderHeight) < mouseY && mouseY < (height - (imageHeight + borderHeight))) {
            return true;
        } else {
            return false;
        }
    }

    private static Square findSquareOfMouse() {
        int x = mouseX;
        int y = mouseY;
        x -= borderWidth;
        y -= (imageHeight + borderHeight);
        if (whiteBoardColor) {
            return squaresBoard[(int)(y/squareHeight) + 1][(int)(x/squareWidth) + 1];
        } else {
            return squaresBoard[9 - ((int)(y/squareHeight) + 1)][9 - ((int)(x/squareWidth) + 1)];
        }
    }

    public static void flipBoard() {
        whiteBoardColor = !whiteBoardColor;
        chessBoard.repaint();
    }

    private static void movePiece(Piece piece, Square start, Square end) {
        setRookAfterCastling();
        // Set the new position of the piece
        if (end.hasPiece()) {
            end.getPiece().setInPlay(false);
        }
        end.setPiece(piece);
        piece.setMoved();
        start.setPiece(null);
    }

    private static void setRookAfterCastling() {
        if (movePiece == ChessSet.wk1 && !ChessSet.wk1.hasMoved()) {
            if (endSquare == c1) {
                d1.setPiece(ChessSet.wr1);
                ChessSet.wr1.setMoved();
                a1.setPiece(null);
            } else if (endSquare == g1) {
                f1.setPiece(ChessSet.wr2);
                ChessSet.wr2.setMoved();
                h1.setPiece(null);
            }
        } else if (movePiece == ChessSet.bk1 && !ChessSet.bk1.hasMoved()) {
            if (endSquare == c8) {
                d8.setPiece(ChessSet.br1);
                ChessSet.br1.setMoved();
                a8.setPiece(null);
            } else if (endSquare == g8) {
                f8.setPiece(ChessSet.br2);
                ChessSet.br2.setMoved();
                h8.setPiece(null);
            }
        }
    }

    private static void unhighlightSquares() {
        for (int i = 1; i < squaresBoard.length; i++) {
            for (int j = 1; j < squaresBoard[i].length; j++) {
                Square square = squaresBoard[i][j];
                square.setHighlightColor(square.getDefaultColor());
            }
        }
    }
}