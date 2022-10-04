package Week;

import java.util.Scanner;

enum Week {
	MON, TUE, WED, THU, FRI, SAT, SUN;

}
public class JavaTest {

	public static void main(String[] args) {
		String inputWeek;
		
		Scanner sc = new Scanner(System.in);
		Week week;
		while(true) {
			System.out.print("요일을 입력하세요: ");
			inputWeek = sc.nextLine();
			
			try {
				week = Week.valueOf(inputWeek);
				
				switch(week) {
				case MON:
					System.out.println("주간회의가 있습니다.");
					break;
					
				case TUE:
					System.out.println("화요일입니다.");
					break;
					
				case WED:
					System.out.println("진행사항 보고하는 날 입니다.");
					break;
					
				case THU:
					System.out.println("목요일입니다.");
					break;
					
				case FRI:
					System.out.println("금요일입니다.");
					break;
					
				case SAT:
					System.out.println("토요일입니다.");
					break;
					
				case SUN:
					System.out.println("오늘은 휴일입니다.");
					break;
				}
			}
			catch (IllegalArgumentException e){
				if(inputWeek.equals("XXX")) {
					System.out.println("프로그램을 종료합니다.");
					return;
				}else {
					System.out.println("잘못 입력하셨습니다.");
				}
			}
			
			System.out.println();
			
		}

	}

}


