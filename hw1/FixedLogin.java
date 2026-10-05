import javax.swing.*;
import java.awt.event.*;

public class FixedLogin extends JFrame {

    private JTextField accountField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public FixedLogin() {
        setTitle("登入");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel accountLabel = new JLabel("帳號：");
        JLabel passwordLabel = new JLabel("密碼：");

        accountField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("登入");

        accountLabel.setBounds(40, 30, 60, 25);
        accountField.setBounds(100, 30, 140, 25);

        passwordLabel.setBounds(40, 70, 60, 25);
        passwordField.setBounds(100, 70, 140, 25);

        loginButton.setBounds(100, 115, 90, 30);

        add(accountLabel);
        add(accountField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String password = new String(passwordField.getPassword());

                if ("admin".equals(account) && "1234".equals(password)) {
                    JOptionPane.showMessageDialog(
                        FixedLogin.this,
                        "登入成功！",
                        "結果",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        FixedLogin.this,
                        "帳號或密碼錯誤！",
                        "結果",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FixedLogin());
    }
}
