package vip.testops.qa_design.lang;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignFileType;
import vip.testops.qa_design.lang.psi.QaDesignFile;
import vip.testops.qa_design.lang.psi.QaDesignRuleFirstLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QaDesignUtil {
    public static List<QaDesignRuleFirstLine> findSubReqs(Project project, String key) {
        List<QaDesignRuleFirstLine> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(QaDesignFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            QaDesignFile file = (QaDesignFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (file != null) {
                QaDesignRuleFirstLine[] reqs = PsiTreeUtil.getChildrenOfType(file, QaDesignRuleFirstLine.class);
                if (reqs != null) {
                    Collections.addAll(result, reqs);
                }
            }
        }
        return result;
    }

    public static List<QaDesignRuleFirstLine> findSubReqs(Project project) {
        List<QaDesignRuleFirstLine> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(QaDesignFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            QaDesignFile file = (QaDesignFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (file != null) {
                QaDesignRuleFirstLine[] reqs = PsiTreeUtil.getChildrenOfType(file, QaDesignRuleFirstLine.class);
                if (reqs != null) {
                    Collections.addAll(result, reqs);
                }
            }
        }
        return result;
    }

    public static Integer getLineNumber(@NotNull PsiElement element) {
        PsiFile containingFile = element.getContainingFile();
        Project project = containingFile.getProject();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
        Document document = psiDocumentManager.getDocument(containingFile);
        int textOffset = element.getTextOffset();
        if (document == null) {
            return null;
        }
        return document.getLineNumber(textOffset) + 1;
    }
}
