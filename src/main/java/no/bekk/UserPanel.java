package no.bekk;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.List;

public class UserPanel extends Panel {

    private List<Person> personList;

    public UserPanel(String id, final List<Person> personList) {
        super(id);
        this.personList = personList;

        setOutputMarkupId(true);

        final PropertyListView<Person> listView = new PropertyListView<Person>("personList", personList) {

            @Override
            protected void populateItem(ListItem<Person> listItem) {
                listItem.add(
                        new Label("firstName"),
                        new Label("lastName"),
                        new Label("emailAddress")
                );
            }
        };
        add(listView);

        final FeedbackPanel feedback = new FeedbackPanel("feedback");

        feedback.setOutputMarkupPlaceholderTag(true);

        add(feedback);

        //TODO: Trekk ut addPersonForm til et Panel og skriv egne tester for det.
        final Form<Person> addPersonForm = new Form<Person>("addPersonForm", new CompoundPropertyModel<Person>(new Person()));

        //TODO: Kanskje lag sånne annotations som vi hadde på TMS for å slippe å sende med noe man kan legge til ajaxRequestTarget. Mulig jeg slipper ved å bare legge til Parent i ajaxRequestTarget

        FormComponentFeedbackBorder firstNameBorder = new FormComponentFeedbackBorder("firstNameBorder");
        FormComponentFeedbackBorder lastNameBorder = new FormComponentFeedbackBorder("lastNameBorder");
        FormComponentFeedbackBorder emailAddressBorder = new FormComponentFeedbackBorder("emailAddressBorder");
        addPersonForm.add(
                firstNameBorder.add(new TextField<String>("firstName").setRequired(true)),
                lastNameBorder.add(new TextField<String>("lastName").setRequired(true)),
                emailAddressBorder.add(new EmailTextField("emailAddress").setRequired(true)),
                new AjaxButton("submit") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        personList.add((Person) addPersonForm.getDefaultModelObject());
                        form.setDefaultModelObject(new Person());
                        target.add(UserPanel.this);
                        form.setVisibilityAllowed(false);
                        info("Saved person successfully");
                    }

                    @Override
                    protected void onError(AjaxRequestTarget target, Form<?> form) {
                        super.onError(target, form);
                        target.add(feedback);
                        target.add(UserPanel.this); //For å vise stjerne ved siden av feltene som det er noe feil med
                    }
                }
        );
        addPersonForm.setVisibilityAllowed(false);

        addPersonForm.setOutputMarkupPlaceholderTag(true);

        add(addPersonForm);


        add(new AjaxLink("addLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                addPersonForm.setVisibilityAllowed(true);
                ajaxRequestTarget.add(addPersonForm);
            }
        });

        add(new AjaxLink("removeLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                if (!personList.isEmpty()) {
                    personList.remove(personList.size() - 1);
                    ajaxRequestTarget.add(UserPanel.this);
                }
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisibilityAllowed(!personList.isEmpty());
            }
        });
    }


}
