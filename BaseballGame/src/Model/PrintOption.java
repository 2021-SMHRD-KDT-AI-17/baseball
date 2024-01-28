package Model;

public class PrintOption {
	private static PrintOption singletonPO=new PrintOption();
	public final String RESET = "\u001B[0m";
	public final String f_BLACK = "\u001B[30m";
	public final String f_RED = "\u001B[31m";
	public final String f_GREEN = "\u001B[32m";
	public final String f_YELLOW = "\u001B[33m";
	public final String f_BLUE = "\u001B[34m";
	public final String f_PURPLE = "\u001B[35m";
	public final String f_CYAN = "\u001B[36m";
	public final String f_WHITE = "\u001B[37m";
	public final String b_BLACK = "\u001B[40m";
	public final String b_RED = "\u001B[41m";
	public final String b_GREEN = "\u001B[42m";
	public final String b_YELLOW = "\u001B[43m";
	public final String b_BLUE = "\u001B[44m";
	public final String b_PURPL = "\u001B[45m";
	public final String b_CYAN = "\u001B[46m";
	public final String b_WHITE = "\u001B[47m";

	private PrintOption() {};
	public static PrintOption getPO() {
		return singletonPO;
	}
//	public void print() {
//		System.out.println("\u001b[31m test");
//		System.out.println(b_GREEN + "This text has a green background but default text!" + RESET);
//		System.out.println(f_YELLOW + "This text has yellow text but a default background!" + RESET);
//		System.out.println(f_BLUE + "This text has blue text but a default background!" + RESET);
//		System.out.println(f_CYAN + "This text has cyan text but a default background!" + RESET);
//		System.out.println(f_BLACK + "This text has black text but a default background!" + RESET);
//		System.out.println("\u001B[31mThis text has black text but a default background!\u001B[0m");
//		System.out.println("\u001B[31mThis text has black text but a default background!" + RESET);
//		System.out.println("\u001B[32mThis text has black text but a default background!" + RESET);
//		System.out.println(f_RED + "This text has red text but a default background!" + RESET);
//		System.out.println(
//				b_GREEN + f_RED + "This text has a green background and red text!" + RESET);
//		System.out.println(
//				b_GREEN + f_BLACK + "This text has a green background and black text!" + RESET);
//		System.out.println(
//				b_GREEN + f_YELLOW + "This text has a green background and yellow text!" + RESET);
//		System.out.println(yellow("This text has yellow text but a default background!"));
//	}
	public String specialFont(String bg,String ft,String content) {
		if(bg.equals("default"))return RESET+ft+content;
		return RESET+bg + ft+content;
	}
	public void commentator(String content, boolean caster_commentator){
		System.out.print(RESET);
		if(caster_commentator)
			System.out.print(f_PURPLE);
		else
			System.out.print(f_CYAN);
		for(int i=0;i<content.length();i++) {
			System.out.print(content.charAt(i));
			try {
				if(content.charAt(i)==' ')
					Thread.sleep(50);
				else
					Thread.sleep(95);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			System.out.println(RESET);
	}
}
