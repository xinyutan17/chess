package chess;
import java.awt.image.*;

public class Piece {
    private int color, type, number;
    private boolean white, inPlay;
    private String name;
    private BufferedImage image;
    private int value;

    private Square square;
    private Piece pinner;
    private boolean pinned, pinning;
    private boolean moved, checking;
    private int moveCounter, attackedSquareCounter;

    private Square[] moves;
    private Square[] attackedSquares;

    public Piece(int color, int type, int number) {
        // color, type, number
        this.color = color;
        this.type = type;
        this.number = number;
        // white
        if (color == 0) {
            white = true;
        } else {
            white = false;
        }
        // inPlay
        if (((type == ChessSet.QUEEN || type == ChessSet.KING) && number == 1)
                || ((type == ChessSet.ROOK || type == ChessSet.BISHOP || type == ChessSet.KNIGHT)
                    && (number == 1 || number == 2))
                || (type == ChessSet.PAWN && number < 9)) {
            inPlay = true;
        } else {
            inPlay = false;
        }
        // name
        char colorChar, typeChar;
        if (white) {
            colorChar = 'w';
        } else {
            colorChar = 'b';
        }
        switch (type) {
            case ChessSet.QUEEN:    typeChar = 'q'; break;
            case ChessSet.ROOK:     typeChar = 'r'; break;
            case ChessSet.BISHOP:   typeChar = 'b'; break;
            case ChessSet.KNIGHT:   typeChar = 'n'; break;
            case ChessSet.PAWN:     typeChar = 'p'; break;
            case ChessSet.KING:     typeChar = 'k'; break;
            default:
                typeChar = ' ';
                System.out.println("Error: Piece.Piece()");
        }
        name = colorChar + "" + typeChar + "" + number;
        // image
        if (white) {
            switch (type) {
                case ChessSet.QUEEN:    image = ChessSet.whiteQueen; break;
                case ChessSet.ROOK:     image = ChessSet.whiteRook; break;
                case ChessSet.BISHOP:   image = ChessSet.whiteBishop; break;
                case ChessSet.KNIGHT:   image = ChessSet.whiteKnight; break;
                case ChessSet.PAWN:     image = ChessSet.whitePawn; break;
                case ChessSet.KING:     image = ChessSet.whiteKing; break;
                default:
                    System.out.println("Error: Piece.Piece()");
            }
        } else {
            switch (type) {
                case ChessSet.QUEEN:    image = ChessSet.blackQueen; break;
                case ChessSet.ROOK:     image = ChessSet.blackRook; break;
                case ChessSet.BISHOP:   image = ChessSet.blackBishop; break;
                case ChessSet.KNIGHT:   image = ChessSet.blackKnight; break;
                case ChessSet.PAWN:     image = ChessSet.blackPawn; break;
                case ChessSet.KING:     image = ChessSet.blackKing; break;
                default:
                    System.out.println("Error: Piece.Piece()");
            }
        }
        // value
        switch (type) {
            case ChessSet.QUEEN:    value = 9; break;
            case ChessSet.ROOK:     value = 5; break;
            case ChessSet.BISHOP:   value = 3; break;
            case ChessSet.KNIGHT:   value = 3; break;
            case ChessSet.PAWN:     value = 1; break;
            case ChessSet.KING:     value = 0; break;
            default:
                value = -1;
                System.out.println("Error: Piece.Piece()");
        }
        // Other
        square = null;
        pinner = null;
        pinned = pinning = false;
        moved = checking = false;
        moves = new Square[28];
        attackedSquares = new Square[28];
        moveCounter = attackedSquareCounter = 0;
    }

    public int getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isInPlay() {
        return inPlay;
    }

    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
        if (inPlay == false) {
            if (square != null) {
                square.setPiece(null);
                square = null;
            }
        }
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getValue() {
        return value;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Piece getPinner() {
        return pinner;
    }

    public void setPinner(Piece pinner) {
        this.pinner = pinner;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned (boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isPinning() {
        return pinning;
    }

    public void setPinning(boolean pinning) {
        this.pinning = pinning;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved() {
        moved = true;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking (boolean checking) {
        this.checking = checking;
    }

    public void resetCounters() {
        moveCounter = attackedSquareCounter = 0;
    }

    public Square[] getMoves() {
        return moves;
    }

    public void addMove(Square move) {
        moves[moveCounter++] = move;
    }

    public void setMoves(Square[] moves) {
        this.moves = moves;
    }

    public Square[] getAttackedSquares() {
        return attackedSquares;
    }

    public void addAttackedSquare(Square attackedSquare) {
        attackedSquares[attackedSquareCounter++] = attackedSquare;
    }

    public void setAttackedSquares(Square[] attackedSquares) {
        this.attackedSquares = attackedSquares;
    }
}