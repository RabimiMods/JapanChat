package com.rabimi.javaskinchanger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class SkinChangeScreen extends Screen {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final ArrayList<File> localSkins = new ArrayList<>();

    private static Identifier currentCustomSkin;

    protected SkinChangeScreen() {
        super(Text.literal("JavaSkinChanger"));
    }

    @Override
    protected void init() {
        addDrawableChild(
                ButtonWidget.builder(Text.literal("Upload Skin"), b -> openSkinFile())
                        .dimensions(10, 10, 150, 20)
                        .build()
        );

        addDrawableChild(
                ButtonWidget.builder(Text.literal("Fetch Mojang Skin"), b -> fetchMojangSkin())
                        .dimensions(170, 10, 180, 20)
                        .build()
        );
    }

    private void openSkinFile() {
        ByteBuffer buffer = MemoryUtil.memUTF8("*.png");
        PointerBuffer filters = MemoryUtil.memAllocPointer(1);
        filters.put(bufferPtr).flip();

        String path = TinyFileDialogs.tinyfd_openFileDialog(
                "Select Skin",
                "",
                filters,
                null,
                false
        );

        MemoryUtil.memFree(filters);
        MemoryUtil.memFree(buffer);

        if (path == null) return;
        applySkin(new File(path));
    }

    private void applySkin(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            NativeImage nativeImage = NativeImage.read(toInputStream(img));

            Identifier id = Identifier.of("javaskinchanger", "custom");
            
            client.getTextureManager().registerTexture(
                    id,
                    new NativeImageBackedTexture(UUID.randomUUID()::toString, nativeImage)
            );

            currentCustomSkin = id;
            applySkinToPlayer(client.player, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchMojangSkin() {
        if (client.player == null) return;

        new Thread(() -> {
            try {
                String uuid = client.player.getUuidAsString().replace("-", "");
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String json = br.lines().reduce("", String::concat);

                String base64 = com.google.gson.JsonParser.parseString(json)
                        .getAsJsonObject()
                        .getAsJsonArray("properties")
                        .get(0).getAsJsonObject()
                        .get("value").getAsString();

                String decoded = new String(Base64.getDecoder().decode(base64));
                String skinUrl = com.google.gson.JsonParser.parseString(decoded)
                        .getAsJsonObject()
                        .getAsJsonObject("textures")
                        .getAsJsonObject("SKIN")
                        .get("url").getAsString();

                BufferedImage img = ImageIO.read(new URL(skinUrl));
                NativeImage nativeImage = NativeImage.read(toInputStream(img));

                Identifier id = Identifier.of("javaskinchanger", "mojang");

                client.execute(() -> {
                    client.getTextureManager().registerTexture(
                            id,
                            new NativeImageBackedTexture(UUID.randomUUID()::toString, nativeImage)
                    );
                    currentCustomSkin = id;
                    applySkinToPlayer(client.player, id);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void applySkinToPlayer(@Nullable ClientPlayerEntity player, Identifier skin) {
        if (player == null) return;
    }

    private InputStream toInputStream(BufferedImage img) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }

    public static Identifier getCustomSkin() {
        return currentCustomSkin;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}
