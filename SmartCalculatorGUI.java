import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmartCalculatorGUI {

    private JTextField display;
    private double num1;
    private String operator;

    public SmartCalculatorGUI() {
        JFrame frame = new JFrame("Smart Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("\\d")) { // If the command is a number
                display.setText(display.getText() + command);
            } else if (command.equals("C")) { // Clear
                display.setText("");
                operator = "";
                num1 = 0;
            } else if (command.equals("=")) { // Calculate the result
                try {
                    double num2 = Double.parseDouble(display.getText());
                    double result = calculate(num1, num2, operator);
                    display.setText(Double.toString(result));
                } catch (NumberFormatException ex) {
                    display.setText("Error");
                } catch (IllegalArgumentException ex) {
                    display.setText(ex.getMessage());
                }
            } else { // Operator (+, -, *, /)
                try {
                    num1 = Double.parseDouble(display.getText());
                    operator = command;
                    display.setText("");
                } catch (NumberFormatException ex) {
                    display.setText("Error");
                }
            }
        }
    }

    private double calculate(double num1, double num2, String operation) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartCalculatorGUI::new);
    }
}
