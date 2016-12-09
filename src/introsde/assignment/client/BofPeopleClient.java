package introsde.assignment.client;

import java.util.List;

import javax.xml.ws.Holder;

import introsde.document.ws.PeopleService;
import introsde.document.ws.Measure;
import introsde.document.ws.People;
import introsde.document.ws.Person;
import introsde.document.ws.Person.CurrentHealth;

public class BofPeopleClient {
	public static void main(String[] args) throws Exception {
		PeopleService service = new PeopleService();
		People people = service.getPeopleImplPort();

		BofWriterOnFile writer = BofWriterOnFile.getWriter();
		writer.write("The wsdl file is located at");
		writer.write(service.getWSDLDocumentLocation().toString() + "\n");

		// Request 1
		writer.printRequest("List of persons");
		List<Person> pList = people.readPersonList();
		writer.printPrettyListPerson(pList);

		// Request 2
		writer.printRequest("Person 1");
		Person p = people.readPerson(1);
		writer.printPrettyPerson(p);

		// Request 3
		writer.printRequest("Updated person with name John");
		p.setName("John");
		Holder<Person> h = new Holder<>(p);
		people.updatePerson(h);
		writer.printPrettyPerson(h.value);

		// Request 4
		writer.printRequest("New person");
		Person newPerson = new Person();
		newPerson.setName("Jack");
		newPerson.setLastname("Finn");
		Measure m = new Measure();
		m.setType("weight");
		m.setValue("80");
		m.setValueType("integer");
		m.setDate("05/07/2013");
		CurrentHealth c = new CurrentHealth();
		c.getMeasure().add(m);
		newPerson.setCurrentHealth(c);
		h.value = newPerson;
		people.createPerson(h);
		writer.printPrettyPerson(h.value);

		// Request 5
		writer.printRequest("Deletion");
		int success = people.deletePerson(h.value.getIdPerson());
		writer.write("\t" + ((success == 0) ? "OK" : "ERROR") + "\n");

		// Request 6
		writer.printRequest("People " + p.getIdPerson() + "'s measures");
		List<Measure> measures = people.readPersonHistory(p.getIdPerson(), "weight");
		writer.printPrettyMeasureList(measures);

		// Request 7
		writer.printRequest("Measure list");
		measures = people.readMeasureTypes();
		writer.printPrettyMeasureList(measures);

		// Request 8
		writer.printRequest("First measure of type weight");
		Measure measure = people.readPersonMeasure(p.getIdPerson(), "weight", measures.get(0).getIdMeasure());
		writer.printPrettyMeasure(measure);
		writer.write("\n");

		// Request 9
		writer.printRequest("New measure");
		m = new Measure();
		m.setType("height");
		m.setValue("158");
		m.setDate("15/12/2016");
		m.setValueType("integer");
		Holder<Measure> hMeasure = new Holder<>(m);
		people.savePersonMeasure(p.getIdPerson(), hMeasure);
		writer.printPrettyMeasure(hMeasure.value);
		writer.write("\n");

		// Request 10
		writer.printRequest("Updated measure");
		hMeasure.value.setValue("160");
		people.updatePersonMeasure(p.getIdPerson(), hMeasure);
		writer.printPrettyMeasure(hMeasure.value);
		writer.write("\n");

		writer.closeWriting();

	}

}
