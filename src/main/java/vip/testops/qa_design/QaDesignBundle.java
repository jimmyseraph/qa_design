package vip.testops.qa_design;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public final class QaDesignBundle extends DynamicBundle {
    public static final String BUNDLE = "messages.qadesign";
    public static final QaDesignBundle INSTANCE = new QaDesignBundle();
    private QaDesignBundle() {
        super(BUNDLE);
    }

    public static @NotNull @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object @NotNull ... params) {
        return INSTANCE.getMessage(key, params);
    }
}
