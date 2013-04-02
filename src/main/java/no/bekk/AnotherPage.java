package no.bekk;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.ArrayList;

public class AnotherPage extends WebPage {


    public AnotherPage() {

        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Henrik", "Testesen", "henrik@testarino.nu"));
        persons.add(new Person("Peter", "Testesen", "peter@testosteron.org"));

        add(
                new UserPanel("userPanel", persons)
        );
    }

    //TODO: Lag en basepage som har noe css som i hvert fall legger til noe padding eller margin på toppen og til venstre. Alle websider skal arve fra denne pagen.
}
