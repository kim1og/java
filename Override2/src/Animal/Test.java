package Animal;

class Animal{
	public void animalSpeak() {
		System.out.println("animal speak");
	}
}

class Dog extends Animal {
	@Override
	public void animalSpeak() {
		System.out.println("woof");
	}
}

class Cat extends Animal {
	@Override
	public void animalSpeak() {
		System.out.println("meow");
	}
}


public class Test {

	public static void main(String[] args) {
		Animal animal = new Animal();
		Animal dog = new Dog();
		Animal cat = new Cat();
		speak(animal);
		speak(dog);
		speak(cat);
	}
	
	public static void speak(Animal a) {
		a.animalSpeak();
	}

}
