package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class QuestionDialog extends JDialog {
    //private static final int width = 400;
    private static final int width = 500;
    private static final int height = 125;
    // UI
    private static JFrame frame;
    private static JComboBox levelBox;
    private static JButton newQuestion;
    private static JLabel score;
    private static JButton reset;

    private static int correctCount, totalCount;
    // QuestionDialog()
    private static int level = 0;

    private static JLabel instructions, problem, timeRemaining;
    private static JTextField answerField;
    private static JButton giveUp;

    private static boolean gaveUp;
    private static int seconds;
    private static int correctAnswer, userAnswer;
    private static CountdownTimer timer;

    public static void main(String[]args) {
        // Create UI
        frame = new JFrame("Math Questions Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, width, height);
        frame.setResizable(false);

        levelBox = new JComboBox(new String[] {"Random level",
            "Level 1", "Level 2", "Level 3", "Level 4", "Level 5"});
        levelBox.setSelectedIndex(level);
        levelBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                level = levelBox.getSelectedIndex();
            }
        });
        frame.add(levelBox, BorderLayout.NORTH);

        newQuestion = new JButton("New Question");
        newQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                QuestionDialog dialog = new QuestionDialog(frame, level);
                if(dialog.answerIsCorrect()) {
                   correctCount++;
                }
                totalCount++;
                score.setText("Correct answers: " + correctCount + "/" + totalCount
                        + " (" + (int)((double)correctCount/totalCount*100) + "%)");
            }
        });
        frame.add(newQuestion, BorderLayout.CENTER);

        score = new JLabel();
        frame.add(score, BorderLayout.SOUTH);
        reset();

        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        frame.add(reset, BorderLayout.EAST);

        frame.setVisible(true);
    }

    public static void reset() {
        correctCount = 0;
        totalCount = 0;
        score.setText("Press \"New Question\" to begin");
    }

    public QuestionDialog(Frame owner, int level) {
        super(owner, "Math Question: " + level, true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(width, height);
        if (owner != null) {
            setLocation(owner.getX() + (owner.getWidth()-width)/2,
                    owner.getY() + (owner.getHeight()-height)/2);
        } else {
            setLocation(400, 400);
        }

        setResizable(false);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        instructions = new JLabel("Do the following problem from left to right. Leave off any decimals.");
        add(instructions);

        problem = new JLabel("");
        add(problem);

        timeRemaining = new JLabel("");
        add(timeRemaining);

        answerField = new JTextField(12);
        answerField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();

                String answer = answerField.getText();
                if (answer.length() == 0) {
                    gaveUp = true;
                } else {
                    userAnswer = 0;
                    boolean negative = false;
                    int power = 0;
                    for (int i = answer.length() - 1; i >= 0; i--) {
                        char c = answer.charAt(i);
                        if (c == '-') {
                            negative = true;
                        } else if ('0' <= c && c <= '9') {
                            userAnswer += (int)(c-48)*(Math.pow(10, power));
                            power++;
                        }
                    }
                    if (negative) {
                        userAnswer *= -1;
                    }
                }
                showResult();
            }
        });
        answerField.addKeyListener(new KeyAdapter() {
            // Makes it so only numbers and '-' can be typed
                public void keyReleased(KeyEvent e) {
                String text = answerField.getText();
                String filtered = "";
                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    if (c == '-' || ('0' <= c && c <= '9')) {
                        filtered += c;
                    }
                }
                answerField.setText(filtered);
            }
        });
        add(answerField);

        giveUp = new JButton("Give Up");
        giveUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gaveUp = true;
                showResult();
            }
        });
        add(giveUp);
        // Initialize variables
        gaveUp = false;
        seconds = 0;
        correctAnswer = userAnswer = 0;
        // Create arrays of random operations and operands, add time based on operations
        int currentLevel = level;
        if (currentLevel == 0) {                         // It has been chosen as "random"
            currentLevel = (int)(Math.random()*5 + 1);   // 1-5
            setTitle("Math Question: " + currentLevel);
        }
        int difficulty = 3*currentLevel;
        seconds = difficulty;
        int[] numbers = new int[difficulty];
        char[] operations = new char[difficulty];
        for (int i = 0; i < operations.length; i++) {
            double random1 = Math.random();
            double random2 = Math.random();
            if (random1 < 0.5) {
                if (random2 < 0.5) {
                    operations[i] = '+';
                } else {
                    operations[i] = '-';
                }
                numbers[i] = (int)(Math.random()*20) + 1;       // 1-20
                seconds += 2;
            } else {
                if (random2 < 0.5) {
                    operations[i] = 'x';
                } else {
                    operations[i] = 247;                        // 247 = division symbol
                }
                numbers[i] = (int)(Math.random()*4) + 2;        // 2-5
                seconds += 3;
            }
        }
        // Find correct answer
        double answer = (int)(Math.random()*10) + 1;            // 1-10
        String question = "" + (int)answer;
        for (int i = 0; i < operations.length; i++) {
            char operation = operations[i];
            int number = numbers[i];
            question += " " + operation + " " + number;
            switch (operation) {
                case '+': answer += number; break;
                case '-': answer -= number; break;
                case 'x': answer *= number; break;
                case 247: answer /= number; break;              // 247 = division symbol
                default:
                    System.out.println("Error in QuestionDialog()");
            }
        }
        correctAnswer = (int)answer;
        question += " = ";
        // Set text
        problem.setText(question);
        // Create timer
        timer = new CountdownTimer(10, "You have: ", " seconds remaining.",
                0, 0, seconds, 0.0,
                new Runnable() {            // Change Time
                    public void run() {
                        timer.changeTime(0, 0, 0, -CountdownTimer.oneCentisecond);
                    }
                }, new Runnable() {         // Update Time
                    public void run() {
                        if (timer != null) {
                            timeRemaining.setText(timer.getTime(true, true, true, true));
                        }
                    }
                }, new Runnable() {
                    public void run() {     // Perform on close
                        showResult();
                    }
                }
        );
        timer.updateTime();
        timer.start();
        
        setVisible(true);
    }

    private void showResult() {
        timer.stop();
        QuestionDialog.this.setVisible(false);
        String message = "";
        if (gaveUp) {
            message = "You gave up. Try harder next time!";
        } else if (timer.isOutOfTime()) {
            message = "You ran out of time. Work faster next time!";
        } else if (userAnswer == correctAnswer) {
            message = "Correct! You had " + timer.getSeconds() + "." + timer.getCentiseconds() + " seconds remaining.";
        } else {
            message = "Incorrect. Your answer was " + userAnswer + ".";
        }
        message += "\n" + problem.getText() + correctAnswer;
        JOptionPane.showMessageDialog(frame, message);
    }

    public boolean answerIsCorrect() {
        if (gaveUp || timer.isOutOfTime()) {
            return false;
        } else if (userAnswer == correctAnswer) {
            return true;
        } else {
            return false;
        }
    }
}