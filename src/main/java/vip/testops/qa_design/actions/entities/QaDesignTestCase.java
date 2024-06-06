package vip.testops.qa_design.actions.entities;

import java.util.LinkedHashSet;
import java.util.Set;

public class QaDesignTestCase {
    String name;
    String desc;
    String data;
    String step;
    String expect;
    String link;
    Set<String> tags;

    public QaDesignTestCase(String link, Set<String> tags, String name, String desc, String data, String step, String expect) {
        this.name = name;
        this.desc = desc;
        this.data = data;
        this.step = step;
        this.expect = expect;
        this.link = link;
        this.tags = tags;
    }

    public QaDesignTestCase(String name, String desc, String data, String step, String expect) {
        this(null, null, name, desc, data, step, expect);
    }

    public QaDesignTestCase() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        if(this.tags == null) {
            this.tags = new LinkedHashSet<>();
        }
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
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
