package vip.testops.qa_design.utils;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import vip.testops.qa_design.QaDesignBundle;

public final class SensitiveDataUtil {

    public static CredentialAttributes createCredentialAttributes(String key) {
        return new CredentialAttributes(
                CredentialAttributesKt.generateServiceName(QaDesignBundle.message("credential.qa_design.serviceName"), key)
        );
    }
}
