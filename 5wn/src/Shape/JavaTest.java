package Shape;

public class JavaTest {

	public static void main(String[] args) {
		final int MAX_SHAPE = 3;
		Shape shape[] = new Shape[MAX_SHAPE];
		shape[0] = new Square(4);
		shape[1] = new Circle(2);
		shape[2] = new Square(5);
		
		for(Shape s : shape) {
			printShape(s);
		}
		
	}
	
	public static void printShape(Shape shape) {
		System.out.print("Area: " + shape.getArea());
		System.out.println(", Perimeter: " + shape.getPerimeter());
	}

}

abstract class Shape {
	protected abstract double getArea();
	protected abstract double getPerimeter();
}

class Square extends Shape {
	private double width;
	private final int numOfSide = 4;
	Square(double width){
		this.width = width;
	}
	protected double getArea() {
		return width * width;
	}
	
	protected double getPerimeter() {
		return width * numOfSide;
	}
}


class Circle extends Shape {
	private double r;
	private final double PI = 3.14;
	Circle(double r){
		this.r = r;
	}
	protected double getArea() {
		return r * r * PI;
	}
	
	protected double getPerimeter() {
		return r * 2 * PI;
	}
}

