package lab_2;
public class StaffManager {
	public static void main(String[] args) {
		Staff staff1 = new Staff("James Wright", 29, "Accounting", 365, 15);
		Staff staff2 = new Staff("Peter Coolidge", 32, "R&D", 1095, 7);
		Staff staff3 = new Staff("Amy Smith", 27);

		System.out.println(staff1.toString());
		System.out.println(staff2.toString());
		System.out.println(staff3.toString());

		System.out.println("---\nSame Career?");
		if (staff2.sameCareer(staff3)) {
			System.out.println(staff2.getName() + " and " + staff3.getName() + ", Same.");
		} else {
			System.out.println(staff2.getName() + " and " + staff3.getName() + ", Not exactly same.");
		}

		System.out.println("...A Few years later...");
		staff3.setCareer("R&D", 1095);
		if (staff2.sameCareer(staff3)) {
			System.out.println(staff2.getName() + " and " + staff3.getName() + ", Same.");
		} else {
			System.out.println(staff2.getName() + " and " + staff3.getName() + ", Not exactly same.");
		}
		System.out.println("---");
		staff1.vacation(10);
		staff3.vacation(20);
		staff3.vacation(1);
	}
}

