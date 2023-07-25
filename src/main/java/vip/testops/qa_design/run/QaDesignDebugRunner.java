package vip.testops.qa_design.run;

import com.intellij.execution.JavaTestFrameworkDebuggerRunner;
import com.intellij.execution.configurations.RunProfile;
import com.theoryinpractice.testng.configuration.TestNGConfiguration;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.run.configuration.QaDesignDebugConfiguration;
//import vip.testops.qa_design.run.configuration.QaDesignDebugConfiguration;

public class QaDesignDebugRunner extends JavaTestFrameworkDebuggerRunner {
    @Override
    protected boolean validForProfile(@NotNull RunProfile profile) {
        return profile instanceof QaDesignDebugConfiguration;
    }

    @NotNull
    @Override
    protected String getThreadName() {
        return "QaDesigner";
    }

    @NotNull
    @Override
    public String getRunnerId() {
        return "QaDesignerDebug";
    }
}
