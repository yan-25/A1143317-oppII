import javax.swing.*;
import java.awt.*;

public class hw1 {
    public static void main(String[] args) {

        // 建立視窗
        JFrame frame = new JFrame("骰子模擬器");

        // 視窗大小
        frame.setSize(400, 320);

        // 關閉視窗時結束程式
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 使用 BorderLayout
        frame.setLayout(new BorderLayout());

        // 中央顯示目前點數
        JLabel diceLabel = new JLabel("1", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
        frame.add(diceLabel, BorderLayout.CENTER);

        // 下方「擲骰子」按鈕
        JButton rollButton = new JButton("擲骰子");
        frame.add(rollButton, BorderLayout.SOUTH);

        // 視窗置中
        frame.setLocationRelativeTo(null);

        // 顯示視窗
        frame.setVisible(true);
    }
}