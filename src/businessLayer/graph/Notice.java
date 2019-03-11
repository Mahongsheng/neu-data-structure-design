package businessLayer.graph;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 这是一个公告类
 * 其包含公告内容和发布的时间
 * @author 软英1702 马洪升 20175188
 */
public class Notice {
    private StringProperty time = new SimpleStringProperty();
    private StringProperty content = new SimpleStringProperty();

    public Notice() {
    }

    public Notice(String time, String content) {
        this.time = new SimpleStringProperty(time);
        this.content = new SimpleStringProperty(content);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}
