package introsde.assignment.client;

import java.util.List;

import javax.xml.ws.Holder;

import introsde.assignment.soap.PeopleService;
import introsde.assignment.soap.Measure;
import introsde.assignment.soap.People;
import introsde.assignment.soap.Person;
import introsde.assignment.soap.Person.CurrentHealth;

public class MyPeopleClient {
	public static void main(String[] args) throws Exception {
		PeopleService service = new PeopleService();
		People people = service.getPeopleImplPort();

		MyWriterOnFile writer = MyWriterOnFile.getWriter();
		writer.write("The wsdl file is located at");
		writer.write(service.getWSDLDocumentLocation().toString() + "\n");

		// Request 1
		writer.printRequest("List of persons");
		List<Person> pList = people.getPeopleList();
		writer.printPrettyListPerson(pList);

		// Request 2
		writer.printRequest("Person 1");
		Person p = people.readPerson(new Long(1));
		writer.printPrettyPerson(p);

		// Request 3
		writer.printRequest("Updated person");
		p.setFirstname("John");
		Holder<Person> h = new Holder<>(p);
		people.updatePerson(h);
		writer.printPrettyPerson(h.value);

		// Request 4
		writer.printRequest("New person");
		Person newPerson = new Person();
		newPerson.setFirstname("Jack");
		newPerson.setLastname("Finn");
		Measure m = new Measure();
		m.setMeasureType("weight");
		m.setMeasureValue("80");
		m.setMeasureValueType("integer");
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
		writer.printRequest("Value");
		Measure measure = people.readPersonMeasure(p.getIdPerson(), "weight", measures.get(0).getIdMeasure());
		writer.printPrettyMeasure(measure);
		writer.write("\n");

		// Request 9
		writer.printRequest("New measure");
		m = new Measure();
		m.setMeasureType("height");
		m.setMeasureValue("158");
		m.setDateRegistered("15/12/2016");
		m.setMeasureValueType("integer");
		Holder<Measure> hMeasure = new Holder<>(m);
		people.savePersonMeasure(p.getIdPerson(), hMeasure);
		writer.printPrettyMeasure(hMeasure.value);
		writer.write("\n");

		// Request 10
		writer.printRequest("Updated measure");
		hMeasure.value.setMeasureValue("160");
		people.updatePersonMeasure(p.getIdPerson(), hMeasure);
		writer.printPrettyMeasure(hMeasure.value);
		writer.write("\n");

		writer.closeWriting();

	}

}
