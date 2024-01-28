package Model;

public class Game {
	private static Game singletonG = new Game();
	public static Game getGame() {
		return singletonG;
	}
	public String[] getEntry() {
		String[] entry = { "강민규", "고현우", "고희청", "권승호", "김대원", "김동준", "김하영", "김혜린", "나유정", "박상윤", "서동현",
				"신혜선", "양세진", "오현진", "유승주", "윤옥산", "윤정원", "이봄", "임명택", "장지오", "정두연", "정재원", "조가현", "조영훈", "홍창민", "정세연",
				"신범식", "조자연", "임명진", "안현진", "임승환", "조준용" };
		return entry;
	}
}
