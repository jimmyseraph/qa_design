package vip.testops.qa_design.toolWindow.ui.entities;

public class Message {
    private String content;
    private Sender sender;

    public Message(String content, Sender sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
