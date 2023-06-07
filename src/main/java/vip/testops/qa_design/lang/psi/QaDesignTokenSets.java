package vip.testops.qa_design.lang.psi;

import com.intellij.psi.tree.TokenSet;

public interface QaDesignTokenSets {
    TokenSet CONTENTS = TokenSet.create(QaDesignTypes.CONTENT);

    TokenSet COMMENTS = TokenSet.create(QaDesignTypes.COMMENT);

}
