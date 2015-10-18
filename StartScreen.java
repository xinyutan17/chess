package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartScreen extends JFrame {
    Container contentPane;
    CardLayout cards;
    ContentPanel content;
    InstructionsPanel instructions;
    static NewGamePanel playGame;

    public static void main(String[]args) {
        new StartScreen();
    }

    public StartScreen() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 200, 400, 400);

        contentPane = getContentPane();
        
        cards = new CardLayout();
        contentPane.setLayout(cards);
        
        content = new ContentPanel();
        contentPane.add(content, "Content");

        playGame = new NewGamePanel();
        contentPane.add(playGame, "New Game");

        instructions = new InstructionsPanel();
        contentPane.add(instructions, "Instructions");
        
        setVisible(true);
    }

    class ContentPanel extends JPanel {
        JButton newGame, instructions;
        int mouseX, mouseY;
        Color mouseColor;

        public ContentPanel() {
            setLayout(null);

            newGame = new JButton("New Game");
            newGame.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cards.show(contentPane, "New Game");
                }
            });
            newGame.setBounds(125, 150, 150, 50);
            add(newGame);

            instructions = new JButton("Instructions");
            instructions.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cards.show(contentPane, "Instructions");
                }
            });
            instructions.setBounds(125, 250, 150, 50);
            add(instructions);

            mouseColor = new Color(50, 50, 255);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    int blue = mouseColor.getBlue();
                    int random = (int)(Math.random()*20 + 30);
                    if (Math.random() < 0.5) {
                        blue += random;
                    } else {
                        blue -= random;
                    }
                    if (blue < 0 || blue > 255) {
                        blue = 255;
                    }
                    mouseColor = new Color(50, 50, blue);
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint();
                }

                public void mouseDragged(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint();
                }
            });
        }

        public void paintComponent(Graphics g) {
            // Background
            int width = getWidth();
            int height = getHeight();
            int halfWidth = width/2;
            int halfHeight = height/2;
            for (int i = 0; i < halfWidth; i++) {
                for (int j = 0; j < halfHeight; j++) {
                    g.setColor(new Color(0, 0, (i + j)/4));
                    g.drawRect(i, j, 1, 1);
                    g.drawRect(width - i, j, 1, 1);
                    g.drawRect(i, height - j, 1, 1);
                    g.drawRect(width - i, height - j, 1, 1);
                }
            }
            // Mouse position
            g.setColor(mouseColor);
            g.fillOval(mouseX - 10, mouseY - 10, 20, 20);
            // Welcome
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Welcome to", 125, 50);
            g.setFont(new Font("Serif", Font.BOLD, 50));
            g.drawString("Chess", 140, 100);
        }
    }

    class InstructionsPanel extends JPanel {
        JPanel buttonPanel;
        JButton newGame, gamePlay, commands;

        CardLayout instructionCards;
        JPanel centerPanel;
        JPanel newGamePanel, gamePlayPanel, commandsPanel;

        JButton back;
        
        public InstructionsPanel() {
            setBackground(Color.white);
            setLayout(new BorderLayout());
            // Button Panel
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(0, 3));
            add(buttonPanel, BorderLayout.NORTH);

            newGame = new JButton("New Game");
            newGame.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    instructionCards.show(centerPanel, "New Game");
                }
            });
            buttonPanel.add(newGame);

            gamePlay = new JButton("Game Play");
            gamePlay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    instructionCards.show(centerPanel, "Game Play");
                }
            });
            buttonPanel.add(gamePlay);

            commands = new JButton("Commands");
            commands.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    instructionCards.show(centerPanel, "Commands");
                }
            });
            buttonPanel.add(commands);
            // Center Panel
            instructionCards = new CardLayout();
            centerPanel = new JPanel();
            centerPanel.setLayout(instructionCards);
            add(centerPanel, BorderLayout.CENTER);
            // New Game
            newGamePanel = new JPanel();
            newGamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            newGamePanel.setLayout(new BoxLayout(newGamePanel, BoxLayout.Y_AXIS));
            centerPanel.add(newGamePanel, "New Game");

            newGamePanel.add(new JLabel("Name - The name you will be called during the game."));
            newGamePanel.add(new JLabel(" ")); // Takes up space
            newGamePanel.add(new JLabel("Time - Represented in hours, minutes, and seconds."));
            newGamePanel.add(new JLabel(" ")); // Takes up space
            newGamePanel.add(new JLabel("Delay - Your timer will not start until after this amount of seconds."));
            newGamePanel.add(new JLabel(" ")); // Takes up space
            newGamePanel.add(new JLabel("Increment - After every move, you will gain this amount of"));
            newGamePanel.add(new JLabel("     seconds."));
            newGamePanel.add(new JLabel(" ")); // Takes up space
            newGamePanel.add(new JLabel("Educational Questions - After every move, you will receive a"));
            newGamePanel.add(new JLabel("     math question. If you answer correctly, you gain 1/60 of the"));
            newGamePanel.add(new JLabel("     initial time."));
            newGamePanel.add(new JLabel(" ")); // Takes up space
            newGamePanel.add(new JLabel("Flip board after each move - After every move, the board flips."));
            newGamePanel.add(new JLabel("     If it is White's turn, it will show the board from White's view."));
            newGamePanel.add(new JLabel("     If it is Black's turn, it will show the board from Black's view."));
            // Game Play
            gamePlayPanel = new JPanel();
            gamePlayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            gamePlayPanel.setLayout(new BoxLayout(gamePlayPanel, BoxLayout.Y_AXIS));
            centerPanel.add(gamePlayPanel, "Game Play");

            gamePlayPanel.add(new JLabel("Queen - Moves horizontally, vertically, and diagonally."));
            gamePlayPanel.add(new JLabel(" ")); // Takes up space
            gamePlayPanel.add(new JLabel("Rook - Moves horizontally and vertically."));
            gamePlayPanel.add(new JLabel(" ")); // Takes up space
            gamePlayPanel.add(new JLabel("Bishop - Moves diagonally."));
            gamePlayPanel.add(new JLabel(" ")); // Takes up space
            gamePlayPanel.add(new JLabel("Knight - Moves in an 'L' shape."));
            gamePlayPanel.add(new JLabel(" ")); // Takes up space
            gamePlayPanel.add(new JLabel("Pawn - Moves forward. Can take pieces on one forward"));
            gamePlayPanel.add(new JLabel("     diagonal square and promote to a different piece."));
            gamePlayPanel.add(new JLabel(" ")); // Takes up space
            gamePlayPanel.add(new JLabel("King - Moves one square in any direction. Can castle."));
            gamePlayPanel.add(new JLabel(" ")); // Takes up space
            gamePlayPanel.add(new JLabel("If you are unsure of where a piece can move, right click that"));
            gamePlayPanel.add(new JLabel("     piece and click \"Show moves\"."));
            gamePlayPanel.add(new JLabel(" ")); // Takes up space
            gamePlayPanel.add(new JLabel("If you see the message \"Game over\", then nothing more can"));
            gamePlayPanel.add(new JLabel("     happen, and you should close the program."));
            // Commands
            commandsPanel = new JPanel();
            commandsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            commandsPanel.setLayout(new BoxLayout(commandsPanel, BoxLayout.Y_AXIS));
            centerPanel.add(commandsPanel, "Commands");

            commandsPanel.add(new JLabel("Offer draw - Confirms that you would like to offer the draw,"));
            commandsPanel.add(new JLabel("     then asks your opponent if they accept."));
            commandsPanel.add(new JLabel(" ")); // Takes up space
            commandsPanel.add(new JLabel("Resign - Confirms if you want to resign. If 'yes', then you forfeit"));
            commandsPanel.add(new JLabel("     the game."));
            commandsPanel.add(new JLabel(" ")); // Takes up space
            commandsPanel.add(new JLabel("Pause/Resume - Stops/restarts the current player's timer and"));
            commandsPanel.add(new JLabel("     restricts/permits the movement of pieces."));
            commandsPanel.add(new JLabel(" ")); // Takes up space
            commandsPanel.add(new JLabel("Flip board - Changes the board's perspective between White's"));
            commandsPanel.add(new JLabel("     view and Black's view."));
            // Back Panel
            back = new JButton("Back");
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cards.show(contentPane, "Content");
                }
            });
            add(back, BorderLayout.SOUTH);
        }
    }

    class NewGamePanel extends JPanel {
        JPanel playerInfoPanel;
        PlayerPanel white, black;

        JPanel educationalQuestionsPanel;
        JCheckBox educationalQuestions;
        JComboBox difficulty;

        JPanel flipBoardPanel;
        JCheckBox flipBoard;

        JPanel actionPanel;
        JButton back, startGame;
        
        public NewGamePanel() {
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            // Player info
            playerInfoPanel = new JPanel();
            playerInfoPanel.setLayout(new GridLayout(1, 2));
            add(playerInfoPanel);
            
            white = new PlayerPanel(true);
            playerInfoPanel.add(white);

            black = new PlayerPanel(false);
            playerInfoPanel.add(black);
            // Educational questions
            educationalQuestionsPanel = new JPanel();
            add(educationalQuestionsPanel);

            educationalQuestions = new JCheckBox("Educational Questions - difficulty:");
            educationalQuestions.setSelected(true);
            educationalQuestionsPanel.add(educationalQuestions);

            difficulty = new JComboBox(new String[]{"Random", "1", "2", "3", "4", "5"});
            educationalQuestionsPanel.add(difficulty);
            // Flip board
            flipBoardPanel = new JPanel();
            add(flipBoardPanel);

            flipBoard = new JCheckBox("Flip board after each move");
            flipBoard.setSelected(true);
            flipBoardPanel.add(flipBoard);
            // Action
            actionPanel = new JPanel();
            actionPanel.setLayout(new GridLayout(1, 2));
            add(actionPanel);

            back = new JButton("Back");
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cards.show(contentPane, "Content");
                }
            });
            actionPanel.add(back);

            startGame = new JButton("Start Game");
            startGame.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    StartScreen.this.setVisible(false);
                    if (!educationalQuestions.isSelected()) {
                        Main.educationalQuestionDifficulty = -1;
                    } else {
                        Main.educationalQuestionDifficulty = difficulty.getSelectedIndex();
                    }
                    Main.flipBoard = flipBoard.isSelected();
                    Main.main = new Main();
                }
            });
            actionPanel.add(startGame);
        }

        class PlayerPanel extends JPanel {
            KeyAdapter keyFilter;
            
            private JPanel row1;
            private JLabel colorLabel;

            private JPanel row2, row21;
            private JLabel nameLabel;
            private JTextField nameField;

            private JPanel row3, row31;
            private JLabel timeLabel;
            private JPanel timePanel;
            private JTextField hours, minutes, seconds;
            private JLabel colon1, colon2;

            private JPanel row4;
            private JLabel delayLabel;
            private JTextField delay;
            private JLabel secondsLabel1;

            private JPanel row5;
            private JLabel incrementLabel;
            private JTextField increment;
            private JLabel secondsLabel2;

            public PlayerPanel(boolean white) {
                setBorder(BorderFactory.createEtchedBorder());
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                keyFilter = new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        boolean alphacharacter;
                        int key = e.getKeyCode();
                        switch (key) {
                            case KeyEvent.VK_UP:
                            case KeyEvent.VK_DOWN:
                            case KeyEvent.VK_LEFT:
                            case KeyEvent.VK_RIGHT:
                            case KeyEvent.VK_BACK_SPACE:
                                alphacharacter = false;
                                break;
                            default:
                                alphacharacter = true;
                        }
                        if (alphacharacter) {
                            Object o = e.getSource();
                            JTextField field = null;
                            if (o == hours) {
                                field = hours;
                            } else if (o == minutes) {
                                field = minutes;
                            } else if (o == seconds) {
                                field = seconds;
                            } else if (o == delay) {
                                field = delay;
                            } else if (o == increment) {
                                field = increment;
                            } else {
                                System.out.println("Error in NewGameFrame.keyFilter");
                            }
                            String text = field.getText();
                            String filtered = "";
                            for (int i = 0; i < text.length(); i++) {
                                char c = text.charAt(i);
                                if ('0' <= c && c <= '9') {
                                    filtered += c;
                                }
                            }
                            if (filtered.length() > 2) {
                                filtered = filtered.substring(0, 2);
                            } else if (filtered.length() == 2 && (int)(filtered.charAt(0)) > '6') { // If tens digit is > 6
                                filtered = "60";
                            }
                            field.setText(filtered);
                        }
                    }
                };
                // Row 1
                row1 = new JPanel();
                add(row1);

                if (white) {
                    colorLabel = new JLabel("WHITE");
                } else {
                    colorLabel = new JLabel("BLACK");
                }
                row1.add(colorLabel);

                // Row 2
                row2 = new JPanel();
                add(row2);

                nameLabel = new JLabel ("Name: ");
                row2.add(nameLabel);

                row21 = new JPanel();
                add(row21);

                if (white) {
                    nameField = new JTextField("White", 12);
                } else {
                    nameField = new JTextField("Black", 12);
                }
                row21.add(nameField);

                // Row 3
                row3 = new JPanel();
                add(row3);

                timeLabel = new JLabel("Time (hours:minutes:seconds):", JLabel.CENTER);
                row3.add(timeLabel);

                row31 = new JPanel();
                add(row31);
                
                timePanel = new JPanel();
                row31.add(timePanel);

                hours = new JTextField("0", 2);
                hours.addKeyListener(keyFilter);
                timePanel.add(hours);

                colon1 = new JLabel(":");
                timePanel.add(colon1);

                minutes = new JTextField("15", 2);
                minutes.addKeyListener(keyFilter);
                timePanel.add(minutes);

                colon2 = new JLabel(":");
                timePanel.add(colon2);

                seconds = new JTextField("00", 2);
                seconds.addKeyListener(keyFilter);
                timePanel.add(seconds);
                // Row 4
                row4 = new JPanel();
                add(row4);

                delayLabel = new JLabel("Delay:");
                row4.add(delayLabel);

                delay = new JTextField("3", 2);
                delay.addKeyListener(keyFilter);
                row4.add(delay);

                secondsLabel1 = new JLabel("seconds");
                row4.add(secondsLabel1);
                // Row 5
                row5 = new JPanel();
                add(row5);

                incrementLabel = new JLabel("Increment:");
                row5.add(incrementLabel);

                increment = new JTextField("10", 2);
                increment.addKeyListener(keyFilter);
                row5.add(increment);

                secondsLabel2 = new JLabel("seconds");
                row5.add(secondsLabel2);
            }
        }
    }
    
    private static int toInt(String s) {
        if (s.length() == 1) {
            return (int)(s.charAt(0))-48;
        } else if (s.length() == 2) {
            //                  tens digit              ones digit
            return ((int)(s.charAt(0))-48)*10 + ((int)(s.charAt(1)) - 48);
        } else {
            return 0;
        }
    }

    public static String getWhiteName() {
        return playGame.white.nameField.getText();
    }

    public static int getWhiteHours() {
        return toInt(playGame.white.hours.getText());
    }

    public static int getWhiteMinutes() {
        return toInt(playGame.white.minutes.getText());
    }

    public static int getWhiteSeconds() {
        return toInt(playGame.white.seconds.getText());
    }

    public static int getWhiteDelay() {
        return toInt(playGame.white.delay.getText());
    }

    public static int getWhiteIncrement() {
        return toInt(playGame.white.increment.getText());
    }

    public static String getBlackName() {
        return playGame.black.nameField.getText();
    }
    
    public static int getBlackHours() {
        return toInt(playGame.black.hours.getText());
    }

    public static int getBlackMinutes() {
        return toInt(playGame.black.minutes.getText());
    }

    public static int getBlackSeconds() {
        return toInt(playGame.black.seconds.getText());
    }

    public static int getBlackDelay() {
        return toInt(playGame.black.delay.getText());
    }

    public static int getBlackIncrement() {
        return toInt(playGame.black.increment.getText());
    }
}
