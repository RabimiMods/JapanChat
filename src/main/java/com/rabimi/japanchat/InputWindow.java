package com.rabimi.japanchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class InputWindow {
    public static void open(Consumer<String> onConfirm) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("日本語入力 - JapanChat");
            frame.setAlwaysOnTop(true);
            frame.setSize(400, 80);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);

            JTextField textField = new JTextField();
            textField.setFont(new Font("SansSerif", Font.PLAIN, 20));
            frame.add(textField, BorderLayout.CENTER);

            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        onConfirm.accept(textField.getText());
                        frame.dispose();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        frame.dispose();
                    }
                }
            });

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            textField.requestFocus();
        });
    }
}