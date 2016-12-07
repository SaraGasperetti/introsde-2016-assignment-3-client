package introsde.assignment.client;

import java.io.PrintWriter;
import java.util.List;

import introsde.document.ws.Measure;
import introsde.document.ws.Person;

public class BofWriterOnFile {

	private static BofWriterOnFile instance;
	PrintWriter writer;
	private static int count = 1;
	private final static String xmlFile = "client-server-xml.log";

	private BofWriterOnFile() {
		try {
			writer = new PrintWriter(xmlFile, "UTF-8");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static BofWriterOnFile getWriter() {
		if (instance == null) {
			instance = new BofWriterOnFile();
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
		this.write("\tName: '" + p.getName() + " " + p.getLastname() + "', Birthday: '" + p.getBirthdate() + "'");
		this.write("\tCurrent profile: ");
		printPrettyMeasureList(p.getCurrentHealth().getMeasure());
	}

	protected void printPrettyListPerson(List<Person> pList) {
		for (Person p : pList) {
			printPrettyPerson(p);
		}
	}

	protected void printPrettyMeasure(Measure m) {
		this.write("\t" + m.getType() + ": " + m.getValue() + " (" + m.getValueType() + ")");
	}

	protected void printPrettyMeasureList(List<Measure> mList) {
		for (Measure m : mList) {
			printPrettyMeasure(m);
		}
		this.write("\n");
	}

}
