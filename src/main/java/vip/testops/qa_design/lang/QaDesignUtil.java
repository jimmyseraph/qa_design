package vip.testops.qa_design.lang;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
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

    /**
     * Attempts to collect any comment elements above the Simple key/value pair.
     */
//    public static @NotNull String findDocumentationComment(SimpleProperty property) {
//        List<String> result = new LinkedList<>();
//        PsiElement element = property.getPrevSibling();
//        while (element instanceof PsiComment || element instanceof PsiWhiteSpace) {
//            if (element instanceof PsiComment) {
//                String commentText = element.getText().replaceFirst("[!# ]+", "");
//                result.add(commentText);
//            }
//            element = element.getPrevSibling();
//        }
//        return StringUtil.join(Lists.reverse(result),"\n ");
//    }
}
