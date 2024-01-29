package ViewController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import Model.*;

public class Main {
	static UserDAO udao = new UserDAO();
	static Scanner sc = new Scanner(System.in);
	public static int input_I() {
		try {
			int temp = sc.nextInt();
			return temp;
		} catch (Exception e) {
			return -1;
		}finally {
			sc.nextLine();
		}
	}
	public static void main(String[] args) {
		while(true) {
			System.out.println("[1]로그인 [2]회원가입 [3]랭킹정보 [4]게임종료");
			int choice=input_I();
			if(choice==1) {
				BaseballDTO bdto = new BaseballDTO();
				//로그인
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				UserDTO dto = new UserDTO();
				dto.setId(id);
				dto.setPw(pw);

				UserDTO result = udao.login(dto);
				if(result==null) {
					System.out.println("로그인 실패");
					continue;
				}
				//로그인성공
				while(true) {
					String masterId = "test1";
					if(result.getId().equals(masterId)) {
						System.out.println("[1]게임시작 [2]내기록 [3]회원확인 [4]로그아웃");
					}else {
						System.out.println("[1]게임시작 [2]내기록 [3]회원탈퇴 [4]로그아웃");
					}
					choice=input_I();
					if(choice==1) {
						String[] entry=beforeGame(bdto);
						playGame(entry, bdto.getClub(), true);	
					}else if(choice==2) {
						//내기록
						//baseballDTO사용
						BaseballDAO bdao = new BaseballDAO();
					    ArrayList<BaseballDTO> list = bdao.rank(result);
					    if(list.size() == 0) {
					    	System.out.println("기록이 없습니다. 게임을 플레이해주세요.");
					    }else {
					    	 for(int i = 0; i < list.size(); i++) {
							    	System.out.println("구단명: " + list.get(i).getClub() + " / 점수: "+ list.get(i).getScore()+ "점");
					    }
					    }
						
					}else if(choice==3 && result.getId().equals(masterId)) {
						// 마스터 아이디로 회원을 확인할 때
						BaseballDAO bdao = new BaseballDAO();
						ArrayList<BaseballDTO> list = bdao.history(result.getId());
						System.out.println("아이디/점수");
						for (int i = 0; i < list.size(); i++) {
							System.out.println(list.get(i).getId() + "/t" + list.get(i).getScore());
						}
					}else if(choice==3) {
						//회원탈퇴
						//baseball tbable에서 정보 모두 삭제후 (FK)
						//USER_INFO table에서 정보 삭제(PK)
						
						System.out.println("정말로 탈퇴하시겠습니까? [1]예 [2]아니오");
						int yes = sc.nextInt();
						if(yes == 1) {
							BaseballDAO bdao = new BaseballDAO();
							int cnt = bdao.delete(id);
							cnt = 1;
								System.out.println("회원삭제 성공!");
						}
						else {
							System.out.println("회원탈퇴 실패...");
						}
					}else if(choice==4) {
						System.out.println("로그아웃");
						break;
					}else {
						System.out.println("1~4를 입력해주세요");
					}
				}
			}else if(choice==2) {
				//회원가입
				System.out.print("가입할 아이디 입력 : ");
				String joinId = sc.next();
				
				int result = udao.idCheck(joinId);
				
				if(result == 0 ) {
					System.out.println("사용 가능한 ID입니다.");
				}else {
					System.out.println("중복된 ID입니다.");
					continue;
				}
				System.out.print("가입할 비밀번호 입력 : ");
				String joinPw = sc.next();

				UserDTO dto = new UserDTO();
				dto.setId(joinId);
				dto.setPw(joinPw);
			
				int cnt = udao.join(dto);
			}else if (choice==3) {
				BaseballDAO bdao = new BaseballDAO();
				//랭킹보기
				
				ArrayList<BaseballDTO>list = bdao.rank();
				
				System.out.println("아이디\t구단\t점수");
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).getId() + "\t" + list.get(i).getClub()+"\t"+list.get(i).getScore());

				}
			}else if(choice==4) {
				//게임종료
				System.out.println("게임종료");
				break;
			}else {
				//예외처리
				System.out.println("1~4를 입력해주세요");
			}
		}
	}
	public static String[] beforeGame(BaseballDTO bdto) {
		String input_S;
		// 구단 이름 설정
		PrintOption po = PrintOption.getPO();
		String content = po.specialFont(po.b_WHITE, po.f_BLUE, "구단명을 정해주세요(9자이하)\n>>");
		while (true) {
			System.out.print(content);
			input_S = sc.next();
			if (input_S.length() < 9)
				break;
		}
		bdto.setClub(input_S);
		// 선수 선택
		Game g = Game.getGame();
		String[] entry = g.getEntry();
		String[] userEntry = new String[9];
		ArrayList<String> list = new ArrayList<>();
		System.out.println();
		String cName = bdto.getClub();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < entry.length; j++) {
				String name = entry[j];
				if (name.length() < 3)
					name += " ";
				String person = (j >= 9 ? "[" : " [") + (j + 1) + "]" + name + " ";
				if (entry[j].equals("합류함")) {
					content = po.specialFont("default", po.f_RED, person);
				} else {
					content = po.specialFont("default", po.f_BLACK, person);
				}
				System.out.print(content);
				if (j % 4 == 3)
					System.out.println();
			}
			content = "함께할 " + (i + 1) + "번째 선수를 골라주세요(남은선택:" + (9 - i) + "번)\n>>";
			content = po.specialFont(po.b_WHITE, po.f_PURPLE, content);
			System.out.print(content);
			int choice = input_I();
			if (choice < 1 || choice > 32) {
				System.out.println("1~32를 입력해주세요");
				i--;
				continue;
			}
			choice--;
			if (entry[choice].equals("합류함")) {
				System.out.println("이미 합류한 선수입니다");
				i--;
				continue;
			}
			list.add(entry[choice]);
			content = "\n'" + cName + "'에 '" + entry[choice] + "'선수가 합류합니다!\n";
			content = po.specialFont(po.b_WHITE, po.f_RED, content);
			System.out.println(content);
			entry[choice] = "합류함";
		}
		Collections.shuffle(list);
		list.toArray(userEntry);
		// 게임 오프닝
		content = cName + "의 오늘 경기, 타순 여러분께 확인 드리겠습니다";
		po.commentator(content, false);
		System.out.print(po.specialFont(po.b_CYAN, po.f_PURPLE, ""));
		for (int i = 0; i < userEntry.length; i++) {
			String name = userEntry[i];
			if (name.length() < 3)
				name += " ";
			System.out.println(" " + (i + 1) + " " + name + "\t\t");
			try {
				Thread.sleep(145);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(" P $#%\t\t\n 감독 ^&*\t");
		return userEntry;
	}

	public static void playGame(String[] entry, String club, boolean isMaster) {
		PrintOption po = PrintOption.getPO();
		byte inning = 1;
		int input_I;
		int score = 0;
		byte[] num = new byte[3];
		Random rd = new Random();
		while (inning < 10) {
			int rdcomment = rd.nextInt(4);
			intro(inning, rdcomment, entry, club);
			for (byte i = 0; i < num.length; i++) {
				num[i] = (byte) (rd.nextInt(9) + 1);
				for (int j = 0; j < i; j++)
					if (num[i] == num[j])
						i--;
			}
			System.out.println();
			byte[] digits = new byte[3];
			while (true) {
				if (isMaster) {
					System.err.print(po.RESET + "답 : ");
					for (byte i = 0; i < num.length; i++)
						System.err.print(num[i] + " ");
					System.out.println("");
				}
				String content;
				if (inning != 9)
					content = po.specialFont("default", po.f_GREEN,
							"누적 획득점수:" + score + "\n자릿수의 값이 중복되지 않는 3자리수를 입력하세요(0제외, 1~9, 위치무관) [맞춘수/점수]"
									+ "\n아웃[0/0] 볼[1/3] 안타[2/12] 홈런[3/45]\n>>");
				else
					content = po.specialFont("default", po.f_GREEN,
							"누적 획득점수:" + score + "\n자릿수의 값이 중복되지 않는 3자리수를 입력하세요(0제외, 1~9, 위치무관) [맞춘수/점수]"
									+ "\n아웃[0/0] 볼[1/6] 안타[2/24] 홈런[3/50] 4번타자 보너스 : 점수2배!!\n>>");
				System.out.print(content);
				input_I = input_I();
				digits[0] = (byte) (input_I / 100);
				digits[1] = (byte) (input_I / 10 % 10);
				digits[2] = (byte) (input_I % 10);
				if (digits[0] != digits[1] && digits[1] != digits[2] && digits[0] != digits[2] && input_I > 100
						&& input_I < 999 && digits[0] != 0 && digits[1] != 0 && digits[2] != 0)
					break;
			}
			byte count = 0;
			for (byte cpu : num)
				for (byte player : digits) {
					if (cpu == player)
						count++;
				}
			int aceBouns = 1;
			if (inning == 9)
				aceBouns = 2;
			switch (count) {
			case 0:
				break;
			case 1:
				score += 3 * aceBouns;
				break;
			case 2:
				score += 12 * aceBouns;
				break;
			case 3:
				score += 45 * aceBouns;
				break;
			}
			System.out.println(count);
			inning++;
		}
	}

	public static void intro(byte inning, int rdComment, String[] userEntry, String club) {
		PrintOption po = PrintOption.getPO();
		String pName = userEntry[inning - 1];
		String c;
		if (inning == 1) {
			c = pName + "선수 타석에 올라왔습니다";
			po.commentator(c, false);
			switch (rdComment) {
			case 0:
				c = "공격적인 선수에요. 이번경기에서 활약을 보여줄지 기대가 됩니다.";
				break;
			case 1:
				c = pName + ", 경험이 많은 선수죠";
				break;
			case 2:
				c = "저번 시즌 무안타를 보여줬지만 타수는 많은 " + pName;
				break;
			case 3:
				c = "저번 경기에서 " + pName + "선수가 잘 열어줬죠...";
				break;
			}
			po.commentator(c, true);
		} else if (inning == 9) {
			if (rdComment % 2 == 0) {
				c = "경기장의 열기가 절정에 이릅니다 " + club + "의 마지막 공격";
				po.commentator(c, true);
				c = "%점차의 명승부.. 4번타자 " + pName + ", 마운드에 오릅니다";
				po.commentator(c, true);
				c = "이번에도 보여주나요? " + pName + "선수 ";
				po.commentator(c, false);
			} else {
				c = inning + "회$ 투 스트라이크 원아웃의 상황 4번타자 " + pName + "!";
				po.commentator(c, false);
				c = "반드시 점수를 내야합니다!!";
				po.commentator(c, true);
			}
		} else {
			if (rdComment % 2 == 0) {
				c = inning + "회$ 원 아웃이지만 절호의 기회가 있는 " + club;
				po.commentator(c, false);
				c = "이번에도 보여주나요? " + pName + "선수 ";
				po.commentator(c, true);
			} else {
				c = inning + "회$ " + club + "의 공격 돌아옵니다";
				po.commentator(c, false);
				c = pName + "!";
				po.commentator(c, false);
				switch (rdComment) {
				case 0:
					c = "뭔가 보여줘야합니다";
					break;
				case 1:
					c = pName + "선수에게 바라는건 삼진아웃만 당하지 않으면 되는데...";
					break;
				case 2:
					c = "끈질긴 선수죠 포수 싸인 보냅니다";
					break;
				case 3:
					c = pName + ", 저번시즌 큰 기여를 한 선수죠";
					break;
				}
				po.commentator(c, true);
			}
		}
	}

}
