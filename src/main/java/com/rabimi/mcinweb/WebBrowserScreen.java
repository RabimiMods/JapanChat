package com.rabimi.mcinweb;

import de.keksuccino.mcef.Main;
import de.keksuccino.mcef.browser.Browser;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class WebBrowserScreen extends Screen {
    private Browser browser;

    protected WebBrowserScreen() {
        super(Text.literal("Web Browser"));
    }

    @Override
    protected void init() {
        super.init();
        // ブラウザの初期化（URLを指定）
        if (this.browser == null) {
            this.browser = Main.BROWSER_SERVICE.createBrowser("https://www.google.com");
        }
        // 画面サイズに合わせてブラウザのサイズを調整
        if (this.browser != null) {
            this.browser.resize(this.width, this.height);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // ブラウザを描画
        if (this.browser != null) {
            this.browser.draw(context, mouseX, mouseY);
        }
    }

    @Override
    public void close() {
        // 画面を閉じるときにブラウザを破棄（メモリリーク防止）
        if (this.browser != null) {
            this.browser.close();
        }
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false; // ブラウザを開いている間もゲームを止めない
    }
}
