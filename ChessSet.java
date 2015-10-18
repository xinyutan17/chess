package chess;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class ChessSet {
    static final int WHITE = 0;
    static final int BLACK = 1;

    static final int QUEEN = 0;
    static final int ROOK = 1;
    static final int BISHOP = 2;
    static final int KNIGHT = 3;
    static final int PAWN = 4;
    static final int KING = 5;

    static Piece[][][] chessSet;
    // White pieces
    static Piece wq1, wq2, wq3, wq4, wq5, wq6, wq7, wq8, wq9, wq10, wq11, wq12, wq13, wq14, wq15, wq16;
    static Piece wr1, wr2, wr3, wr4, wr5, wr6, wr7, wr8, wr9, wr10, wr11, wr12, wr13, wr14, wr15, wr16;
    static Piece wb1, wb2, wb3, wb4, wb5, wb6, wb7, wb8, wb9, wb10, wb11, wb12, wb13, wb14, wb15, wb16;
    static Piece wn1, wn2, wn3, wn4, wn5, wn6, wn7, wn8, wn9, wn10, wn11, wn12, wn13, wn14, wn15, wn16;
    static Piece wp1, wp2, wp3, wp4, wp5, wp6, wp7, wp8, wp9, wp10, wp11, wp12, wp13, wp14, wp15, wp16;
    static Piece wk1;
    // Black pieces
    static Piece bq1, bq2, bq3, bq4, bq5, bq6, bq7, bq8, bq9, bq10, bq11, bq12, bq13, bq14, bq15, bq16;
    static Piece br1, br2, br3, br4, br5, br6, br7, br8, br9, br10, br11, br12, br13, br14, br15, br16;
    static Piece bb1, bb2, bb3, bb4, bb5, bb6, bb7, bb8, bb9, bb10, bb11, bb12, bb13, bb14, bb15, bb16;
    static Piece bn1, bn2, bn3, bn4, bn5, bn6, bn7, bn8, bn9, bn10, bn11, bn12, bn13, bn14, bn15, bn16;
    static Piece bp1, bp2, bp3, bp4, bp5, bp6, bp7, bp8, bp9, bp10, bp11, bp12, bp13, bp14, bp15, bp16;
    static Piece bk1;

    static BufferedImage[][] imageSet;
    static BufferedImage whiteQueen, whiteRook, whiteBishop, whiteKnight, whiteKing, whitePawn;
    static BufferedImage blackQueen, blackRook, blackBishop, blackKnight, blackKing, blackPawn;
    
    public static void loadImages() {
        Color white = new Color(255,255,255);
        Color gray = new Color(127,127,127);
        imageSet = new BufferedImage[2][6];
        try {
            whiteQueen = filter(ImageIO.read(new File("chess/img/White_Queen.png")), gray);   imageSet[WHITE][QUEEN] = whiteQueen;
            whiteRook = filter(ImageIO.read(new File("chess/img/White_Rook.png")), gray);     imageSet[WHITE][ROOK] = whiteRook;
            whiteBishop = filter(ImageIO.read(new File("chess/img/White_Bishop.png")), gray); imageSet[WHITE][BISHOP] = whiteBishop;
            whiteKnight = filter(ImageIO.read(new File("chess/img/White_Knight.png")), gray); imageSet[WHITE][KNIGHT] = whiteKnight;
            whitePawn = filter(ImageIO.read(new File("chess/img/White_Pawn.png")), gray);     imageSet[WHITE][PAWN] = whitePawn;
            whiteKing = filter(ImageIO.read(new File("chess/img/White_King.png")), gray);     imageSet[WHITE][KING] = whiteKing;

            blackQueen = filter(ImageIO.read(new File("chess/img/Black_Queen.png")), white);  imageSet[BLACK][QUEEN] = blackQueen;
            blackRook = filter(ImageIO.read(new File("chess/img/Black_Rook.png")), white);    imageSet[BLACK][ROOK] = blackRook;
            blackBishop = filter(ImageIO.read(new File("chess/img/Black_Bishop.png")), white);imageSet[BLACK][BISHOP] = blackBishop;
            blackKnight = filter(ImageIO.read(new File("chess/img/Black_Knight.png")), white);imageSet[BLACK][KNIGHT] = blackKnight;
            blackPawn = filter(ImageIO.read(new File("chess/img/Black_Pawn.png")), white);    imageSet[BLACK][PAWN] = blackPawn;
            blackKing = filter(ImageIO.read(new File("chess/img/Black_King.png")), white);    imageSet[BLACK][KING] = blackKing;
        } catch (IOException e) {
            System.out.println("Image files not found.");
        }
    }

    public static void createChessSet() {
        chessSet = new Piece[2][6][17];  // [Color][Type][Number]

        // White
        wq1 = new Piece(WHITE, QUEEN, 1); chessSet[WHITE][QUEEN][1] = wq1;
        wq2 = new Piece(WHITE, QUEEN, 2); chessSet[WHITE][QUEEN][2] = wq2;
        wq3 = new Piece(WHITE, QUEEN, 3); chessSet[WHITE][QUEEN][3] = wq3;
        wq4 = new Piece(WHITE, QUEEN, 4); chessSet[WHITE][QUEEN][4] = wq4;
        wq5 = new Piece(WHITE, QUEEN, 5); chessSet[WHITE][QUEEN][5] = wq5;
        wq6 = new Piece(WHITE, QUEEN, 6); chessSet[WHITE][QUEEN][6] = wq6;
        wq7 = new Piece(WHITE, QUEEN, 7); chessSet[WHITE][QUEEN][7] = wq7;
        wq8 = new Piece(WHITE, QUEEN, 8); chessSet[WHITE][QUEEN][8] = wq8;
        wq9 = new Piece(WHITE, QUEEN, 9); chessSet[WHITE][QUEEN][9] = wq9;
        wq10 = new Piece(WHITE, QUEEN, 10); chessSet[WHITE][QUEEN][10] = wq10;
        wq11 = new Piece(WHITE, QUEEN, 11); chessSet[WHITE][QUEEN][11] = wq11;
        wq12 = new Piece(WHITE, QUEEN, 12); chessSet[WHITE][QUEEN][12] = wq12;
        wq13 = new Piece(WHITE, QUEEN, 13); chessSet[WHITE][QUEEN][13] = wq13;
        wq14 = new Piece(WHITE, QUEEN, 14); chessSet[WHITE][QUEEN][14] = wq14;
        wq15 = new Piece(WHITE, QUEEN, 15); chessSet[WHITE][QUEEN][15] = wq15;
        wq16 = new Piece(WHITE, QUEEN, 16); chessSet[WHITE][QUEEN][16] = wq16;

        wr1 = new Piece(WHITE, ROOK, 1); chessSet[WHITE][ROOK][1] = wr1;
        wr2 = new Piece(WHITE, ROOK, 2); chessSet[WHITE][ROOK][2] = wr2;
        wr3 = new Piece(WHITE, ROOK, 3); chessSet[WHITE][ROOK][3] = wr3;
        wr4 = new Piece(WHITE, ROOK, 4); chessSet[WHITE][ROOK][4] = wr4;
        wr5 = new Piece(WHITE, ROOK, 5); chessSet[WHITE][ROOK][5] = wr5;
        wr6 = new Piece(WHITE, ROOK, 6); chessSet[WHITE][ROOK][6] = wr6;
        wr7 = new Piece(WHITE, ROOK, 7); chessSet[WHITE][ROOK][7] = wr7;
        wr8 = new Piece(WHITE, ROOK, 8); chessSet[WHITE][ROOK][8] = wr8;
        wr9 = new Piece(WHITE, ROOK, 9); chessSet[WHITE][ROOK][9] = wr9;
        wr10 = new Piece(WHITE, ROOK, 10); chessSet[WHITE][ROOK][10] = wr10;
        wr11 = new Piece(WHITE, ROOK, 11); chessSet[WHITE][ROOK][11] = wr11;
        wr12 = new Piece(WHITE, ROOK, 12); chessSet[WHITE][ROOK][12] = wr12;
        wr13 = new Piece(WHITE, ROOK, 13); chessSet[WHITE][ROOK][13] = wr13;
        wr14 = new Piece(WHITE, ROOK, 14); chessSet[WHITE][ROOK][14] = wr14;
        wr15 = new Piece(WHITE, ROOK, 15); chessSet[WHITE][ROOK][15] = wr15;
        wr16 = new Piece(WHITE, ROOK, 16); chessSet[WHITE][ROOK][16] = wr16;

        wb1 = new Piece(WHITE, BISHOP, 1); chessSet[WHITE][BISHOP][1] = wb1;
        wb2 = new Piece(WHITE, BISHOP, 2); chessSet[WHITE][BISHOP][2] = wb2;
        wb3 = new Piece(WHITE, BISHOP, 3); chessSet[WHITE][BISHOP][3] = wb3;
        wb4 = new Piece(WHITE, BISHOP, 4); chessSet[WHITE][BISHOP][4] = wb4;
        wb5 = new Piece(WHITE, BISHOP, 5); chessSet[WHITE][BISHOP][5] = wb5;
        wb6 = new Piece(WHITE, BISHOP, 6); chessSet[WHITE][BISHOP][6] = wb6;
        wb7 = new Piece(WHITE, BISHOP, 7); chessSet[WHITE][BISHOP][7] = wb7;
        wb8 = new Piece(WHITE, BISHOP, 8); chessSet[WHITE][BISHOP][8] = wb8;
        wb9 = new Piece(WHITE, BISHOP, 9); chessSet[WHITE][BISHOP][9] = wb9;
        wb10 = new Piece(WHITE, BISHOP, 10); chessSet[WHITE][BISHOP][10] = wb10;
        wb11 = new Piece(WHITE, BISHOP, 11); chessSet[WHITE][BISHOP][11] = wb11;
        wb12 = new Piece(WHITE, BISHOP, 12); chessSet[WHITE][BISHOP][12] = wb12;
        wb13 = new Piece(WHITE, BISHOP, 13); chessSet[WHITE][BISHOP][13] = wb13;
        wb14 = new Piece(WHITE, BISHOP, 14); chessSet[WHITE][BISHOP][14] = wb14;
        wb15 = new Piece(WHITE, BISHOP, 15); chessSet[WHITE][BISHOP][15] = wb15;
        wb16 = new Piece(WHITE, BISHOP, 16); chessSet[WHITE][BISHOP][16] = wb16;

        wn1 = new Piece(WHITE, KNIGHT, 1); chessSet[WHITE][KNIGHT][1] = wn1;
        wn2 = new Piece(WHITE, KNIGHT, 2); chessSet[WHITE][KNIGHT][2] = wn2;
        wn3 = new Piece(WHITE, KNIGHT, 3); chessSet[WHITE][KNIGHT][3] = wn3;
        wn4 = new Piece(WHITE, KNIGHT, 4); chessSet[WHITE][KNIGHT][4] = wn4;
        wn5 = new Piece(WHITE, KNIGHT, 5); chessSet[WHITE][KNIGHT][5] = wn5;
        wn6 = new Piece(WHITE, KNIGHT, 6); chessSet[WHITE][KNIGHT][6] = wn6;
        wn7 = new Piece(WHITE, KNIGHT, 7); chessSet[WHITE][KNIGHT][7] = wn7;
        wn8 = new Piece(WHITE, KNIGHT, 8); chessSet[WHITE][KNIGHT][8] = wn8;
        wn9 = new Piece(WHITE, KNIGHT, 9); chessSet[WHITE][KNIGHT][9] = wn9;
        wn10 = new Piece(WHITE, KNIGHT, 10); chessSet[WHITE][KNIGHT][10] = wn10;
        wn11 = new Piece(WHITE, KNIGHT, 11); chessSet[WHITE][KNIGHT][11] = wn11;
        wn12 = new Piece(WHITE, KNIGHT, 12); chessSet[WHITE][KNIGHT][12] = wn12;
        wn13 = new Piece(WHITE, KNIGHT, 13); chessSet[WHITE][KNIGHT][13] = wn13;
        wn14 = new Piece(WHITE, KNIGHT, 14); chessSet[WHITE][KNIGHT][14] = wn14;
        wn15 = new Piece(WHITE, KNIGHT, 15); chessSet[WHITE][KNIGHT][15] = wn15;
        wn16 = new Piece(WHITE, KNIGHT, 16); chessSet[WHITE][KNIGHT][16] = wn16;
        
        wp1 = new Piece(WHITE, PAWN, 1); chessSet[WHITE][PAWN][1] = wp1;
        wp2 = new Piece(WHITE, PAWN, 2); chessSet[WHITE][PAWN][2] = wp2;
        wp3 = new Piece(WHITE, PAWN, 3); chessSet[WHITE][PAWN][3] = wp3;
        wp4 = new Piece(WHITE, PAWN, 4); chessSet[WHITE][PAWN][4] = wp4;
        wp5 = new Piece(WHITE, PAWN, 5); chessSet[WHITE][PAWN][5] = wp5;
        wp6 = new Piece(WHITE, PAWN, 6); chessSet[WHITE][PAWN][6] = wp6;
        wp7 = new Piece(WHITE, PAWN, 7); chessSet[WHITE][PAWN][7] = wp7;
        wp8 = new Piece(WHITE, PAWN, 8); chessSet[WHITE][PAWN][8] = wp8;
        wp9 = new Piece(WHITE, PAWN, 9); chessSet[WHITE][PAWN][9] = wp9;
        wp10 = new Piece(WHITE, PAWN, 10); chessSet[WHITE][PAWN][10] = wp10;
        wp11 = new Piece(WHITE, PAWN, 11); chessSet[WHITE][PAWN][11] = wp11;
        wp12 = new Piece(WHITE, PAWN, 12); chessSet[WHITE][PAWN][12] = wp12;
        wp13 = new Piece(WHITE, PAWN, 13); chessSet[WHITE][PAWN][13] = wp13;
        wp14 = new Piece(WHITE, PAWN, 14); chessSet[WHITE][PAWN][14] = wp14;
        wp15 = new Piece(WHITE, PAWN, 15); chessSet[WHITE][PAWN][15] = wp15;
        wp16 = new Piece(WHITE, PAWN, 16); chessSet[WHITE][PAWN][16] = wp16;
        
        wk1 = new Piece(WHITE, KING, 1); chessSet[WHITE][KING][1] = wk1;

        // Black
        bq1 = new Piece(BLACK, QUEEN, 1); chessSet[BLACK][QUEEN][1] = bq1;
        bq2 = new Piece(BLACK, QUEEN, 2); chessSet[BLACK][QUEEN][2] = bq2;
        bq3 = new Piece(BLACK, QUEEN, 3); chessSet[BLACK][QUEEN][3] = bq3;
        bq4 = new Piece(BLACK, QUEEN, 4); chessSet[BLACK][QUEEN][4] = bq4;
        bq5 = new Piece(BLACK, QUEEN, 5); chessSet[BLACK][QUEEN][5] = bq5;
        bq6 = new Piece(BLACK, QUEEN, 6); chessSet[BLACK][QUEEN][6] = bq6;
        bq7 = new Piece(BLACK, QUEEN, 7); chessSet[BLACK][QUEEN][7] = bq7;
        bq8 = new Piece(BLACK, QUEEN, 8); chessSet[BLACK][QUEEN][8] = bq8;
        bq9 = new Piece(BLACK, QUEEN, 9); chessSet[BLACK][QUEEN][9] = bq9;
        bq10 = new Piece(BLACK, QUEEN, 10); chessSet[BLACK][QUEEN][10] = bq10;
        bq11 = new Piece(BLACK, QUEEN, 11); chessSet[BLACK][QUEEN][11] = bq11;
        bq12 = new Piece(BLACK, QUEEN, 12); chessSet[BLACK][QUEEN][12] = bq12;
        bq13 = new Piece(BLACK, QUEEN, 13); chessSet[BLACK][QUEEN][13] = bq13;
        bq14 = new Piece(BLACK, QUEEN, 14); chessSet[BLACK][QUEEN][14] = bq14;
        bq15 = new Piece(BLACK, QUEEN, 15); chessSet[BLACK][QUEEN][15] = bq15;
        bq16 = new Piece(BLACK, QUEEN, 16); chessSet[BLACK][QUEEN][16] = bq16;
        
        br1 = new Piece(BLACK, ROOK, 1); chessSet[BLACK][ROOK][1] = br1;
        br2 = new Piece(BLACK, ROOK, 2); chessSet[BLACK][ROOK][2] = br2;
        br3 = new Piece(BLACK, ROOK, 3); chessSet[BLACK][ROOK][3] = br3;
        br4 = new Piece(BLACK, ROOK, 4); chessSet[BLACK][ROOK][4] = br4;
        br5 = new Piece(BLACK, ROOK, 5); chessSet[BLACK][ROOK][5] = br5;
        br6 = new Piece(BLACK, ROOK, 6); chessSet[BLACK][ROOK][6] = br6;
        br7 = new Piece(BLACK, ROOK, 7); chessSet[BLACK][ROOK][7] = br7;
        br8 = new Piece(BLACK, ROOK, 8); chessSet[BLACK][ROOK][8] = br8;
        br9 = new Piece(BLACK, ROOK, 9); chessSet[BLACK][ROOK][9] = br9;
        br10 = new Piece(BLACK, ROOK, 10); chessSet[BLACK][ROOK][10] = br10;
        br11 = new Piece(BLACK, ROOK, 11); chessSet[BLACK][ROOK][11] = br11;
        br12 = new Piece(BLACK, ROOK, 12); chessSet[BLACK][ROOK][12] = br12;
        br13 = new Piece(BLACK, ROOK, 13); chessSet[BLACK][ROOK][13] = br13;
        br14 = new Piece(BLACK, ROOK, 14); chessSet[BLACK][ROOK][14] = br14;
        br15 = new Piece(BLACK, ROOK, 15); chessSet[BLACK][ROOK][15] = br15;
        br16 = new Piece(BLACK, ROOK, 16); chessSet[BLACK][ROOK][16] = br16;
        
        bb1 = new Piece(BLACK, BISHOP, 1); chessSet[BLACK][BISHOP][1] = bb1;
        bb2 = new Piece(BLACK, BISHOP, 2); chessSet[BLACK][BISHOP][2] = bb2;
        bb3 = new Piece(BLACK, BISHOP, 3); chessSet[BLACK][BISHOP][3] = bb3;
        bb4 = new Piece(BLACK, BISHOP, 4); chessSet[BLACK][BISHOP][4] = bb4;
        bb5 = new Piece(BLACK, BISHOP, 5); chessSet[BLACK][BISHOP][5] = bb5;
        bb6 = new Piece(BLACK, BISHOP, 6); chessSet[BLACK][BISHOP][6] = bb6;
        bb7 = new Piece(BLACK, BISHOP, 7); chessSet[BLACK][BISHOP][7] = bb7;
        bb8 = new Piece(BLACK, BISHOP, 8); chessSet[BLACK][BISHOP][8] = bb8;
        bb9 = new Piece(BLACK, BISHOP, 9); chessSet[BLACK][BISHOP][9] = bb9;
        bb10 = new Piece(BLACK, BISHOP, 10); chessSet[BLACK][BISHOP][10] = bb10;
        bb11 = new Piece(BLACK, BISHOP, 11); chessSet[BLACK][BISHOP][11] = bb11;
        bb12 = new Piece(BLACK, BISHOP, 12); chessSet[BLACK][BISHOP][12] = bb12;
        bb13 = new Piece(BLACK, BISHOP, 13); chessSet[BLACK][BISHOP][13] = bb13;
        bb14 = new Piece(BLACK, BISHOP, 14); chessSet[BLACK][BISHOP][14] = bb14;
        bb15 = new Piece(BLACK, BISHOP, 15); chessSet[BLACK][BISHOP][15] = bb15;
        bb16 = new Piece(BLACK, BISHOP, 16); chessSet[BLACK][BISHOP][16] = bb16;

        bn1 = new Piece(BLACK, KNIGHT, 1); chessSet[BLACK][KNIGHT][1] = bn1;
        bn2 = new Piece(BLACK, KNIGHT, 2); chessSet[BLACK][KNIGHT][2] = bn2;
        bn3 = new Piece(BLACK, KNIGHT, 3); chessSet[BLACK][KNIGHT][3] = bn3;
        bn4 = new Piece(BLACK, KNIGHT, 4); chessSet[BLACK][KNIGHT][4] = bn4;
        bn5 = new Piece(BLACK, KNIGHT, 5); chessSet[BLACK][KNIGHT][5] = bn5;
        bn6 = new Piece(BLACK, KNIGHT, 6); chessSet[BLACK][KNIGHT][6] = bn6;
        bn7 = new Piece(BLACK, KNIGHT, 7); chessSet[BLACK][KNIGHT][7] = bn7;
        bn8 = new Piece(BLACK, KNIGHT, 8); chessSet[BLACK][KNIGHT][8] = bn8;
        bn9 = new Piece(BLACK, KNIGHT, 9); chessSet[BLACK][KNIGHT][9] = bn9;
        bn10 = new Piece(BLACK, KNIGHT, 10); chessSet[BLACK][KNIGHT][10] = bn10;
        bn11 = new Piece(BLACK, KNIGHT, 11); chessSet[BLACK][KNIGHT][11] = bn11;
        bn12 = new Piece(BLACK, KNIGHT, 12); chessSet[BLACK][KNIGHT][12] = bn12;
        bn13 = new Piece(BLACK, KNIGHT, 13); chessSet[BLACK][KNIGHT][13] = bn13;
        bn14 = new Piece(BLACK, KNIGHT, 14); chessSet[BLACK][KNIGHT][14] = bn14;
        bn15 = new Piece(BLACK, KNIGHT, 15); chessSet[BLACK][KNIGHT][15] = bn15;
        bn16 = new Piece(BLACK, KNIGHT, 16); chessSet[BLACK][KNIGHT][16] = bn16;

        bp1 = new Piece(BLACK, PAWN, 1); chessSet[BLACK][PAWN][1] = bp1;
        bp2 = new Piece(BLACK, PAWN, 2); chessSet[BLACK][PAWN][2] = bp2;
        bp3 = new Piece(BLACK, PAWN, 3); chessSet[BLACK][PAWN][3] = bp3;
        bp4 = new Piece(BLACK, PAWN, 4); chessSet[BLACK][PAWN][4] = bp4;
        bp5 = new Piece(BLACK, PAWN, 5); chessSet[BLACK][PAWN][5] = bp5;
        bp6 = new Piece(BLACK, PAWN, 6); chessSet[BLACK][PAWN][6] = bp6;
        bp7 = new Piece(BLACK, PAWN, 7); chessSet[BLACK][PAWN][7] = bp7;
        bp8 = new Piece(BLACK, PAWN, 8); chessSet[BLACK][PAWN][8] = bp8;
        bp9 = new Piece(BLACK, PAWN, 9); chessSet[BLACK][PAWN][9] = bp9;
        bp10 = new Piece(BLACK, PAWN, 10); chessSet[BLACK][PAWN][10] = bp10;
        bp11 = new Piece(BLACK, PAWN, 11); chessSet[BLACK][PAWN][11] = bp11;
        bp12 = new Piece(BLACK, PAWN, 12); chessSet[BLACK][PAWN][12] = bp12;
        bp13 = new Piece(BLACK, PAWN, 13); chessSet[BLACK][PAWN][13] = bp13;
        bp14 = new Piece(BLACK, PAWN, 14); chessSet[BLACK][PAWN][14] = bp14;
        bp15 = new Piece(BLACK, PAWN, 15); chessSet[BLACK][PAWN][15] = bp15;
        bp16 = new Piece(BLACK, PAWN, 16); chessSet[BLACK][PAWN][16] = bp16;

        bk1 = new Piece(BLACK, KING, 1); chessSet[BLACK][KING][1] = bk1;
    }

    private static BufferedImage filter(BufferedImage image, Color filterColor) {
        BufferedImage convertedImage = new BufferedImage(image.getWidth(),image.getHeight(),Transparency.BITMASK);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x,y) == filterColor.getRGB()) {                // If the color is filterColor,
                    convertedImage.setRGB(x, y, new Color(0,0,0,0).getRGB());   // make the pixel transparent.
                }
                else {                                                          // Else,
                    convertedImage.setRGB(x, y, image.getRGB(x,y));             // keep the original color.
                }
            }
        }
        return convertedImage;
    }
}