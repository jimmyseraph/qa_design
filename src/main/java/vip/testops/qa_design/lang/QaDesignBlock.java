package vip.testops.qa_design.lang;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

import java.util.ArrayList;
import java.util.List;

public class QaDesignBlock extends AbstractBlock {

    private final SpacingBuilder spacingBuilder;
    private final int level;
    protected QaDesignBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, SpacingBuilder spacingBuilder, int level) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
        this.level = level;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();
        while (child != null) {
            if (child.getElementType() != TokenType.WHITE_SPACE) {
                Alignment alignment = Alignment.createAlignment();
                int level = 0;
                if (child.getElementType() == QaDesignTypes.RULE_TEST_CASE_DESIGN) {
                    level = 1;
                } else if (
                        child.getElementType() == QaDesignTypes.RULE_TEST_CASE_DESC ||
                        child.getElementType() == QaDesignTypes.RULE_TEST_CASE_DATA ||
                        child.getElementType() == QaDesignTypes.RULE_TEST_CASE_STEP ||
                        child.getElementType() == QaDesignTypes.RULE_TEST_CASE_EXPECT
                ) {
                    level = 1;
                } if (child.getElementType() == QaDesignTypes.CONTENT) {
                    if (child.getPsi().getPrevSibling() != null
                            && child.getPsi().getPrevSibling().getPrevSibling() != null
                            && child.getPsi().getPrevSibling().getNode().getElementType() == TokenType.WHITE_SPACE
                            && child.getPsi().getPrevSibling().getPrevSibling().getNode().getElementType() == QaDesignTypes.CONCAT_NEW_LINE
                    ) {
                        level = 2;
                    }
                    else if (child.getPsi().getPrevSibling() != null
                            && child.getPsi().getPrevSibling().getNode().getElementType() == QaDesignTypes.CONCAT_NEW_LINE
                    ) {
                        level = 2;
                    } else if (child.getPsi().getPrevSibling() != null
                            && child.getPsi().getPrevSibling().getNode().getElementType() == QaDesignTypes.SEPARATOR
                    ) {
                        level = 2;
                    } else if (child.getPsi().getPrevSibling() != null
                            && child.getPsi().getPrevSibling().getPrevSibling() != null
                            && child.getPsi().getPrevSibling().getNode().getElementType() == TokenType.WHITE_SPACE
                            && child.getPsi().getPrevSibling().getPrevSibling().getNode().getElementType() == QaDesignTypes.SEPARATOR
                    ){
                        level = 2;
                    }
                }
                if (level == 2 && blocks.size() > 1) {
                    alignment = Alignment.createChildAlignment(blocks.get(1).getAlignment());
                }
                Block block = new QaDesignBlock(
                        child,
                        Wrap.createWrap(WrapType.NONE, false),
                        alignment,
                        spacingBuilder,
                        level
                );
                blocks.add(block);
            }
            child = child.getTreeNext();
        }
        return blocks;
    }

    @Override
    public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }

    @Override
    public Indent getIndent() {
        return Indent.getSpaceIndent(4 * level);
    }
}
