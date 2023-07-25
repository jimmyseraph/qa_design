package vip.testops.qa_design.run.configuration;

import com.intellij.execution.Location;
import com.intellij.execution.RunManager;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.SimpleConfigurationType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.theoryinpractice.testng.configuration.TestNGConfiguration;
import com.theoryinpractice.testng.model.TestData;
import com.theoryinpractice.testng.model.TestNGTestObject;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignIcons;
import vip.testops.qa_design.utils.ReferenceUtil;

import javax.swing.*;

public class QaDesignDebugConfigurationType extends SimpleConfigurationType {
    protected QaDesignDebugConfigurationType() {
        super("QaDesigner", "QaDesigner", null, NotNullLazyValue.createValue(() -> QaDesignIcons.TEST_CASE_ICON));
    }

    @Override
    public boolean isEditableInDumbMode() {
        return true;
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new QaDesignDebugConfiguration(project, this);
    }

    @NotNull
    @Override
    public String getTag() {
        return "QaDesigner";
    }

    @Override
    public String getHelpTopic() {
        return "reference.dialogs.rundebug.TestNG";
    }

    @NotNull
    public static QaDesignDebugConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(QaDesignDebugConfigurationType.class);
    }

    public boolean isConfigurationByLocation(RunConfiguration runConfiguration, Location<PsiElement> location) {
        QaDesignDebugConfiguration config = (QaDesignDebugConfiguration) runConfiguration;
        TestData testObject = config.getPersistantData();
        if (testObject == null) {
            return false;
        }
        final PsiElement element = location.getPsiElement();
        PsiElement linkElement = ReferenceUtil.getLink(element);
        if(linkElement == null) {
            return false;
        }
        PsiElement javaMethodElement = ReferenceUtil.getReference(linkElement);
        final TestNGTestObject testNGTestObject = TestNGTestObject.fromConfig(config);
        if (testNGTestObject.isConfiguredByElement(javaMethodElement)) {
            final Module configurationModule = config.getConfigurationModule().getModule();
            if (Comparing.equal(location.getModule(), configurationModule)) return true;

            final Module predefinedModule =
                    ((TestNGConfiguration) RunManager.getInstance(location.getProject()).getConfigurationTemplate(getConfigurationFactories()[0])
                            .getConfiguration()).getConfigurationModule().getModule();
            return Comparing.equal(predefinedModule, configurationModule);
        }
        else {
            return false;
        }
    }

}
