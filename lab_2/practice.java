package lab_1;
import java.util.Scanner;

public class practice {
	public static void main(String[] args) {
//		gil dong go, homework.ppt
//		Name Length(Korean) : 3
//		G.D.Go submitted Homework.pdf
		Scanner scanner = new Scanner(System.in);
		String n = scanner.nextLine();
		String name, file;
		name = n.split(",")[0];
		file = n.split(",")[1];
		String[] nameSep = name.split(" ");
		String newName = "";
		int letters = nameSep.length;
		System.out.println("Name Length(Korean) : " + letters);
		for (int i = 0; i < nameSep.length; i++) {
			String temp = nameSep[i];
			if (i != nameSep.length-1) {
				newName += temp.toUpperCase().charAt(0);	
				newName += ".";
			}
			else {
				newName += temp.toUpperCase().charAt(0);
				newName += temp.toLowerCase().substring(1);
			}
		}
		String fileName = file.split("\\.")[0].trim();
		System.out.println(newName + " submitted " + fileName.toUpperCase().charAt(0) + fileName.substring(1) + ".pdf");
    scanner.close();
	}
}

