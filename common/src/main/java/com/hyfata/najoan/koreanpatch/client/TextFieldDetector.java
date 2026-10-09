package com.hyfata.najoan.koreanpatch.client;

import com.hyfata.najoan.koreanpatch.util.TreeSearch;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.List;

public final class TextFieldDetector {
    private TextFieldDetector() {
    }

    public static boolean containsTextWidget(GuiEventListener root) {
        // Dialog inputs live in nested containers rather than screen fields.
        return TreeSearch.containsBfs(
                root,
                node -> node instanceof EditBox || node instanceof MultiLineEditBox,
                node -> node instanceof ContainerEventHandler container
                        ? container.children()
                        : List.of());
    }
}
