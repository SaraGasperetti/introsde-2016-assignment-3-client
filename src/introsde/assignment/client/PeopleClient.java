package introsde.assignment.client;

import java.util.List;

import javax.xml.ws.Holder;

import introsde.assignment.soap.PeopleService;
import introsde.assignment.soap.Measure;
import introsde.assignment.soap.People;
import introsde.assignment.soap.Person;
import introsde.assignment.soap.Person.CurrentHealth;

public class PeopleClient{
    public static void main(String[] args) throws Exception {
        PeopleService service = new PeopleService();
        People people = service.getPeopleImplPort();
        
        //Request 1
        List<Person> pList = people.getPeopleList();
        System.out.println("List of persons ==> ");
        printPrettyListPerson(pList);
        
        //Request 2
        Person p = people.readPerson(new Long(1));
        System.out.println("Person 1 ==> ");
        printPrettyPerson(p);      
        
        //Request 3
        p.setFirstname("John");
        Holder<Person> h = new Holder<>(p);
        people.updatePerson(h);
        System.out.println("Updated person =>");
        printPrettyPerson(h.value);
        
        //Request 4
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
        System.out.println("New person ==>");
        printPrettyPerson(h.value);
        
        //Request 5
        int success = people.deletePerson(h.value.getIdPerson());
        System.out.println("Deletion ==> \n\t" + ((success == 0) ? "OK" : "ERROR") + "\n");

        //Request 6
        List<Measure> measures = people.readPersonHistory(p.getIdPerson(), "weight");
        System.out.println("People " + p.getIdPerson() + "'s measures ==>");
        printPrettyMeasureList(measures);

        
        //Request 7
        measures = people.readMeasureTypes();
        System.out.println("Measure list ==> ");
        printPrettyMeasureList(measures);

        
        //Request 8
        Measure measure = people.readPersonMeasure(p.getIdPerson(), "weight", measures.get(0).getIdMeasure());
        System.out.println("Value ==>");
        printPrettyMeasure(measure);
        System.out.println();

        
        //Request 9
        m = new Measure();
        m.setMeasureType("height");
        m.setMeasureValue("158");
        m.setDateRegistered("15/12/2016");
        m.setMeasureValueType("integer");
        Holder<Measure> hMeasure = new Holder<>(m);
        people.savePersonMeasure(p.getIdPerson(), hMeasure);
        System.out.println("New measure ==>");
        printPrettyMeasure(hMeasure.value);
        System.out.println();

        
        //Request 10
        hMeasure.value.setMeasureValue("160");
        people.updatePersonMeasure(p.getIdPerson(), hMeasure);
        System.out.println("Updated measure ==>");
        printPrettyMeasure(hMeasure.value);
        System.out.println();
        
    }
    
    private static void printPrettyPerson(Person p) {
    	System.out.println("\tName: '" + p.getFirstname() + " " + p.getLastname() + "', Birthday: '" + p.getBirthdate() + "'");
    	System.out.println("\tCurrent profile: ");
    	printPrettyMeasureList(p.getCurrentHealth().getMeasure());
    }
    
    private static void printPrettyListPerson(List<Person> pList) {
    	for (Person p : pList) {
    		printPrettyPerson(p);
    	}
    }
    
    private static void printPrettyMeasure(Measure m) {
		System.out.println("\t" + m.getMeasureType() + ": " + m.getMeasureValue() + " (" +  m.getMeasureValueType() + ")");
    }
    
    private static void printPrettyMeasureList(List<Measure> mList) {
    	for (Measure m : mList) {
    		printPrettyMeasure(m);
    	}
    	System.out.println();
    }
}
