import java.awt.*;
import java.util.Locale;
import java.util.Random;
import javax.swing.*;

public class DiceSimulator extends JFrame {

    private final Random random = new Random();

    private final JLabel statsLabel = new JLabel();
    private final JLabel diceLabel =
            new JLabel("?", SwingConstants.CENTER);

    private int rollCount = 0;
    private int total = 0;

    public DiceSimulator() {
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 上方統計資訊
        statsLabel.setHorizontalAlignment(
                SwingConstants.CENTER);
        statsLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 16));
        statsLabel.setBorder(
                BorderFactory.createEmptyBorder(18, 8, 8, 8));
        updateStats();
        add(statsLabel, BorderLayout.NORTH);

        // 中央骰子點數
        diceLabel.setFont(
                new Font("SansSerif", Font.BOLD, 60));
        diceLabel.setForeground(Color.BLACK);
        add(diceLabel, BorderLayout.CENTER);

        // 下方擲骰按鈕
        JButton rollButton = new JButton("擲骰子");
        rollButton.setFont(
                new Font("SansSerif", Font.PLAIN, 18));
        rollButton.setPreferredSize(
                new Dimension(150, 45));

        rollButton.addActionListener(e -> rollDice());

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 15));
        buttonPanel.add(rollButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void rollDice() {
        // 隨機產生1到6
        int points = random.nextInt(6) + 1;

        rollCount++;
        total += points;

        diceLabel.setText(String.valueOf(points));

        // 設定點數顏色
        if (points == 6) {
            diceLabel.setForeground(
                    new Color(0, 150, 0));
        } else if (points == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }

        updateStats();
    }

    private void updateStats() {
        double average = (rollCount == 0)
                ? 0.0 : (double) total / rollCount;

        statsLabel.setText(String.format(
                Locale.US,
                "已擲 %d 次，總和 %d，平均 %.2f",
                rollCount, total, average));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DiceSimulator window = new DiceSimulator();
            window.setVisible(true);
        });
    }
}