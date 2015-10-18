package chess;

public class MoveFinder {
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static final int QUEEN = 0;
    public static final int ROOK = 1;
    public static final int BISHOP = 2;
    public static final int KNIGHT = 3;
    public static final int PAWN = 4;
    public static final int KING = 5;

    public static final int MOVES = 28;    // Total number of moves per piece

    private static boolean white;

    private static Square[][] squaresBoard = ChessBoard.squaresBoard;
    private static Piece[][][] chessSet = ChessSet.chessSet;

    private static Square[][] hiddenAttackedSquares;
    private static int wHiddenCounter, bHiddenCounter;

    public static boolean isValidMove(Piece movePiece, Square endSquare) {
        if (movePiece.isWhite() == white) {
            Square[] moves = movePiece.getMoves();
            for (int i = 0; i < moves.length; i++) {
                if (moves[i] == endSquare) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void update() {
        resetVariables();
        updatePieces();
        addEnPassant();
        modifyKingMoves(ChessSet.wk1);
        modifyKingMoves(ChessSet.bk1);
        filterPinnedMoves();
        filterCheckedMoves();
    }

    // Resets ChessGame booleans, king squares, squares, and pieces
    private static void resetVariables() {
        // Chess game
        white = ChessGame.white;
        ChessGame.checking = ChessGame.doubleChecking = false;
        // Squares
        for (int row = 1; row < squaresBoard.length; row++) {
            for (int col = 1; col < squaresBoard[row].length; col++) {
                Square square = squaresBoard[row][col];
                square.setAttackedByWhite(false);
                square.setAttackedByBlack(false);
                square.setEnPassantMove(false);
            }
        }
        // Pieces
        for (int color = 0; color < chessSet.length; color++) {
            for (int type = 0; type < chessSet[color].length; type++) {
                for (int number = 1; number < chessSet[color][type].length; number++) {
                    Piece piece = chessSet[color][type][number];
                    if (piece != null) {
                        piece.setPinner(null);
                        piece.setPinned(false);
                        piece.setPinning(false);
                        piece.setChecking(false);
                        piece.resetCounters();
                        piece.setMoves(new Square[28]);
                        piece.setAttackedSquares(new Square[28]);
                    }
                }
            }
        }
        // Hidden attacked squares
        hiddenAttackedSquares = new Square[2][8];
        wHiddenCounter = bHiddenCounter = 0;
    }

    // Updates each piece's moves, attackedSquares, checking, and three pin variables
    // May update some square's attackedByWhite or attackedByBlack
    // May update ChessGame.checking
    // May add hiddenAttackedSquares to the Square[] hiddenAttackedSquares
    private static void updatePieces() {
        for (int color = 0; color < chessSet.length; color++) {
            for (int type = 0; type < chessSet[color].length; type++) {
                for (int number = 1; number < chessSet[color][type].length; number++) {
                    Piece piece = chessSet[color][type][number];
                    if (piece != null) {
                        if (piece.isInPlay()) {
                            updatePiece(piece);
                        }
                    }
                }
            }
        }
    }

    // See updatePieces()
    private static void updatePiece(Piece piece) {
        int type = piece.getType();
        switch (type) {
            case QUEEN:     updateQueen(piece);     break;
            case ROOK:      updateRook(piece);      break;
            case BISHOP:    updateBishop(piece);    break;
            case KNIGHT:    updateKnight(piece);    break;
            case PAWN:      updatePawn(piece);      break;
            case KING:      updateKing(piece);      break;
            default:
                System.out.println("Error: MoveFinder.updatePiece()");
        }
    }

    // See updatePieces()
    private static void updateQueen(Piece queen) {
        updateRook(queen);
        updateBishop(queen);
    }

    // See updatePieces()
    private static void updateRook(Piece rook) {
        Square square = rook.getSquare();
        processMove(rook, findSquaresInDirection(square,  1, 0));  // down
        processMove(rook, findSquaresInDirection(square, -1, 0));  // up
        processMove(rook, findSquaresInDirection(square, 0,  1));  // right
        processMove(rook, findSquaresInDirection(square, 0, -1));  // left
    }

    // See updatePieces()
    private static void updateBishop(Piece bishop) {
        Square square = bishop.getSquare();
        processMove(bishop, findSquaresInDirection(square,  1,  1));  // down-right
        processMove(bishop, findSquaresInDirection(square,  1, -1));  // down-left
        processMove(bishop, findSquaresInDirection(square, -1,  1));  // up-right
        processMove(bishop, findSquaresInDirection(square, -1, -1));  // up-left
    }

    // See updatePieces()
    private static void updateKnight(Piece knight) {
        processMove(knight,  1,  2); // down-right
        processMove(knight,  1, -2); // down-left
        processMove(knight, -1,  2); // up-right
        processMove(knight, -1, -2); // up-left
        
        processMove(knight,  2,  1); // down-right
        processMove(knight,  2, -1); // down-left
        processMove(knight, -2,  1); // up-right
        processMove(knight, -2, -1); // up-left
    }

    // See updatePieces()
    private static void updatePawn(Piece pawn) {
        int direction;
        if (pawn.isWhite()) {
            direction = -1;
        } else {
            direction = 1;
        }
        // Left
        Square left = getSquare(pawn, direction, -1);
        if (left != null) {
            if (left.hasPiece()) {
                processMove(pawn, left);
            } else {
                // Attacked square
                pawn.addAttackedSquare(left);
                if(pawn.isWhite()) {
                    left.setAttackedByWhite(true);
                } else {
                    left.setAttackedByBlack(true);
                }
            }
        }
        // Center
        Square center = getSquare(pawn, direction, 0);
        if (center != null) {
            if (!center.hasPiece()) {
                pawn.addMove(center);
            }
        }
        // Right
        Square right = getSquare(pawn, direction, 1);
        if (right != null) {
            if (right.hasPiece()) {
                processMove(pawn, right);
            } else {
                // Attacked square
                pawn.addAttackedSquare(right);
                if(pawn.isWhite()) {
                    right.setAttackedByWhite(true);
                } else {
                    right.setAttackedByBlack(true);
                }
            }
        }
        // Two squares on first move
        if (!pawn.hasMoved() && !center.hasPiece()) {
            Square doubleMove = getSquare(pawn, 2*direction, 0);
            if (doubleMove != null) {
                if (!doubleMove.hasPiece()) {
                    pawn.addMove(doubleMove);
                }
            }
        }
    }

    // See updatePieces()
    private static void updateKing(Piece piece) {
        processMove(piece, -1, -1); // up-left
        processMove(piece, -1,  0); // up-center
        processMove(piece, -1,  1); // up-right

        processMove(piece,  0, -1); // center-left
        processMove(piece,  0,  1); // center-right

        processMove(piece,  1, -1); // down-left
        processMove(piece,  1,  0); // down-center
        processMove(piece,  1,  1); // down-right
    }

    // Process moves of queens, rooks, and bishops
    private static void processMove(Piece piece, Square[] moves) {
        if (moves != null) {
            boolean isWhite = piece.isWhite();
            // Update piece.moves, piece.attackedSquares, and square.attackedByWhite/Black
            boolean moreMoves = true;
            boolean moreAttackedSquares = true;
            for (int i = 0; i < moves.length; i++) {
                Square current = moves[i];
                if (current != null) {
                    if (moreAttackedSquares) {
                        piece.addAttackedSquare(current);
                        if(isWhite) {
                            current.setAttackedByWhite(true);
                        } else {
                            current.setAttackedByBlack(true);
                        }
                    }
                    if (moreMoves) {
                        if (current.hasPiece()) {
                            if (current.getPiece().isWhite() != isWhite) {
                                piece.addMove(current);
                            }
                            moreMoves = false;
                            moreAttackedSquares = false;
                        } else {
                            piece.addMove(current);
                        }
                    }
                } else {
                    break;
                }
            }

            // Update piece.checking and ChessGame.checking
            // Update MoveFinder.hiddenAttackedSquares
            // Update piece.pinned, piece.pinner, piece.pinning
            Square kingSquare = getOpposingKingSquare(isWhite);
            int kingIndex = -1;
            int pieceCount = 0;
            Piece pinned = null;
            for (int i = 0; i < moves.length; i++) {
                Square current = moves[i];
                if (current == kingSquare) {
                    kingIndex = i;
                    break;
                } else if (current != null && current.hasPiece()) {
                    pieceCount++;
                    if (current.getPiece().isWhite() != isWhite) {
                        pinned = current.getPiece();
                    }
                }
            }
            if(kingIndex != -1) {
                if (pieceCount == 0) {
                    // Checking
                    piece.setChecking(true);
                    ChessGame.checking = true;
                    // HiddenAttackedSquare
                    int hiddenAttackedSquareIndex = kingIndex + 1;
                    if (hiddenAttackedSquareIndex < moves.length) {
                        Square hiddenAttackedSquare = moves[hiddenAttackedSquareIndex];
                        if (hiddenAttackedSquare != null) {
                            addHiddenAttackedSquare(!isWhite, hiddenAttackedSquare);
                        }
                    }
                } else if (pieceCount == 1 && pinned != null) {
                    // Pinning
                    piece.setPinning(true);
                    pinned.setPinned(true);
                    pinned.setPinner(piece);

                }
            }
        }
    }

    // Process moves of knights, pawns, and kings
    // [rowMod > 0 : down]   [rowMod < 0: up]   [colMod > 0 : right]   [colMod < 0: left]
    private static void processMove(Piece piece, int rowMod, int colMod) {
        processMove(piece, getSquare(piece, rowMod, colMod));
    }

    private static void processMove(Piece piece, Square endSquare) {
        if (endSquare != null) {
            boolean isWhite = piece.isWhite();
            // Attacked square
            piece.addAttackedSquare(endSquare);
            if(isWhite) {
                endSquare.setAttackedByWhite(true);
            } else {
                endSquare.setAttackedByBlack(true);
            }
            // Move
            if (endSquare.hasPiece()) {
                Piece currentPiece = endSquare.getPiece();
                if (currentPiece.isWhite() != isWhite) {
                    piece.addMove(endSquare);
                    // Checking
                    if ((isWhite && currentPiece == ChessSet.bk1)
                            || (!isWhite && currentPiece == ChessSet.wk1)) {
                        piece.setChecking(true);
                        ChessGame.checking = true;
                    }
                }
            } else {
                piece.addMove(endSquare);
            }
        }
    }

    private static Square getSquare(Piece piece, int rowMod, int colMod) {
        int row = piece.getSquare().getRow() + rowMod;
        int col = piece.getSquare().getCol() + colMod;
        if (isInBounds(row, col)) {
            return squaresBoard[row][col];
        } else {
            return null;
        }
    }

    private static void addEnPassant() {
        if (ChessBoard.movePiece != null) { // not first turn
            String previousMove = GameRecord.moveRecord.getStringOfMove(ChessBoard.movePiece, ChessBoard.startSquare, ChessBoard.endSquare);
            if (previousMove != null) {
                if (previousMove.charAt(0) == 'P') {    // piece = pawn
                    if (Math.abs(previousMove.charAt(2) - previousMove.charAt(5)) == 2) {   // double move
                        int startRow = 9 - (previousMove.charAt(2)-48);
                        int startCol = previousMove.charAt(1)-96;
                        if (startRow == 2 || startRow == 7) {
                            int rowMod;
                            boolean whitePawn;
                            if (startRow == 2) {    // movePiece pawn is black
                                rowMod = 1;
                                whitePawn = true;
                            } else {                // movePiece pawn is white
                                rowMod = -1;
                                whitePawn = false;
                            }
                            if (startCol - 1 >= 1) {     // left square
                                Square left = squaresBoard[startRow + 2*rowMod][startCol-1];
                                if (left.hasPiece()) {
                                    Piece piece = left.getPiece();
                                    if (piece.getType() == PAWN && piece.isWhite() == whitePawn) {
                                        Square enPassantSquare = squaresBoard[startRow + rowMod][startCol];
                                        enPassantSquare.setEnPassantMove(true);
                                        piece.addMove(enPassantSquare);

                                    }
                                }
                            }
                            if (startCol + 1 <= 8) {     // right square
                                Square right = squaresBoard[startRow + 2*rowMod][startCol+1];
                                if (right.hasPiece()) {
                                    Piece piece = right.getPiece();
                                    if (piece.getType() == PAWN && piece.isWhite() == whitePawn) {
                                        Square enPassantSquare = squaresBoard[startRow + rowMod][startCol];
                                        enPassantSquare.setEnPassantMove(true);
                                        piece.addMove(enPassantSquare);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Remove attacked squares, remove hidden attacked squares, add castling
    private static void modifyKingMoves(Piece king) {
        boolean isWhite = king.isWhite();
        Square[] moves = king.getMoves();
        for (int i = 0; i < moves.length; i++) {
            if (moves[i] != null) {
                // Attacked squares
                if (isWhite) {
                    if (moves[i].isAttackedByBlack()) {
                        moves[i] = null;
                    }
                } else {
                    if (moves[i].isAttackedByWhite()) {
                        moves[i] = null;
                    }
                }
                if (moves[i] != null) {
                    // Hidden attacked squares
                    int color;
                    if (isWhite) {
                        color = WHITE;
                    } else {
                        color = BLACK;
                    }
                    for (int j = 0; j < hiddenAttackedSquares[color].length; j++) {
                        if (moves[i] == hiddenAttackedSquares[color][j]) {
                            moves[i] = null;
                        }
                    }
                }
            }
        }

        // Castling
        if (isWhite && !king.hasMoved() && !(white && ChessGame.checking)) {
            // White Queenside Castling
            if (!ChessSet.wr1.hasMoved() && !ChessBoard.b1.hasPiece()
                    && !ChessBoard.c1.hasPiece() && !ChessBoard.c1.isAttackedByBlack()
                    && !ChessBoard.d1.hasPiece() && !ChessBoard.d1.isAttackedByBlack()) {
                king.addMove(ChessBoard.c1);
            }
            // White Kingside Castling
            if (!ChessSet.wr2.hasMoved()
                    && !ChessBoard.f1.hasPiece() && !ChessBoard.f1.isAttackedByBlack()
                    && !ChessBoard.g1.hasPiece() && !ChessBoard.g1.isAttackedByBlack()) {
                king.addMove(ChessBoard.g1);
            }
        } else if (!isWhite && !king.hasMoved() && !(!white && ChessGame.checking)) {
            // Black Queenside Castling
            if (!ChessSet.br1.hasMoved() && !ChessBoard.b8.hasPiece()
                    && !ChessBoard.c8.hasPiece() && !ChessBoard.c8.isAttackedByWhite()
                    && !ChessBoard.d8.hasPiece() && !ChessBoard.d8.isAttackedByWhite()) {
                king.addMove(ChessBoard.c8);
            }
            // Black Kingside Castling
            if (!ChessSet.br2.hasMoved()
                    && !ChessBoard.f8.hasPiece() && !ChessBoard.f8.isAttackedByWhite()
                    && !ChessBoard.g8.hasPiece() && !ChessBoard.g8.isAttackedByWhite()) {
                king.addMove(ChessBoard.g8);
            }
        }

        king.setMoves(filterMoves(moves));
    }

    private static void filterPinnedMoves() {
        for (int color = 0; color < chessSet.length; color++) {
            for (int type = 0; type < chessSet[color].length; type++) {
                for (int number = 1; number < chessSet[color][type].length; number++) {
                    Piece piece = chessSet[color][type][number];
                    if (piece != null) {
                        if (piece.isInPlay() && piece.isPinned()) {
                            Square pinnerSquare = piece.getPinner().getSquare();
                            // Find possible moves
                            Square[] possibleMoves = findSquaresBetweenIncludingStart(pinnerSquare,
                                    getKingSquare(piece.isWhite()));
                            // Filter impossible moves
                            Square[] moves = piece.getMoves();
                            for (int i = 0; i < moves.length; i++) {
                                if (moves[i] != null) {
                                    boolean validMove = false;
                                    for (int j = 0; j < possibleMoves.length; j++) {
                                        if (moves[i] == possibleMoves[j]) {
                                            validMove = true;
                                        }
                                    }
                                    if (!validMove) {
                                        moves[i] = null;
                                    }
                                }
                            }
                            piece.setMoves(filterMoves(moves));
                        }
                    }
                }
            }
        }
    }

    private static void filterCheckedMoves() {
        if (ChessGame.checking) {
            int color, oppositeColor;
            if (white) {
                color = WHITE;
                oppositeColor = BLACK;
            } else {
                color = BLACK;
                oppositeColor = WHITE;
            }
            int checking = 0;
            Piece checker = null;
            for (int type = 0; type < chessSet[oppositeColor].length; type++) {
                for (int number = 1; number < chessSet[oppositeColor][type].length; number++) {
                    Piece current = chessSet[oppositeColor][type][number];
                    if (current != null) {
                        if (current.isInPlay() && current.isChecking()) {
                            checking++;
                            checker = current;
                        }
                    }
                }
            }
            if (checking != 1 && checking != 2) {
                System.out.println("Error: MoveFinder.filterCheckedMoves()");
                System.out.println(checking + " checks.");
            } else {
                Square[] possibleMoves = findSquaresBetweenIncludingStart(
                            checker.getSquare(), getOpposingKingSquare(checker.isWhite()));
                for (int type = 0; type < KING; type++) {   // Go through all pieces except the king
                    for (int number = 1; number < chessSet[color][type].length; number++) {
                        Piece piece = chessSet[color][type][number];
                        if (piece != null && piece.isInPlay()) {
                            if (checking == 2) {        // Double check - only king can move
                                piece.setMoves(null);
                                ChessGame.doubleChecking = true;
                            } else if (checking == 1) { // Single check - take 'checker', interpose, move king
                                Square[] moves = piece.getMoves();
                                for (int i = 0; i < moves.length; i++) {
                                    if (moves[i] != null) {
                                        boolean validMove = false;
                                        for (int j = 0; j < possibleMoves.length; j++) {
                                            if (moves[i] == possibleMoves[j]) {
                                                validMove = true;
                                            }
                                        }
                                        if (!validMove) {
                                            moves[i] = null;
                                        }
                                    }
                                }
                                piece.setMoves(filterMoves(moves));
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static Square getKingSquare(boolean white) {
        if (white) {
            return ChessSet.wk1.getSquare();
        } else {
            return ChessSet.bk1.getSquare();
        }
    }

    private static Square getOpposingKingSquare(boolean white) {
        return getKingSquare(!white);
    }

    private static void addHiddenAttackedSquare(boolean white, Square hiddenAttackedSquare) {
        if (white) {
            hiddenAttackedSquares[WHITE][wHiddenCounter++] = hiddenAttackedSquare;
        } else {
            hiddenAttackedSquares[BLACK][bHiddenCounter++] = hiddenAttackedSquare;
        }
    }

    // Returns new Square[7] of all squares that are in bounds, excluding 'start'
    // Starts from the square and goes in the specific direction
    // [rowMod = 1 : down]   [rowMod = -1: up]   [colMod = 1 : right]   [colMod = -1: left]
    private static Square[] findSquaresInDirection(Square start, int rowMod, int colMod) {
        if (start == null || (rowMod == 0 && colMod == 0)) {
            return null;
        }
        Square[] squares = new Square[7];
        int counter = 0;
        int row = start.getRow() + rowMod;
        int col = start.getCol() + colMod;
        while (isInBounds(row, col)) {
            squares[counter++] = squaresBoard[row][col];
            row += rowMod;
            col += colMod;
        }
        return squares;
    }

    // Finds all squares exclusively between 'start' and 'end'
    private static Square[] findSquaresBetween(Square start, Square end) {
        for (int rowMod = -1; rowMod <= 1; rowMod++) {
            for (int colMod = -1; colMod <= 1; colMod++) {
                Square[] squaresInDirection = findSquaresInDirection(start, rowMod, colMod);
                if (squaresInDirection != null) {
                    for (int i = 0; i < squaresInDirection.length; i++) {
                        if (squaresInDirection[i] == end) {
                            Square[] inbetweenSquares = squaresInDirection;
                            for (int j = i; j < inbetweenSquares.length; j++) {
                                inbetweenSquares[j] = null;
                            }
                            return inbetweenSquares;
                        }
                    }
                }
            }
        }
        return null;
    }

    private static Square[] findSquaresBetweenIncludingStart(Square start, Square end) {
        Square[] inbetweenSquares = findSquaresBetween(start, end);
        if (inbetweenSquares == null) {
            return new Square[]{start};
        } else {
            Square[] inbetweenSquaresIncludingStart = new Square[inbetweenSquares.length+1];
            inbetweenSquaresIncludingStart[0] = start;
            for (int i = 0; i < inbetweenSquares.length; i++) {
                inbetweenSquaresIncludingStart[i+1] = inbetweenSquares[i];
            }
            return inbetweenSquaresIncludingStart;
        }
    }

    // Returns true if the row and column are inclusively between 1-8
    private static boolean isInBounds(int row, int col) {
        if (1 <= row && row <= 8 && 1 <= col && col <= 8) {
            return true;
        } else {
            return false;
        }
    }

    // Returns new Square[28] based on the Square[], putting all nulls at the end
    private static Square[] filterMoves(Square[] moves) {
        Square[] filteredMoves = new Square[MOVES];
        int counter = 0;
        for (int i = 0; i < moves.length; i++) {
            if (moves[i] != null) {
                filteredMoves[counter++] = moves[i];
            }
        }
        return filteredMoves;
    }
}
