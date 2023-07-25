package vip.testops.qa_design.lang.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LeafElementManipulator<T extends LeafPsiElement> extends AbstractElementManipulator<T> {
    @Override
    public @Nullable T handleContentChange(@NotNull T element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
        String text = element.getText();
        String newText = text.substring(0, range.getStartOffset()) + newContent + text.substring(range.getEndOffset());
        return (T)element.replaceWithText(newText);
    }
}
