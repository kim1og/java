package PersonalNumberStorage;

public class Test {

	public static void main(String[] args) {
		PersonalNumberStorage storage = new PersonalNumberStorageImpl(100);
		storage.addPersonalInfo("Jack", 22);
		storage.addPersonalInfo("King", 33);
		
		System.out.println(storage.searchName(22));
		System.out.println(storage.searchName(33));
		System.out.println(storage.searchName(44));
	}

}

interface PersonalNumberStorage{
	void addPersonalInfo(String name, int age);
	String searchName(int age);
}

class PersonalNumberStorageImpl implements PersonalNumberStorage{   //handler
	PersonalNumInfo[] perArr;
	int numOfPerInfo;
	PersonalNumberStorageImpl(int maxPerNum){
		perArr = new PersonalNumInfo[maxPerNum];
		numOfPerInfo = 0;
	}
	public void addPersonalInfo(String name, int age) {
		perArr[numOfPerInfo++] = new PersonalNumInfo(name, age);
	}
	public String searchName(int age) {
		for (int i = 0; i < numOfPerInfo; i++) {
			if(age == perArr[i].getAge()) {
				return perArr[i].getName();
			}
		}
		return "no one";
	}
}

class PersonalNumInfo{
	String name;
	int age;
	
	PersonalNumInfo(String name, int age){
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
