package com.rabimi.mcinweb;

import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.MCEFBrowser;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.class_2960; // Identifier の難読化名

public class WebBrowserScreen extends Screen {
    private MCEFBrowser browser;

    protected WebBrowserScreen() {
        super(Text.literal("Web Browser"));
    }

    @Override
    protected void init() {
        super.init();
        if (this.browser == null) {
            // MCEF.java に定義されていた正しい作成メソッド
            this.browser = MCEF.createBrowser("https://www.google.com", false);
        }
        if (this.browser != null) {
            this.browser.resize(this.width, this.height);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        
        if (this.browser != null && this.browser.isTextureReady()) {
            // MCEFBrowser.java の 88行目にある getTextureIdentifier() を使用
            class_2960 texture = this.browser.getTextureIdentifier();
            
            if (texture != null) {
                // 画面全体にブラウザのテクスチャを描画
                context.drawTexture(texture, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
            }
        }
    }

    @Override
    public void close() {
        // MCEFBrowser.java の 485行目にある close() を呼び出し
        if (this.browser != null) {
            this.browser.close();
        }
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    // マウス入力などのイベントをブラウザに送る（これがないと操作できません）
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.browser != null) {
            this.browser.sendMousePress((int) mouseX, (int) mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.browser != null) {
            this.browser.sendMouseRelease((int) mouseX, (int) mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (this.browser != null) {
            this.browser.sendMouseMove((int) mouseX, (int) mouseY);
        }
    }
}
