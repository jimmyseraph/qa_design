package vip.testops.qa_design;

import com.intellij.lang.Language;

public class QaDesignLanguage extends Language {
    public static final QaDesignLanguage INSTANCE = new QaDesignLanguage();
    protected QaDesignLanguage() {
        super("QaDesign");
    }
}
