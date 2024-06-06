package vip.testops.qa_design.lang.reference;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInspection.reference.PsiMemberReference;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.IncorrectOperationException;
import com.theoryinpractice.testng.util.TestNGUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MethodReference extends PsiReferenceBase<PsiElement> implements PsiMemberReference {

    public MethodReference(PsiElement element) {
        super(element, false);
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        if (element instanceof PsiMethod) {
            return handleElementRename(((PsiMethod)element).getName());
        }
        return super.bindToElement(element);
    }

    @Override
    @Nullable
    public PsiElement resolve() {
        @NonNls String val = getValue();
        final String methodName = StringUtil.getShortName(val);
        PsiClass cls = getDependsClass(val);
        if (cls != null) {
            PsiMethod[] methods = cls.findMethodsByName(methodName, true);
            for (PsiMethod method : methods) {
                if (TestNGUtil.hasTest(method, false) || TestNGUtil.hasConfig(method)) {
                    return method;
                }
            }
        }
        return null;
    }

    @Nullable
    private PsiClass getDependsClass(String val) {
        final String className = StringUtil.getPackageName(val);
        final PsiElement element = getElement();
        return StringUtil.isEmpty(className) ? PsiUtil.getTopLevelClass(element)
                : JavaPsiFacade.getInstance(element.getProject()).findClass(className, element.getResolveScope());
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> list = new ArrayList<>();
        @NonNls String val = getValue();
        int hackIndex = val.indexOf(CompletionUtil.DUMMY_IDENTIFIER);
        if (hackIndex > -1) {
            val = val.substring(0, hackIndex);
        }
        final String className = StringUtil.getPackageName(val);
        PsiClass cls = getDependsClass(val);
        if (cls != null) {
            final PsiMethod current = PsiTreeUtil.getParentOfType(getElement(), PsiMethod.class);
            final String configAnnotation = TestNGUtil.getConfigAnnotation(current);
            final PsiMethod[] methods = cls.getMethods();
            for (PsiMethod method : methods) {
                final String methodName = method.getName();
                if (current != null && methodName.equals(current.getName())) continue;
                if (configAnnotation == null && TestNGUtil.hasTest(method) ||
                        configAnnotation != null && AnnotationUtil.isAnnotated(method, configAnnotation, AnnotationUtil.CHECK_HIERARCHY)) {
                    final String nameToInsert = StringUtil.isEmpty(className) ? methodName : StringUtil.getQualifiedName(cls.getQualifiedName(), methodName);
                    list.add(LookupElementBuilder.create(nameToInsert));
                }
            }
        }
        return list.toArray();
    }
}
