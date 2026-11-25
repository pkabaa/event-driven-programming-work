package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {
    @FXML private TextField display;

    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @FXML
    private void handleNumber(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String digit = btn.getText();

        if (startNewNumber) {
            display.setText(digit);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + digit);
        }
    }

    @FXML
    private void handleOperator(ActionEvent event) {
        Button btn = (Button) event.getSource();
        firstOperand = Double.parseDouble(display.getText());
        operator = btn.getText();
        startNewNumber = true;
    }

    @FXML
    private void handleEquals(ActionEvent event) {
        double secondOperand = Double.parseDouble(display.getText());
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    display.setText("Error");
                    startNewNumber = true;
                    return;
                }
                break;
            default:
                return;
        }

        display.setText(String.valueOf(result));
        startNewNumber = true;
    }

    @FXML
    private void handleClear(ActionEvent event) {
        display.setText("0");
        firstOperand = 0;
        operator = "";
        startNewNumber = true;
    }
}