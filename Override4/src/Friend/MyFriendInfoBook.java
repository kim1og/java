package Friend;

import java.util.Scanner;

public class MyFriendInfoBook {
	final static int FRIEND_NUM = 10;
	public static void main(String[] args) {
		
		FriendInfoHandler handler= new FriendInfoHandler(FRIEND_NUM);
		while(true) {
			System.out.println("*** 메뉴 선택 ***");
			System.out.println("1. 고교 친구 저장");
			System.out.println("2. 대학 친구 저장");
			System.out.println("3. 전체 정보 출력");
			System.out.println("4. 기본 정보 출력");
			System.out.println("5. 프로그램 종료");
			System.out.print("선택>> ");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			
			switch(choice) {
				case 1:
				case 2:
					handler.addFriend(choice);
					break;
				case 3:
					handler.showAllData();
					break;
				case 4:
					handler.showAllSimpleData();
					break;
				case 5:
					System.out.println("프로그램을 종료합니다.");
					return;
			}
		}

	}

}


class HighFriend{
	String name;
	String phoneNum;
	String addr;
	String work;
	public HighFriend(String name, String phone, String addr, String job) {
		this.name = name;
		this.phoneNum = phone;
		this.addr = addr;
		this.work = job;
	}
	
	public void showData() {
		System.out.println("이름 : " + name);
		System.out.println("전화 : " + phoneNum);
		System.out.println("주소 : " + addr);
		System.out.println("직업 : " + work);
	}
	
	public void showBasicInfo() {
		System.out.println("이름 : " + name);
		System.out.println("전화 : "+ phoneNum);
	}
}

class UnivFriend {
	String name;
	String phoneNum;
	String addr;
	String major;
	public UnivFriend(String name, String phone, String addr, String major) {
		this.name = name;
		this.phoneNum = phone;
		this.addr = addr;
		this.major = major;
	}
	public void showData() {
		System.out.println("이름 : " + name);
		System.out.println("전화 : " + phoneNum);
		System.out.println("주소 : " + addr);
		System.out.println("전공 : " + major);
	}
	
	public void showBasicInfo() {
		System.out.println("이름 : " + name);
		System.out.println("전화 : " + phoneNum);
		System.out.println("전공 : " + major);
	}
}

class FriendInfoHandler{
	private HighFriend[] myHighFriends;
	private UnivFriend[] myUnivFriends;
	private int numOfUnivFriends;
	private int numOfHighFriends;
	
	public FriendInfoHandler(int num) {
		myHighFriends = new HighFriend[num];
		myUnivFriends = new UnivFriend[num];
		numOfUnivFriends = 0;
		numOfHighFriends = 0;
	}
	private void addFriendInfo(Object fren) {
		if (fren instanceof HighFriend) {
			myHighFriends[numOfHighFriends++] = (HighFriend)fren; 
		}else if(fren instanceof UnivFriend) {
			myUnivFriends[numOfUnivFriends++] = (UnivFriend)fren; 
		}
	}
	
	public void addFriend(int choice) {
		String name, phoneNum, addr;
		Scanner sc = new Scanner(System.in);
		System.out.print("이름 : ");
		name = sc.nextLine();
		System.out.print("전화 : ");
		phoneNum = sc.nextLine();
		System.out.print("주소 : ");
		addr = sc.nextLine();
		if (choice == 1) {
			String job;
			System.out.print("직업 : ");
			job = sc.nextLine();
			addFriendInfo(new HighFriend(name, phoneNum, addr, job));
		} else {
			String major;
			System.out.print("학과 : ");
			major = sc.nextLine();
			addFriendInfo(new UnivFriend(name, phoneNum, addr, major));
		}
		System.out.println("입력 완료! \n");
	}
	
	public void showAllData() {
		for (int i = 0; i < numOfUnivFriends; i ++) {
			myUnivFriends[i].showData();
			System.out.println("");
		}
		for (int i = 0; i < numOfHighFriends; i ++) {
			myHighFriends[i].showData();
			System.out.println("");
		}
	}
	public void showAllSimpleData() {
		for (int i = 0; i < numOfUnivFriends; i ++) {
			myUnivFriends[i].showBasicInfo();
			System.out.println("");
		}
		for (int i = 0; i < numOfHighFriends; i ++) {
			myHighFriends[i].showBasicInfo();
			System.out.println("");
		}
	}
}
