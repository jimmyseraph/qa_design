package vip.testops.qa_design.run.configuration;

import com.intellij.execution.Location;
import com.intellij.execution.PsiLocation;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.LazyRunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.ClassUtil;
import com.theoryinpractice.testng.model.TestData;
import com.theoryinpractice.testng.model.TestType;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.utils.ReferenceUtil;


public class QaDesignRunConfigurationProducer extends LazyRunConfigurationProducer<QaDesignDebugConfiguration> {
    @NotNull
    @Override
    public ConfigurationFactory getConfigurationFactory() {
        return QaDesignDebugConfigurationType.getInstance();
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull QaDesignDebugConfiguration configuration, @NotNull ConfigurationContext context, @NotNull Ref<PsiElement> sourceElement) {
        PsiMethod sourceMethod = getTargetMethod(context);
        if(sourceMethod == null) return false;
        final Pair<String, String> position = getPosition(sourceMethod);
        if (position != null) {
            final Project project = configuration.getProject();
            PsiFile file = sourceMethod.getContainingFile();
            Module targetModule = ModuleUtilCore.findModuleForFile(file.getVirtualFile(), project);
            final Location<?> contextLocation = context.getLocation();
            assert contextLocation != null;
            PsiElement element = contextLocation.getPsiElement();
            configuration.beFromQaDesignPosition(new PsiLocation<>(element));
            Pair<String, String> pos = getPosition(configuration);
            configuration.setName("Tests for " + StringUtil.getShortName(position.first) + "." + pos.second);
            configuration.setModule(targetModule);
            return true;
        }
        return false;

    }

    @Override
    public boolean isConfigurationFromContext(@NotNull QaDesignDebugConfiguration configuration, @NotNull ConfigurationContext context) {
        PsiMethod sourceMethod = getTargetMethod(context);
        if (sourceMethod == null) return false;
        final Pair<String, String> position = getPosition(sourceMethod);
        return position != null && position.equals(getPosition(configuration));
    }

    protected Pair<String, String> getPosition(QaDesignDebugConfiguration configuration) {
        final TestData data = configuration.getPersistantData();
        if (data.TEST_OBJECT.equals(TestType.METHOD.getType())) {
            return Pair.create(data.getMainClassName(), data.getMethodName());
        }
        return null;
    }

    private static Pair<String, String> getPosition(PsiMethod method) {
        if (method == null) {
            return null;
        }
        final PsiClass containingClass = method.getContainingClass();
        if (containingClass == null) {
            return null;
        }
        final String qualifiedName = ClassUtil.getJVMClassName(containingClass);
        if (qualifiedName != null) {
            return Pair.create(qualifiedName, method.getName());
        }
        return null;
    }

    private static PsiMethod getTargetMethod(@NotNull ConfigurationContext context) {
        final Location<?> contextLocation = context.getLocation();
        assert contextLocation != null;
        PsiElement element = contextLocation.getPsiElement();
        PsiElement link = ReferenceUtil.getLink(element);
        if(link == null) return null;
        PsiElement refElement = ReferenceUtil.getReference(link);
        if(! (refElement instanceof PsiMethod sourceMethod)) {
            return null;
        }
        return sourceMethod;
    }

}
