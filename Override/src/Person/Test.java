package Person;

public class Test {
	public static void main(String[] args) {
		Person jee = new Student("jee");
		Person kim = new Professor("kim");
		Person lee = new Researcher("lee");
		
		
		if(jee instanceof Person) {
			jee.printPersonMessage();
		}
		if(jee instanceof Student) {
			((Student)jee).printStudentMessage();
		}
		if(kim instanceof Student) {
			((Student)kim).printStudentMessage();
		}
		if(kim instanceof Professor) {
			((Professor)kim).printProfessorMessage();
		}
		if(kim instanceof Researcher) {
			((Researcher)kim).printResearcherMessage();
		}
		if(lee instanceof Professor) {
			((Professor)lee).printProfessorMessage();
		}
	}
}


class Person{
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	Person(String name){
		this.name = name;
	}
	
	public void printPersonMessage() {
		System.out.println(name + "는 Person으로 캐스팅할 수 있다.");
	}
}

class Student extends Person{
	Student(String name){
		super(name);
	}
	
	public void printStudentMessage() {
		System.out.println(super.getName() + "는 Student로 캐스팅할 수 있다.");
	}
}


class Researcher extends Person{
	Researcher(String name){
		super(name);
	}
	
	public void printResearcherMessage() {
		System.out.println(super.getName() + "는 Researcher로 캐스팅할 수 있다.");
	}
}



class Professor extends Researcher{
	Professor(String name){
		super(name);
	}
	
	public void printProfessorMessage() {
		System.out.println(super.getName() + "는 Professor로 캐스팅할 수 있다.");
	}
}






