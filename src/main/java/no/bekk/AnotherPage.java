package no.bekk;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.ArrayList;

public class AnotherPage extends WebPage {


    public AnotherPage() {

        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Henrik", "Hemmen", "hemm1@club4.no"));
        persons.add(new Person("Peter", "Hemmen", "ceo@facebook.com"));

        add(
                new UserPanel("userPanel", persons)
        );
    }
}
