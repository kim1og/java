package Test;




public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Cat();
		new Dog();
		new Duck();
		
		System.out.println("积己等 按眉狼 荐 : " + Animal.getCount());
	}

}


class Animal {
	private static int count = 0;

	
	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Animal.count = count;
	}
	
	public void AddFriend() {
		Animal.count++;
	}
	
}

class Cat extends Animal{
	Cat(){
		super.AddFriend();
	}
}

class Dog extends Animal{
	Dog(){
		super.AddFriend();
	}
}

class Duck extends Animal{
	Duck(){
		super.AddFriend();
	}
}


