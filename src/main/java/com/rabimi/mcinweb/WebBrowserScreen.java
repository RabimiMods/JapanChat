package com.rabimi.mcinweb;

// クラス名を MCEFBrowser に合わせてインポート
import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.MCEFBrowser; 
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class WebBrowserScreen extends Screen {
    // ここを Browser から MCEFBrowser に変更
    private MCEFBrowser browser;

    protected WebBrowserScreen() {
        super(Text.literal("Web Browser"));
    }

    @Override
    protected void init() {
        super.init();
        if (this.browser == null) {
            // MCEFBrowser 型として作成
            this.browser = MCEF.BROWSER_SERVICE.createBrowser("https://www.google.com");
        }
        if (this.browser != null) {
            this.browser.resize(this.width, this.height);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (this.browser != null) {
            // 描画メソッド名は draw であることが多いですが、
            // もしエラーが出る場合は render か paint か確認が必要です
            this.browser.draw(context, mouseX, mouseY);
        }
    }

    @Override
    public void close() {
        if (this.browser != null) {
            this.browser.close();
        }
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
