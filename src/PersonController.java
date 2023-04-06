import java.util.ArrayList;
import java.util.List;

public class PersonController {
    private List<Person> persons;

    public PersonController() {
        persons = new ArrayList<>();
    }

    public void addPerson(String firstName, String lastName, int age) {
        persons.add(new Person(firstName, lastName, age));
    }

    public void deletePerson(int index) {
        if (index >= 0 && index < persons.size()) {
            persons.remove(index);
        }
    }

    public List<Person> getPersons() {
        return persons;
    }
}
