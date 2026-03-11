package com.rabimi.mcinweb;

import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.MCEFBrowser;
import net.minecraft.client.gui.DrawContext;
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
            // ブラウザの初期化
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
            // MCEFからテクスチャIDを取得 (Identifierへのキャスト)
            Identifier texture = (Identifier) (Object) this.browser.getTextureIdentifier();
            
            if (texture != null) {
                /*
                 * 1.21.11 ドキュメントにある public メソッドを使用
                 * drawTexturedQuad(Identifier sprite, int x1, int y1, int x2, int y2, float u1, float u2, float v1, float v2)
                 */
                context.drawTexturedQuad(
                    texture,         // Identifier
                    0,               // x1 (左)
                    0,               // y1 (上)
                    this.width,      // x2 (右)
                    this.height,     // y2 (下)
                    0.0f,            // u1 (テクスチャ左端)
                    1.0f,            // u2 (テクスチャ右端)
                    0.0f,            // v1 (テクスチャ上端)
                    1.0f             // v2 (テクスチャ下端)
                );
            }
        }
    }

    @Override
    public void close() {
        // 画面を閉じるときにブラウザを解放
        if (this.browser != null) {
            this.browser.close();
        }
        super.close();
    }

    @Override
    public boolean shouldPause() {
        // ブラウザを開いている間もゲームを止めない
        return false;
    }
}
