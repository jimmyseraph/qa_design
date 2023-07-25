package vip.testops.qa_design.lang;

import com.intellij.lang.ASTFactory;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.psi.impl.source.tree.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.lang.psi.LinkedMethodValueElement;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

public class QaDesignASTFactory extends ASTFactory {
    @Override
    public @Nullable CompositeElement createComposite(@NotNull IElementType type) {
        if (type instanceof IFileElementType) {
            return new FileElement(type, null);
        }
        return new CompositeElement(type);
    }

    @Override
    public @Nullable LeafElement createLeaf(@NotNull IElementType type, @NotNull CharSequence text) {
        if (type == QaDesignTypes.LINKED_METHOD_VALUE) {
            return new LinkedMethodValueElement(type, text);
        }
        ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(type.getLanguage());
        if (parserDefinition != null && parserDefinition.getCommentTokens().contains(type)) {
            return new PsiCommentImpl(type, text);
        }

        return new LeafPsiElement(type, text);
    }
}
