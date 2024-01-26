package ViewController;

import java.util.Scanner;

import Model.*;

public class Main {
	static String[] entry= {"강민규","고현우","고희청","권승호","김대원","김동준",
			"김하영","김혜린","나유정","박상윤","서동현","신혜선",
			"양세진","오현진","유승주","윤옥산","윤정원","이봄",
			"임명택","장지오","정두연","정재원","조가현","조영훈",
			"홍창민","정세연","신범식","조자연","임명진","안현진",
			"임승환","조준용"};
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		UserDAO udao=new UserDAO();
		while(true) {
			String input1,input2;
			System.out.println("[1]로그인 [2]회원가입 [3]랭킹정보 [4]게임종료");
			byte choice=sc.nextByte();
			if(choice==1) {
				//로그인
			}else if(choice==2) {
				//회원가입
			}else if (choice==3) {
				//랭킹보기
			}else if(choice==4) {
				//게임종료
				System.out.println("게임종료");
				break;
			}else {
				//예외처리
			}
		}
	}
	public int playGame() {
		int score=0;
		byte inning=1;
		for(int i=0;i<entry.length;i++) {
			String name=entry[i];
			if(name.length()<3)name+=" ";
			System.out.print((i>=9?"[":" [")+(i+1)+"]"+name+" ");
			if(i%4==3)System.out.println();
		}
		while(inning!=9) {
			
			inning++;
		}
		System.out.println("[1]게임시작 [2]내 기록 [3]회원탈퇴 [4]로그아웃");
		return score=0;
	}
}
