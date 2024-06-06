package vip.testops.qa_design.actions.entities;

import java.util.ArrayList;
import java.util.List;

public class QaDesignEntity {
    String feature;
    List<QaDesignTestPoint> testPoints;

    public QaDesignEntity() {
        this("", new ArrayList<>());
    }

    public QaDesignEntity(String feature) {
        this(feature, new ArrayList<>());
    }

    public QaDesignEntity(String feature, List<QaDesignTestPoint> testPoints) {
        this.feature = feature;
        this.testPoints = testPoints;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public List<QaDesignTestPoint> getTestPoints() {
        return testPoints;
    }

    public void setTestPoints(List<QaDesignTestPoint> testPoints) {
        this.testPoints = testPoints;
    }
}
