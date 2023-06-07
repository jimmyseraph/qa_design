package vip.testops.qa_design.lang;

import com.intellij.formatting.*;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignLanguage;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

public class QaDesignFormattingModelBuilder implements FormattingModelBuilder {
    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, QaDesignLanguage.INSTANCE)
                .before(QaDesignTypes.SEPARATOR).spaceIf(false)
                .after(QaDesignTypes.SEPARATOR).spaceIf(true);
    }

    @Override
    public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
        final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
        return FormattingModelProvider
                .createFormattingModelForPsiFile(formattingContext.getContainingFile(),
                        new QaDesignBlock(formattingContext.getNode(),
                                Wrap.createWrap(WrapType.NONE, false),
                                Alignment.createAlignment(),
                                createSpaceBuilder(codeStyleSettings), 0),
                        codeStyleSettings);
    }
}
