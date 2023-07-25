package vip.testops.qa_design.lang.psi;

import com.intellij.psi.tree.TokenSet;

public interface QaDesignTokenSets {

    TokenSet CONTENTS = TokenSet.create(QaDesignTypes.CONTENT);

    TokenSet RULE_FIRST_LINES = TokenSet.create(QaDesignTypes.RULE_FIRST_LINE);

    TokenSet RULE_LINKED_METHODS = TokenSet.create(QaDesignTypes.RULE_LINKED_METHOD);

    TokenSet LINKED_METHOD_VALUES = TokenSet.create(QaDesignTypes.LINKED_METHOD_VALUE);

    TokenSet COMMENTS = TokenSet.create(QaDesignTypes.COMMENT);

}
