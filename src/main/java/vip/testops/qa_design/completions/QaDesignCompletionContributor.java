package vip.testops.qa_design.completions;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QaDesignCompletionContributor extends CompletionContributor {

    public QaDesignCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(
                            @NotNull CompletionParameters parameters,
                            @NotNull ProcessingContext context,
                            @NotNull CompletionResultSet result
                    ) {
                        PsiElement position = parameters.getOriginalPosition();
                        if (position == null) {
                            return;
                        }
                        String nodeType = position.getNode().getElementType().toString();
                        if("QaDesignTokenType.INSIDE".equals(nodeType)) {
                            String msg = position.getParent().toString();
                            if (msg.contains("QaDesignTokenType.FEATURE")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.feature")));
                            }
                            if (msg.contains("QaDesignTokenType.TEST_POINT_KEY")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.test_point")));
                            }
                            if (msg.contains("QaDesignTokenType.TEST_CASE_NAME_KEY")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.testcase")));
                            }
                            if (msg.contains("QaDesignTokenType.TEST_CASE_DESC_KEY")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.testcase.desc")));
                            }
                            if (msg.contains("QaDesignTokenType.TEST_CASE_STEP_KEY")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.testcase.step")));
                            }
                            if (msg.contains("QaDesignTokenType.TEST_CASE_DATA_KEY")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.testcase.data")));
                            }
                            if (msg.contains("QaDesignTokenType.TEST_CASE_EXPECT_KEY")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.testcase.expect")));
                            }
                            if (msg.contains("QaDesignTokenType.LINKED_METHOD_KEY")) {
                                result.addElement(LookupElementBuilder.create(QaDesignBundle.message("keywords.qa_design.link")));
                            }
                        } else if ("QaDesignTokenType.CONTENT".equals(nodeType)) {
                            // get all the QaDesignTokenType.CONTENT element of this file
                            List<PsiElement> contentElements = Arrays.stream((position.getContainingFile()).getChildren())
                                    .filter(item -> item.getNode().getElementType().toString().equals("QaDesignTokenType.CONTENT"))
                                    .toList();
                            result.addAllElements(contentElements.stream()
                                    .map(item -> LookupElementBuilder.create(item.getText()))
                                    .collect(Collectors.toList()));
                        }
                    }
                });
    }
}
