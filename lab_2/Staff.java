package lab_2;
public class Staff{
	private String name;
	private int age;
	private String department;
	private int workDays;
	private int vacationDays;

	public Staff(String name, int age) {
		this.name = name;
		this.age = age;
		this.department = "None";
		this.workDays = 0;
		this.vacationDays = 20;
	}

	public Staff(String name, int age, String department, int workDays, int vacationDays) {
		this.name = name;
		this.age = age;
		this.department = department;
		this.workDays = workDays;
		this.vacationDays = vacationDays;
	}

	public String getName() {
		return this.name;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setWorkDays(int workDays) {
		this.workDays = workDays;
	}

	public void setCareer(String department, int workDays) {
		this.department = department;
		this.workDays = workDays;
	}

	public boolean sameCareer(Staff other) {
		return (this.department.equals(other.department) && this.workDays == other.workDays);
	}

	public String toString() {
		return "Name: " + this.name + ", Age: " + this.age + ", Department: " + this.department + ", workDays: " + this.workDays + ", vacationDays: " + this.vacationDays;
	}

	public void vacation(int vacationDays) {
		if (this.vacationDays >= vacationDays) {
			this.vacationDays -= vacationDays;
			System.out.println(this.name + ", 휴가 " + vacationDays + " 사용. 남은 휴가 일 수: " + this.vacationDays);
		} else {
			System.out.println(this.name + ", 남은 휴가 일 수 부족.");
		}
	}
}