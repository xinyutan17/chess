package chess;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    static Main main;
    static JPanel centerPanel;
    static int educationalQuestionDifficulty;
    static boolean flipBoard;
    
    public static void main(String[]args) {
        new StartScreen();
    }
    
    public Main() {
        super("Chess");
        setBounds(300,0,800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessSet.loadImages();
        ChessSet.createChessSet();

        createUI();

        ChessGame.startNewGame();
        
        setVisible(true);
    }

    private void createUI() {
        setLayout(new BorderLayout());

        //ChessMenuBar.chessMenuBar = new ChessMenuBar();
        //setJMenuBar(ChessMenuBar.chessMenuBar);
        // Center
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);

        CommandPanel.commandPanel = new CommandPanel();
        centerPanel.add(CommandPanel.commandPanel, BorderLayout.NORTH);

        ChessBoard.chessBoard = new ChessBoard();
        ChessGame.defaultGame = true;
        centerPanel.add(ChessBoard.chessBoard, BorderLayout.CENTER);
        // East
        GameRecord.gameRecord = new GameRecord(StartScreen.getWhiteName(),
                StartScreen.getWhiteHours(), StartScreen.getWhiteMinutes(),
                StartScreen.getWhiteSeconds(), StartScreen.getWhiteDelay(),
                StartScreen.getWhiteIncrement(),StartScreen.getBlackName(),
                StartScreen.getBlackHours(), StartScreen.getBlackMinutes(),
                StartScreen.getBlackSeconds(), StartScreen.getBlackDelay(),
                StartScreen.getBlackIncrement());
        add(GameRecord.gameRecord, BorderLayout.EAST);
    }
   /*static Main window;
   static HumanVersusHuman humanVersusHumanFrame;
   static PiecesSet set;
   static ChessGame game;
   static ChessMenuBar menubar;
   static ChatBox chatBox;
   static JPanel NORTH, EAST, CENTER;
   static GamePanel gamePanel;
   static CommandsPanel commandsPanel;
   static Player whitePlayer, blackPlayer;
   static JLabel playerTurn;
   static ChessBoard whiteBoard, blackBoard;

   public Main() {
       super("Chess");
       white = true;
       set = new PiecesSet();
       menubar = new ChessMenuBar();
       chatBox = new ChatBox();
       humanVersusHumanFrame = new HumanVersusHuman();
       whitePlayer = new Player("WHITE", true);
       blackPlayer = new Player("BLACK", true);
       commandsPanel = new CommandsPanel();
       playerTurn = new JLabel("White to move", JLabel.CENTER);
       blackBoard = new ChessBoard(false);
       whiteBoard = new ChessBoard(true);
       game = new ChessGame();
       gamePanel = new GamePanel();
       setVisible(false);
       setBounds(600,0,700,800);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public static void main (String[]args) {
       setUpGUI();
   }

   public static void setUpGUI() {
       window = new Main();
       window.setJMenuBar(menubar);

       Container screen = window.getContentPane();
       screen.setBackground(Color.white);
       screen.setLayout(new BorderLayout());
       // North
       NORTH = new JPanel();
       NORTH.add(commandsPanel);
       screen.add(NORTH, BorderLayout.NORTH);
       // East
       EAST = new JPanel();
       screen.add(EAST, BorderLayout.EAST);
       EAST.setLayout(new BorderLayout());
       //EAST.add(blackPlayer.time, BorderLayout.NORTH);
       EAST.add(game.moveRecord, BorderLayout.CENTER);
       //EAST.add(whitePlayer.time, BorderLayout.SOUTH);
       // Center
       CENTER = new JPanel();
       CENTER.setLayout(new BorderLayout());
       screen.add(CENTER, BorderLayout.CENTER);

       CENTER.add(playerTurn, BorderLayout.NORTH);
       CENTER.add(gamePanel, BorderLayout.CENTER);
       CENTER.add(chatBox, BorderLayout.SOUTH);

       MoveChecker.initializeAllMoves();

       window.setContentPane(screen);
       window.setVisible(true);
   }*/
}