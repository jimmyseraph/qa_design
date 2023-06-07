package vip.testops.qa_design.lang;

import com.intellij.lexer.FlexAdapter;

public class QaDesignLexerAdapter extends FlexAdapter {
    public QaDesignLexerAdapter() {
        super(new QaDesignLexer(null));
    }
}
