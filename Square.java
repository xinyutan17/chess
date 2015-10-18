package chess;
import java.awt.*;

public class Square {
    private String name;
    private int row, col;       // (x,y) coordinate in squaresBoard
    private Color defaultColor;

    private Color highlightColor;
    private Piece piece;
    private boolean attackedByWhite, attackedByBlack;
    private boolean enPassantMove;

    public Square (String name) {
        this.name = name;
        switch (name.charAt(0)) {
            case 'a': col = 1; break;
            case 'b': col = 2; break;
            case 'c': col = 3; break;
            case 'd': col = 4; break;
            case 'e': col = 5; break;
            case 'f': col = 6; break;
            case 'g': col = 7; break;
            case 'h': col = 8; break;
            default:
                System.out.println("Error: Square.Square()");
        }
        switch (name.charAt(1)) {
            case '1': row = 8; break;
            case '2': row = 7; break;
            case '3': row = 6; break;
            case '4': row = 5; break;
            case '5': row = 4; break;
            case '6': row = 3; break;
            case '7': row = 2; break;
            case '8': row = 1; break;
            default:
                System.out.println("Error: Square.Square()");
        }
        if ((row+col)%2 == 1) {
            defaultColor = new Color(139, 101, 8); // yellow
        } else {
            defaultColor = new Color(255, 255, 0); // brown
        }
        highlightColor = defaultColor;
        piece = null;
        attackedByWhite = attackedByBlack = false;
        enPassantMove = false;
    }
    
    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        if (piece != null) {
            this.piece = piece;
            this.piece.setInPlay(true);
            this.piece.setSquare(this);
        } else {
            this.piece = null;
        }
    }

    public boolean hasPiece() {
        if (piece != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAttackedByWhite() {
        return attackedByWhite;
    }

    public void setAttackedByWhite(boolean attackedByWhite) {
        this.attackedByWhite = attackedByWhite;
    }

    public boolean isAttackedByBlack() {
        return attackedByBlack;
    }

    public void setAttackedByBlack(boolean attackedByBlack) {
        this.attackedByBlack = attackedByBlack;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }

    public boolean isEnPassantMove() {
        return enPassantMove;
    }

    public void setEnPassantMove(boolean enPassantMove) {
        this.enPassantMove = enPassantMove;
    }
}