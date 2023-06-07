package vip.testops.qa_design.actions.entities;

import java.util.List;

public class QaDesignEntity {
    String requirementId;
    String requirement;
    List<QaDesignTestPoint> testPoints;

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public List<QaDesignTestPoint> getTestPoints() {
        return testPoints;
    }

    public void setTestPoints(List<QaDesignTestPoint> testPoints) {
        this.testPoints = testPoints;
    }
}
