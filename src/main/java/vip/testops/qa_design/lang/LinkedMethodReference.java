package vip.testops.qa_design.lang;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LinkedMethodReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference{
    public LinkedMethodReference(@NotNull PsiElement element) {
        super(element, element.getTextRange());
    }

    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        ((PsiNamedElement) myElement).setName(newElementName);
        return myElement;
    }

    @Override
    public @Nullable PsiElement resolve() {
        Project project = getElement().getProject();

        String value = StringUtil.unquoteString(getElement().getText());
        String[] parts = value.split("@");
        String className = parts[0];
        String methodName = parts.length > 1 ? parts[1] : "";

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
        if (psiClass == null) {
            return null;
        }

        PsiMethod[] methods = psiClass.findMethodsByName(methodName, false);
        if(methods.length > 0) {
            return methods[0];
        }
        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        // Provide completion variants for the reference
        Project project = getElement().getProject();
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        PsiShortNamesCache cache = PsiShortNamesCache.getInstance(project);

        List<LookupElement> variants = new ArrayList<>();
        for (String className : cache.getAllClassNames()) {
            for(PsiClass psiClass : JavaPsiFacade.getInstance(project).findClasses(className, scope)) {
                if(psiClass.isValid()) {
                    variants.add(LookupElementBuilder.create(psiClass));
                }
            }
        }
        return variants.toArray();
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Project project = getElement().getProject();

        String value = StringUtil.unquoteString(getElement().getText());
        String[] parts = value.split("@");
        String className = parts[0];
        String methodName = parts.length > 1 ? parts[1] : "";

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
        if (psiClass == null) {
            return ResolveResult.EMPTY_ARRAY;
        }

        PsiMethod[] methods = psiClass.findMethodsByName(methodName, false);
        if(methods.length > 0) {
            return new ResolveResult[]{new PsiElementResolveResult(methods[0])};
        }
        return ResolveResult.EMPTY_ARRAY;
    }


}