package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PromotePawnDialog extends JDialog implements ActionListener {
    private JButton queen, rook, bishop, knight;
    private JButton[] buttons;
    private int color;
    private Square endSquare;
    
    public PromotePawnDialog(JFrame owner, int colorAlias, Square endSquareAlias) {
        super (owner, "Promote Pawn", true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new FlowLayout());
        setBounds((owner.getWidth()-600)/2,(owner.getHeight()-600)/2,600,150);

        color = colorAlias;
        endSquare = endSquareAlias;

        buttons = new JButton[]{queen, rook, bishop, knight};
        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            button = new JButton(new ImageIcon(ChessSet.imageSet[color][i]));
            button.setActionCommand(i + "");
            button.addActionListener(this);
            add(button);
        }
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        int type = e.getActionCommand().charAt(0) - 48;
        for (int i = 1; i < ChessSet.chessSet[color][type].length; i++) {
            Piece piece = ChessSet.chessSet[color][type][i];
            if (!piece.isInPlay()) {
                ChessGame.promoting = true;
                endSquare.getPiece().setInPlay(false);
                ChessBoard.endSquare.setPiece(piece);
                piece.setInPlay(true);
                piece.setSquare(endSquare);
                setVisible(false);
                break;
            }
        }
    }
}