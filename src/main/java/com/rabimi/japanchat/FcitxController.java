package com.rabimi.japanchat;

import java.io.IOException;

public class FcitxController {

    public static void setFcitxState(boolean active) {
        String method = active ? "FocusIn" : "FocusOut";
        try {
            new ProcessBuilder(
                "dbus-send",
                "--session",
                "--dest=org.fcitx.Fcitx5",
                "--type=method_call",
                "/controller",
                "org.fcitx.Fcitx.Controller1." + method
            ).start();
        } catch (IOException e) {

        }
    }
}