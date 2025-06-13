import java.util.Random;


public class NameGen {

}
//Class to generate random  names
class NameGenerator {
    //List  of random firstnames
    String[] firstNames = {
            "John", "Emma", "Olivia", "Liam", "Noah", "Ava", "Sophia", "James", "Mia", "Ethan",
            "Isabella", "Lucas", "Michael", "Amelia", "Elijah", "Charlotte", "Alexander", "Emily", "Benjamin", "Abigail",
            "Sebastian", "Harper", "Henry", "Evelyn", "Daniel", "Luna", "Matthew", "Ella", "Jackson", "Avery",
            "Logan", "Sofia", "David", "Scarlett", "Joseph", "Victoria", "Samuel", "Grace", "Owen", "Chloe",
            "Wyatt", "Camila", "Gabriel", "Penelope", "Jayden", "Riley", "Carter", "Zoey", "Julian", "Nora", "Max", "George", "Ivan", "Donald",
            "Jeremy", "Chris",  "Lea", "Kate", "Kira", "Robert", "Walter", "Hank", "Gustavo", "Jimmy", "Varvara"
    };

    //List of random lastnames
    String[] lastNames = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
            "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
            "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
            "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts", "White", "Fring"
    };
    //Method to generate a random fullname array
    public String[] generateNames(int size) {
        String[] nameArray = new String[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            nameArray[i] = firstName + " " + lastName;
        }

        return nameArray;
    }
}
