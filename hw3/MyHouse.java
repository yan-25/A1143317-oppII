import javax.swing.*;
import java.awt.*;

public class MyHouse extends JPanel {

    public MyHouse() {
        setPreferredSize(new Dimension(500, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. 天空與草地
        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, 500, 280);

        g.setColor(new Color(76, 175, 80));
        g.fillRect(0, 280, 500, 120);

        // 2. 太陽
        g.setColor(Color.YELLOW);
        g.fillOval(380, 30, 80, 80);

        // 使用 Math.cos 和 Math.sin 畫出8條光芒
        g.setColor(Color.ORANGE);
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI * 2 * i / 8;

            int x1 = (int)(420 + 48 * Math.cos(angle));
            int y1 = (int)(70 + 48 * Math.sin(angle));

            int x2 = (int)(420 + 65 * Math.cos(angle));
            int y2 = (int)(70 + 65 * Math.sin(angle));

            g.drawLine(x1, y1, x2, y2);
        }

        // 3. 雲朵（至少3個互相重疊的白色橢圓）
        g.setColor(Color.WHITE);
        g.fillOval(60, 55, 75, 55);
        g.fillOval(100, 35, 90, 75);
        g.fillOval(155, 55, 75, 55);

        // 4. 房屋主體
        g.setColor(new Color(255, 228, 181));
        g.fillRect(155, 175, 205, 155);

        g.setColor(Color.BLACK);
        g.drawRect(155, 175, 205, 155);

        // 5. 屋頂（三條線組成三角形）
        g.setColor(Color.RED);
        g.drawLine(135, 175, 257, 90);
        g.drawLine(257, 90, 380, 175);
        g.drawLine(135, 175, 380, 175);

        // 6. 門
        g.setColor(new Color(139, 69, 19));
        g.fillRoundRect(225, 245, 55, 85, 15, 15);

        // 門把
        g.setColor(Color.YELLOW);
        g.fillOval(267, 277, 9, 9);

        // 7. 窗戶（兩扇）
        // 左邊窗戶
        g.setColor(Color.WHITE);
        g.fillRect(175, 205, 50, 50);

        g.setColor(Color.BLACK);
        g.drawRect(175, 205, 50, 50);
        g.drawLine(200, 205, 200, 255);
        g.drawLine(175, 230, 225, 230);

        // 右邊窗戶
        g.setColor(Color.WHITE);
        g.fillRect(290, 205, 50, 50);

        g.setColor(Color.BLACK);
        g.drawRect(290, 205, 50, 50);
        g.drawLine(315, 205, 315, 255);
        g.drawLine(290, 230, 340, 230);

        // 8. 學號與姓名簽名
        g.setColor(Color.BLACK);
        g.drawString("A1143317 張晏愷", 10, 385);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("我的小房子");

        MyHouse house = new MyHouse();

        frame.add(house);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}