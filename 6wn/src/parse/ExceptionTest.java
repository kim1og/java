package parse;

import java.util.Scanner;

public class ExceptionTest {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input;
		while(true) {
			System.out.print("입력하시오(종료:Z) ");
			input = sc.nextLine();
			try {
				Integer.parseInt(input);
				System.out.println("숫자입니다!!");
			}
			catch(NumberFormatException e){
				if(input.equals("Z")) {
					System.out.println("프로그램을 종료합니다!!");
					return;
				}
				
				System.out.println("문자입니다!!");
			}
		}

	}

}
