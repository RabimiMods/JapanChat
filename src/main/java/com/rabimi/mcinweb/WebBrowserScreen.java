package com.rabimi.mcinweb;

import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.MCEFBrowser;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RenderPipeline;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WebBrowserScreen extends Screen {
    private MCEFBrowser browser;

    protected WebBrowserScreen() {
        super(Text.literal("Web Browser"));
    }

    @Override
    protected void init() {
        super.init();
        if (this.browser == null) {
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
            Identifier texture = (Identifier) (Object) this.browser.getTextureIdentifier();
            
            if (texture != null) {
                // 最新の drawTexture 仕様 (RenderLayer/Pipeline を使用)
                // 最もシンプルな全体描画メソッドに切り替えます
                context.drawTexture(RenderPipeline.getGui(), texture, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
            }
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

    /* * 最新バージョンでは mouseClicked(Click click, boolean bl) のような形式になっています。
     * 入力処理の完全な修正にはさらに詳細なクラス情報が必要なため、
     * まずは「ビルドを通すこと（描画を確認すること）」を最優先し、
     * 一旦マウスイベントをコメントアウトします。
     */
     
    /*
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.browser != null) {
            this.browser.sendMousePress((int) mouseX, (int) mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    */
}
