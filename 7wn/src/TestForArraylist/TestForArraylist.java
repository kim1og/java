package TestForArraylist;

import java.util.ArrayList;
import java.util.Scanner;

public class TestForArraylist {

	public static void main(String[] args) {
		int arrSize = 4;
		ArrayList<String> words = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		int maxStringSize = 0;
		int maxStringIndex = 0;
		int temp;
		
		for(int i = 0; i < arrSize; i++) {
			System.out.print("이름을 입력하세요>> ");
			words.add(scan.next());
		}

		for(String s : words) {
			System.out.print(s + " ");
		}
		System.out.println();

		for(int i = 0; i < words.size(); i++) {
			temp = words.get(i).length();
			if(temp > maxStringSize) {
				maxStringSize = temp;
				maxStringIndex = i;
			}
		}
		
		System.out.println("가장 긴 이름 : " + words.get(maxStringIndex));
	}
}
