package com.rabimi.japanchat;

import java.io.IOException;

public class FcitxController {
    private static boolean lastState = false;

    public static void setFcitxState(boolean active) {
        if (active == lastState) return;

        try {
            String state = active ? "2" : "1";
            
            ProcessBuilder pb = new ProcessBuilder(
                "dbus-send",
                "--session",
                "--dest=org.fcitx.Fcitx5",
                "--type=method_call",
                "/remote",
                "org.fcitx.Fcitx.Remote1.SetCurrentIMState",
                "int32:" + state
            );
            
            pb.start();
            lastState = active;
            
            System.out.println("[JapanChat] Fcitx state set to: " + (active ? "ON" : "OFF"));
        } catch (IOException e) {
            System.err.println("[JapanChat] Failed to call dbus-send. Is it installed?");
            e.printStackTrace();
        }
    }
}