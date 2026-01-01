public static void open(String title, Consumer<String> callback) {
    MinecraftClient.getInstance().executeAsync(() -> {
        // StringをByteBufferに変換
        var titleBuf = MemoryUtil.memUTF8(title);
        var filterDescription = MemoryUtil.memUTF8("PNG Image");
        var filterBuf = MemoryUtil.memAllocPointer(1);
        filterBuf.put(MemoryUtil.memUTF8("*.png")).flip();

        String path = TinyFileDialogs.tinyfd_openFileDialog(
                titleBuf,
                null,
                filterBuf,
                filterDescription,
                false
        );

        // メモリ解放（重要）
        MemoryUtil.memFree(filterBuf.get(0));
        MemoryUtil.memFree(filterBuf);
        MemoryUtil.memFree(titleBuf);
        MemoryUtil.memFree(filterDescription);

        callback.accept(path);
    });
}
