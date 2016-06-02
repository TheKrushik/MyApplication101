package info.krushik.android.myapplication101;

public class Student {

    public long id;
    public String FirstName;
    public String LastName;
    public long Age;

    public Student() {
    }

    public Student(String firstName, String lastName, long age) {
        FirstName = firstName;
        LastName = lastName;
        Age = age;
    }

    @Override
    public String toString() {
        return String.format("id %s, %s %s, age: %s", id, FirstName, LastName, Age);
    }
}

