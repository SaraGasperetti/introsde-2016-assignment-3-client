package introsde.assignment.client;

import java.io.PrintWriter;
import java.util.List;

import introsde.assignment.soap.Measure;
import introsde.assignment.soap.Person;

public class MyWriterOnFile {

	private static MyWriterOnFile instance;
	PrintWriter writer;
	private static int count = 1;
	private final static String xmlFile = "client-server-xml.log";

	private MyWriterOnFile() {
		try {
			writer = new PrintWriter(xmlFile, "UTF-8");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static MyWriterOnFile getWriter() {
		if (instance == null) {
			instance = new MyWriterOnFile();
		}
		return instance;
	}

	public void write(String text) {
		writer.println(text);
	}

	public void closeWriting() {
		writer.close();
		instance = null;
		count = 1;
	}

	public void printRequest(String description) {
		this.write("Request #" + count + ": " + description + " ==>\n");
		count++;
	}

	protected void printPrettyPerson(Person p) {
		this.write("\tId: " + p.getIdPerson() + "\n\tFirstname: '" + p.getFirstname() + 
				"'\n\tLastname: '" + p.getLastname() + "'\n\tBirthday: '" + p.getBirthdate() + 
				"'\n\tEmail: '" + p.getEmail() + "'");
		this.write("\tCurrent profile: ");
		printPrettyMeasureList(p.getCurrentHealth().getMeasure());
	}

	protected void printPrettyListPerson(List<Person> pList) {
		for (Person p : pList) {
			printPrettyPerson(p);
		}
	}

	protected void printPrettyMeasure(Measure m) {
		this.write("\t\t" + m.getMeasureType() + " (mid: " + m.getIdMeasure()+ "): " + m.getMeasureValue() + " (" + m.getMeasureValueType() + ") registered on " + m.getDateRegistered());
	}

	protected void printPrettyMeasureList(List<Measure> mList) {
		for (Measure m : mList) {
			printPrettyMeasure(m);
		}
		this.write("\n");
	}

}
