package no.bekk;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;

public class HelloWorld extends WebPage {

    private String message;
    private String anotherMessage;


    public HelloWorld() {
        setDefaultModel(new CompoundPropertyModel(this));
        message = "Hello World from model";
        anotherMessage = "This is another property which is automatically linked by Wicket";
        add(new Label("message"));
        add(new Label("anotherMessage"));

        add(new BookmarkablePageLink<WebPage>("denAndreSiden", AnotherPage.class));
    }
}
