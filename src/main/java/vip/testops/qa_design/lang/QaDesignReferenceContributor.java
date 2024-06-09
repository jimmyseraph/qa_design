package vip.testops.qa_design.lang;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.JavaLangClassMemberReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.lang.psi.QaDesignTypes;
import vip.testops.qa_design.lang.reference.providers.TestNGMethodReferenceProvider;

import java.util.ArrayList;
import java.util.List;

public class QaDesignReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        final JavaClassReferenceProvider methodSelectorProvider = new JavaClassReferenceProvider();
        List<String> classes = new ArrayList<>();
        classes.add(new String("org.testng.IMethodSelector"));
        methodSelectorProvider.setOption(JavaClassReferenceProvider.SUPER_CLASSES, classes);
        methodSelectorProvider.setOption(JavaClassReferenceProvider.ALLOW_DOLLAR_NAMES, Boolean.TRUE);
        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(QaDesignTypes.LINKED_METHOD_VALUE),
                methodSelectorProvider
        );
//        registrar.registerReferenceProvider(
//                PlatformPatterns.psiElement(QaDesignTypes.LINKED_METHOD_VALUE),
//                new TestNGMethodReferenceProvider()
//        );
    }
}
