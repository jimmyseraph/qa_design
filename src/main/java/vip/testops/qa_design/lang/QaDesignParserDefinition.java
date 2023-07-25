package vip.testops.qa_design.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignLanguage;
import vip.testops.qa_design.lang.parser.QaDesignParser;
import vip.testops.qa_design.lang.psi.QaDesignFile;
import vip.testops.qa_design.lang.psi.QaDesignTokenSets;
import vip.testops.qa_design.lang.psi.QaDesignTokenType;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

public class QaDesignParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(QaDesignLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new QaDesignLexerAdapter();
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return QaDesignTokenSets.COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return QaDesignTokenSets.LINKED_METHOD_VALUES;
    }

    @NotNull
    @Override
    public PsiParser createParser(final Project project) {
        return new QaDesignParser();
    }

    @NotNull
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new QaDesignFile(viewProvider);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return QaDesignTypes.Factory.createElement(node);
    }
}
