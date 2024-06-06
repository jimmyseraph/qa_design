package vip.testops.qa_design.actions.entities;

import java.util.ArrayList;
import java.util.List;

public class QaDesignTestPoint {
    private String name;
    private List<QaDesignTestCase> testCases;

    public QaDesignTestPoint(String name, List<QaDesignTestCase> testCases) {
        this.name = name;
        this.testCases = testCases;
    }

    public QaDesignTestPoint(String name) {
        this(name, new ArrayList<>());
    }

    public QaDesignTestPoint() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QaDesignTestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<QaDesignTestCase> testCases) {
        this.testCases = testCases;
    }
}
