package com.rabimi.mcinweb;

import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.MCEFBrowser;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier; // class_2960 の代わりに Identifier を使用

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
            // Identifier クラスとして取得
            Identifier texture = (Identifier) (Object) this.browser.getTextureIdentifier();
            
            if (texture != null) {
                context.drawTexture(texture, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
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
}
