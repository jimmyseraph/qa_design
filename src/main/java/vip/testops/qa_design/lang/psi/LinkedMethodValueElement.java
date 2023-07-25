package vip.testops.qa_design.lang.psi;

import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.pom.Navigatable;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class LinkedMethodValueElement extends LeafPsiElement implements Navigatable, HintedReferenceHost {
    public LinkedMethodValueElement(@NotNull IElementType type, @NotNull CharSequence text) {
        super(type, text);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        ProgressIndicatorProvider.checkCanceled();
    }

    @Override
    public PsiReference @NotNull [] getReferences(PsiReferenceService.@NotNull Hints hints) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this, hints);
    }

    @Override
    public PsiReference @NotNull [] getReferences() {
        return getReferences(PsiReferenceService.Hints.NO_HINTS);
    }

    @Override
    public boolean shouldAskParentForReferences(PsiReferenceService.@NotNull Hints hints) {
        return true;
    }


    static class Manipulator extends LeafElementManipulator<LinkedMethodValueElement> {
        Manipulator() {
            super();
        }
    }

}
