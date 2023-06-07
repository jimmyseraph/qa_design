package vip.testops.qa_design.actions.entities;

public class QaDesignTestCase {
    String name;
    String desc;
    String data;
    String step;
    String expect;

    public QaDesignTestCase(String name, String desc, String data, String step, String expect) {
        this.name = name;
        this.desc = desc;
        this.data = data;
        this.step = step;
        this.expect = expect;
    }

    public QaDesignTestCase() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }
}
