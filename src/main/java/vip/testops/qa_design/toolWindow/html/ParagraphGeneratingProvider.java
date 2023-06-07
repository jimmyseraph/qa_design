package vip.testops.qa_design.toolWindow.html;

import org.intellij.markdown.ast.ASTNode;
import org.intellij.markdown.html.HtmlGenerator;
import org.intellij.markdown.html.TrimmingInlineHolderProvider;
import org.jetbrains.annotations.NotNull;

public class ParagraphGeneratingProvider extends TrimmingInlineHolderProvider {
    @Override
    public void openTag(@NotNull HtmlGenerator.HtmlGeneratingVisitor visitor, @NotNull String text, @NotNull ASTNode node) {
        visitor.consumeTagOpen(node, "p", null, false);
    }

    @Override
    public void closeTag(@NotNull HtmlGenerator.HtmlGeneratingVisitor visitor, @NotNull String text, @NotNull ASTNode node) {
        visitor.consumeTagClose("p");
    }

    @Override
    public void processNode(@NotNull HtmlGenerator.HtmlGeneratingVisitor visitor, @NotNull String text, @NotNull ASTNode node) {
        openTag(visitor, text, node);
        for (ASTNode child : childrenToRender(node)) {

        }
    }
}
