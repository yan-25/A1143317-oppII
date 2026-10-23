import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.DefaultComboBoxModel;

public class UnitConverter extends JFrame {

    // 三種換算類型與各自可選的單位
    private static final String[] LENGTH_UNITS = {"公尺", "公分", "英吋", "英尺"};
    private static final String[] WEIGHT_UNITS = {"公斤", "公克", "磅", "盎司"};
    private static final String[] TEMP_UNITS = {"攝氏", "華氏", "克氏"};

    // 長度全部先換成公尺；重量全部先換成公斤
    private static final double[] LENGTH_TO_METERS = {1.0, 0.01, 0.0254, 0.3048};
    private static final double[] WEIGHT_TO_KG = {1.0, 0.001, 0.45359237, 0.028349523125};

    private final JComboBox<String> categoryBox =
            new JComboBox<>(new String[]{"長度", "重量", "溫度"});
    private final JTextField sourceField = new JTextField();
    private final JTextField resultField = new JTextField();
    private final JComboBox<String> sourceUnitBox = new JComboBox<>();
    private final JComboBox<String> targetUnitBox = new JComboBox<>();

    public UnitConverter() {
        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 8));

        // NORTH：選擇長度、重量或溫度
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        JLabel categoryLabel = new JLabel("換算類型：");
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        categoryBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        northPanel.add(categoryLabel);
        northPanel.add(categoryBox);
        add(northPanel, BorderLayout.NORTH);

        // CENTER：兩列，每列包含一個 JTextField 與一個 JComboBox
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 12));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
        sourceField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        resultField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        resultField.setEditable(false); // 第二個 JTextField 專門顯示換算結果
        sourceUnitBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        targetUnitBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        sourceUnitBox.setPreferredSize(new Dimension(135, 45));
        targetUnitBox.setPreferredSize(new Dimension(135, 45));

        JPanel sourceRow = new JPanel(new BorderLayout(10, 0));
        sourceField.setBorder(BorderFactory.createTitledBorder("來源數值"));
        sourceRow.add(sourceField, BorderLayout.CENTER);
        sourceRow.add(sourceUnitBox, BorderLayout.EAST);

        JPanel targetRow = new JPanel(new BorderLayout(10, 0));
        resultField.setBorder(BorderFactory.createTitledBorder("換算結果"));
        targetRow.add(resultField, BorderLayout.CENTER);
        targetRow.add(targetUnitBox, BorderLayout.EAST);

        centerPanel.add(sourceRow);
        centerPanel.add(targetRow);
        add(centerPanel, BorderLayout.CENTER);

        // SOUTH：按下按鈕，結果顯示在第二個 JTextField
        JButton convertButton = new JButton("換算");
        convertButton.setFont(new Font("SansSerif", Font.PLAIN, 17));
        convertButton.setPreferredSize(new Dimension(140, 38));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        southPanel.add(convertButton);
        add(southPanel, BorderLayout.SOUTH);

        // 切換類型時，來源和目標的單位選單都一起更新
        categoryBox.addActionListener(e -> updateUnitOptions());
        sourceUnitBox.addActionListener(e -> resultField.setText(""));
        targetUnitBox.addActionListener(e -> resultField.setText(""));
        convertButton.addActionListener(e -> convertAndDisplay());
        sourceField.addActionListener(e -> convertAndDisplay()); // 也可按 Enter 換算

        updateUnitOptions();
    }

    private void updateUnitOptions() {
        String category = (String) categoryBox.getSelectedItem();
        String[] units;
        if ("重量".equals(category)) {
            units = WEIGHT_UNITS;
        } else if ("溫度".equals(category)) {
            units = TEMP_UNITS;
        } else {
            units = LENGTH_UNITS;
        }

        sourceUnitBox.setModel(new DefaultComboBoxModel<>(units));
        targetUnitBox.setModel(new DefaultComboBoxModel<>(units));
        targetUnitBox.setSelectedIndex(1); // 預設與來源單位不同
        resultField.setText("");           // 避免留下前一次換算的結果
    }

    private void convertAndDisplay() {
        try {
            double input = Double.parseDouble(sourceField.getText().trim());
            if (!Double.isFinite(input)) {
                throw new NumberFormatException("請輸入有限的數值");
            }

            String category = (String) categoryBox.getSelectedItem();
            String from = (String) sourceUnitBox.getSelectedItem();
            String to = (String) targetUnitBox.getSelectedItem();
            double answer = convert(category, input, from, to);

            if (!Double.isFinite(answer)) {
                throw new IllegalArgumentException("換算結果超出可顯示範圍");
            }
            DecimalFormat format = new DecimalFormat(
                    "0.##########", DecimalFormatSymbols.getInstance(Locale.US));
            resultField.setText(format.format(answer));
        } catch (NumberFormatException ex) {
            resultField.setText("");
            JOptionPane.showMessageDialog(this, "請輸入有效的數字！", "輸入錯誤",
                    JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            resultField.setText("");
            JOptionPane.showMessageDialog(this, ex.getMessage(), "換算錯誤",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // 將換算獨立成方法，較容易閱讀及測試
    public static double convert(String category, double value, String from, String to) {
        if ("長度".equals(category)) {
            return convertByBase(value, from, to, LENGTH_UNITS, LENGTH_TO_METERS);
        } else if ("重量".equals(category)) {
            return convertByBase(value, from, to, WEIGHT_UNITS, WEIGHT_TO_KG);
        } else if ("溫度".equals(category)) {
            double celsius;
            if ("攝氏".equals(from)) {
                celsius = value;
            } else if ("華氏".equals(from)) {
                celsius = (value - 32.0) * 5.0 / 9.0;
            } else if ("克氏".equals(from)) {
                celsius = value - 273.15;
            } else {
                throw new IllegalArgumentException("不支援的來源單位");
            }

            if ("攝氏".equals(to)) {
                return celsius;
            } else if ("華氏".equals(to)) {
                return celsius * 9.0 / 5.0 + 32.0;
            } else if ("克氏".equals(to)) {
                return celsius + 273.15;
            }
            throw new IllegalArgumentException("不支援的目標單位");
        }
        throw new IllegalArgumentException("不支援的換算類型");
    }

    private static double convertByBase(double value, String from, String to,
                                        String[] names, double[] toBase) {
        int fromIndex = -1;
        int toIndex = -1;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(from)) fromIndex = i;
            if (names[i].equals(to)) toIndex = i;
        }
        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException("不支援的單位");
        }
        return value * toBase[fromIndex] / toBase[toIndex];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UnitConverter window = new UnitConverter();
            window.setVisible(true);
        });
    }
}
