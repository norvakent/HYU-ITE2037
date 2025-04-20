package lab_6;

public class Language {
  protected String name;
  protected int numSpeakers;
  protected String regionsSpoken;
  protected String wordOrder;

  public Language(String name, int numSpeakers, String reigionsSpoken, String wordOrder) {
    this.name = name;
    this.numSpeakers = numSpeakers;
    this.regionsSpoken = reigionsSpoken;
    this.wordOrder = wordOrder;
  }

  public void getInfo() {
    System.out.println(this.name + " is spoken by " +
                       this.numSpeakers + " people mainly in " +
                       this.regionsSpoken + ".\nThe language follows the word order: " +
                       this.wordOrder + ".\n");
  }

  public static void main(String[] args) {
    System.out.println("---------- Language ----------");
    Language spanish = new Language("Spanish", 555000000, "Spain, Latin America, and Equatorial Guinea", "subject-verb-object");
    spanish.getInfo();
    System.out.println("----------Koreanic----------");
    Language korean = new Koreanic("Korean", 80400000);
    korean.getInfo();
    Language jeju_uh = new Koreanic("Jeju Language", 695500);
    jeju_uh.getInfo();
    System.out.println("----------Mayan----------");
    Mayan kiche = new Mayan("Ki'che'", 2330000);
    kiche.getInfo();
  }
}
