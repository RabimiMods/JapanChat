package com.rabimi.japanchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class InputWindow {
    public static void open(Consumer<String> onConfirm) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JapanChat - 日本語入力");
            frame.setAlwaysOnTop(true);
            frame.setUndecorated(false);
            frame.setSize(500, 120);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);

            JTextField textField = new JTextField();
            textField.setFont(new Font("SansSerif", Font.PLAIN, 20));
            frame.add(textField, BorderLayout.CENTER);

            JLabel label = new JLabel(" 日本語を入力してEnterで送信（Escでキャンセル）");
            frame.add(label, BorderLayout.NORTH);

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
            
            frame.toFront();
            frame.requestFocus();
            textField.requestFocusInWindow();
        });
    }
}