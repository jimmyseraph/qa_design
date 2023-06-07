package vip.testops.qa_design.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.lang.psi.QaDesignNamedElement;

public abstract class QaDesignNamedElementImpl extends ASTWrapperPsiElement implements QaDesignNamedElement {
    public QaDesignNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
