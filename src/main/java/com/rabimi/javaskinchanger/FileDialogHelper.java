package com.rabimi.javaskinchanger;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.lwjgl.system.MemoryUtil;
import java.nio.ByteBuffer;
import org.lwjgl.PointerBuffer;
import java.util.function.Consumer;

public class FileDialogHelper {

    public static void open(String title, Consumer<String> callback) {
        MinecraftClient.getInstance().executeAsync(() -> {
            ByteBuffer titleBuf = MemoryUtil.memUTF8(title);
            ByteBuffer filterDesc = MemoryUtil.memUTF8("PNG Image");
            
            PointerBuffer filterPatterns = MemoryUtil.memAllocPointer(1);
            ByteBuffer pngBuf = MemoryUtil.memUTF8("*.png");
            filterPatterns.put(pngBuf);
            filterPatterns.flip();

            String path = TinyFileDialogs.tinyfd_openFileDialog(
                    titleBuf,
                    null,
                    filterPatterns,
                    filterDesc,
                    false
            );

            MemoryUtil.memFree(pngBuf);
            MemoryUtil.memFree(filterPatterns);
            MemoryUtil.memFree(filterDesc);
            MemoryUtil.memFree(titleBuf);

            callback.accept(path);
        });
    }
}
