package vip.testops.qa_design.run.configuration;

import com.intellij.execution.*;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.theoryinpractice.testng.configuration.TestNGConfiguration;
import com.theoryinpractice.testng.model.TestData;
import com.theoryinpractice.testng.model.TestType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.utils.ReferenceUtil;

public class QaDesignDebugConfiguration extends TestNGConfiguration {


    public QaDesignDebugConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory) {
        super(project, factory);
    }

    public QaDesignDebugConfiguration(@Nullable String name, @NotNull Project project) {
        super(name, project);
    }

    public QaDesignDebugConfiguration(@NotNull Project project) {
        super(project);
    }

    protected QaDesignDebugConfiguration(String s, Project project, TestData data, ConfigurationFactory factory) {
        super(s, project, data, factory);
    }

    public void beFromQaDesignPosition(PsiLocation<PsiElement> position) {
        PsiElement linkElement = ReferenceUtil.getLink(position.getPsiElement());
        assert linkElement != null;
        PsiMethod methodElement = (PsiMethod) ReferenceUtil.getReference(linkElement);
        PsiLocation<PsiMethod> method = new PsiLocation<>(methodElement);
        setModule(data.setTestMethod(method));
        getPersistantData().TEST_OBJECT = TestType.METHOD.getType();
    }
}
