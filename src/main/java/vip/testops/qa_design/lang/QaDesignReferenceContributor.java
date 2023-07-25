package vip.testops.qa_design.lang;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

public class QaDesignReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(QaDesignTypes.LINKED_METHOD_VALUE),
                provider
//                new PsiReferenceProvider() {
//            @Override
//            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
//
//                return new PsiReference[]{new LinkedMethodReference(element)};
//            }
//        }
        );
    }
}
