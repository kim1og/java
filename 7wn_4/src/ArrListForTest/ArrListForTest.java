package ArrListForTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ArrListForTest {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<PairIntString> alist = new ArrayList<PairIntString>();
		int num = 0;
		String word;
		
		while(num != 99) {
			System.out.print("번호와 단어를 입력하세요 (99=quit): ");
			num = scan.nextInt();
			word = scan.next();
			
			alist.add(new PairIntString(num, word));
		}
		
		System.out.print("찾을 번호를 입력하세요. : ");
		num = scan.nextInt();
		scan.nextLine();
		
		searchCollection(alist.iterator(), num);
	}
	
	public static void printCollection(Iterator<PairIntString> itr) {
		while(itr.hasNext()) {
			itr.next().showData();
		}
	}
	
	public static void searchCollection(Iterator<PairIntString> itr, int num) {
		while(itr.hasNext()) {
			PairIntString temp = itr.next(); 
			if (temp.getNum() == num) {
				System.out.printf("%s입니다", temp.getWord());
				return;
			}
		}
		System.out.println("일치하는 단어가 없습니다.");
		return;
	}
}

class PairIntString{
	int num;
	String word;
	
	public int getNum() {
		return num;
	}
	
	public String getWord() {
		return word;
	}
	
	public PairIntString(int num, String word){
		this.num = num;
		this.word = word;
	}
	
	public void showData() {
		System.out.printf("%d %s\n", num, word);
	}
}