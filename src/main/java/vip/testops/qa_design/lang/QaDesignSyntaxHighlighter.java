package vip.testops.qa_design.lang;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class QaDesignSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey SEPARATOR =
            createTextAttributesKey("QA_DESIGN_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey KEY =
            createTextAttributesKey("QA_DESIGN_KEY", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey CONTENT =
            createTextAttributesKey("QA_DESIGN_CONTENT", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("QA_DESIGN_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("QA_DESIGN_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);


    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEY};
    private static final TextAttributesKey[] CONTENT_KEYS = new TextAttributesKey[]{CONTENT};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new QaDesignLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(QaDesignTypes.SEPARATOR)) {
            return SEPARATOR_KEYS;
        }
        if (tokenType.equals(QaDesignTypes.REQUIREMENT_KEY) ||
                tokenType.equals(QaDesignTypes.TEST_POINT_KEY) ||
                tokenType.equals(QaDesignTypes.TEST_CASE_DATA_KEY) ||
                tokenType.equals(QaDesignTypes.TEST_CASE_STEP_KEY) ||
                tokenType.equals(QaDesignTypes.TEST_CASE_NAME_KEY) ||
                tokenType.equals(QaDesignTypes.TEST_CASE_EXPECT_KEY) ||
                tokenType.equals(QaDesignTypes.TEST_CASE_DESC_KEY)
        ) {
            return KEY_KEYS;
        }
        if (tokenType.equals(QaDesignTypes.CONTENT)) {
            return CONTENT_KEYS;
        }
        if (tokenType.equals(QaDesignTypes.COMMENT)) {
            return COMMENT_KEYS;
        }
        if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        }
        return EMPTY_KEYS;
    }
}
