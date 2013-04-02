package no.bekk;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.equals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserPanelTest {


    private WicketTester wicketTester;
    private List<Person> personList;

    @Before
    public void setUp() {
        wicketTester = new WicketTester();
        personList = new ArrayList<Person>();
        personList.add(new Person("henrik", "testesen", "måse@dott.no"));
        personList.add(new Person("peter", "testesen", "heste@svette.no"));
        wicketTester.startComponentInPage(new UserPanel("panel", personList));
    }

    @Test
    public void listShowsPersons() {
        wicketTester.assertListView("panel:personList", personList);
        wicketTester.assertContains("henrik");
        wicketTester.assertContains("peter");
    }

    @Test
    public void lenkeneHarRiktigTekst() {
        String addLinkTekst = wicketTester.getTagByWicketId("addLink").getValue();
        assertThat(addLinkTekst, is("Legg til person"));
        String removeLinkTekst = wicketTester.getTagByWicketId("removeLink").getValue();
        assertThat(removeLinkTekst, is("Fjern person"));
    }

    @Test
    public void addLinkViserNyPersonForm() {
        wicketTester.assertInvisible("panel:addPersonForm");
        wicketTester.clickLink("panel:addLink", true);
        wicketTester.assertComponentOnAjaxResponse("panel:addPersonForm");
        wicketTester.assertVisible("panel:addPersonForm");
    }

    @Test
    public void viserIkkeRemoveLinkHvisListaErTom() {
        wicketTester.clickLink("panel:removeLink", true);
        wicketTester.clickLink("panel:removeLink", true);
        wicketTester.assertInvisible("panel:removeLink");
    }

    @Ignore
    @Test
    public void addLinkLeggerTilEnPerson() {
        //TODO: Skriv om denne til å sjekke at en person blir lagt til når funksjonaliteten er ferdig
        wicketTester.assertContainsNot("nyepost@spennende.no");
        wicketTester.clickLink("panel:addLink", true);
        wicketTester.assertContains("nyepost@spennende.no");
    }

    //TODO: Submit-knappen på addform må ha en ordentlig tekst. Skriv test først for pokker!

    @Test
    public void removeLinkFjernerDenSistePersonen() {
        wicketTester.assertContains("peter");
        wicketTester.clickLink("panel:removeLink", true);
        wicketTester.assertContainsNot("peter");
    }

    //TODO: Det må finnes en removelink for hver person i lista sånn at man kan fjerne hvilken person man vil. Skriv testen først!
}
