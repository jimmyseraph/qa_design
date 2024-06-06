package vip.testops.qa_design.lang.reference.providers;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteral;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.lang.reference.MethodReference;


public class TestNGMethodReferenceProvider extends JavaClassReferenceProvider {

    @Override
    public PsiReference @NotNull [] getReferencesByString(String str, @NotNull PsiElement position, int offsetInPosition) {
        return super.getReferencesByString(str, position, offsetInPosition);
    }
}
