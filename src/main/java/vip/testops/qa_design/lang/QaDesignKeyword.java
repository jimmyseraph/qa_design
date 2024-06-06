package vip.testops.qa_design.lang;

import vip.testops.qa_design.QaDesignBundle;

public enum QaDesignKeyword {

    FEATURE(0,QaDesignBundle.message("keywords.qa_design.feature")),
    TEST_POINT(1,QaDesignBundle.message("keywords.qa_design.test_point")),
    TEST_CASE(2,QaDesignBundle.message("keywords.qa_design.testcase")),
    TEST_CASE_DESC(3,QaDesignBundle.message("keywords.qa_design.testcase.desc")),
    TEST_CASE_DATA(4,QaDesignBundle.message("keywords.qa_design.testcase.data")),
    TEST_CASE_STEP(5,QaDesignBundle.message("keywords.qa_design.testcase.step")),
    TEST_CASE_EXPECT(6,QaDesignBundle.message("keywords.qa_design.testcase.expect")),
    TEST_CASE_LINK(7, QaDesignBundle.message("keywords.qa_design.link")),
    TEST_CASE_TAG(8, QaDesignBundle.message("keywords.qa_design.tag")),
    ;
    private final String name;
    private final int index;

    QaDesignKeyword(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public static QaDesignKeyword getKeyword(String name) {
        for(QaDesignKeyword keyword : QaDesignKeyword.values()) {
            if(keyword.getName().equals(name)) {
                return keyword;
            }
        }
        return null;
    }

    public static QaDesignKeyword getKeyword(int index) {
        for(QaDesignKeyword keyword : QaDesignKeyword.values()) {
            if(keyword.getIndex() == index) {
                return keyword;
            }
        }
        return null;
    }
}
