package com.rabimi.japanchat;

import java.io.IOException;

public class FcitxController {
    public static void setFcitxState(boolean active) {
        try {
            String state = active ? "2" : "1";
            new ProcessBuilder(
                "dbus-send",
                "--session",
                "--dest=org.fcitx.Fcitx5",
                "--type=method_call",
                "/remote",
                "org.fcitx.Fcitx.Remote1.SetCurrentIMState",
                "int32:" + state
            ).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}