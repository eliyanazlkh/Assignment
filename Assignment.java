import javax.swing.*;
import java.awt.*;

public class Assignment extends JFrame {

    private JTextField inputField;
    private JButton encodeButton;
    private JLabel charCountLabel;
    private JLabel finalShiftLabel;
    private JTextArea resultArea;
    private JLabel statusLabel;
    private Encoded encoder;

    public Assignment() {
        encoder = new Encoded();

        setTitle("String Encoder - G03/DE-G03");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 380);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Top: Input
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Input: "));
        inputField = new JTextField(25);
        topPanel.add(inputField);
        encodeButton = new JButton("Encode");
        topPanel.add(encodeButton);
        add(topPanel, BorderLayout.NORTH);

        // Center: Stats + Result
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(10));
        
        charCountLabel = new JLabel("Non-space characters: -");
        centerPanel.add(charCountLabel);
        
        finalShiftLabel = new JLabel("Final shift: -");
        centerPanel.add(finalShiftLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        
        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        centerPanel.add(new JScrollPane(resultArea));
        
        add(centerPanel, BorderLayout.CENTER);

        // Status
        statusLabel = new JLabel("Ready.");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Listeners
        encodeButton.addActionListener(e -> encode());
        inputField.addActionListener(e -> encode());

        setVisible(true);
    }

    private void encode() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            statusLabel.setText("Enter some text first.");
            statusLabel.setForeground(Color.ORANGE);
            resultArea.setText("Output:\n");
            return;
        }
        
        boolean ok = encoder.encode(input);

        if (ok) {
            int charCount = encoder.getCharCount();
            int groupShift = encoder.generateShift();
            int finalShift = groupShift + charCount;
            
            charCountLabel.setText("Non-space characters: " + charCount);
            finalShiftLabel.setText("Final shift: " + finalShift);
            
            resultArea.setText("Output:\n" + encoder.getResultText());
            statusLabel.setText("Success!");
            statusLabel.setForeground(Color.GREEN);
        } else {
            charCountLabel.setText("Non-space characters: -");
            finalShiftLabel.setText("Final shift: -");
            resultArea.setText("Output:");
            statusLabel.setText("Invalid input: use only a-z, 0-9, spaces.");
            statusLabel.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "Invalid input. Please use only lowercase letters (a-z), digits (0-9), and spaces.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Assignment());
    }
}
