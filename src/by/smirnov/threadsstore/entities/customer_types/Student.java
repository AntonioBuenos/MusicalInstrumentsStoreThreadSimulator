package by.smirnov.threadsstore.entities.customer_types;

import by.smirnov.threadsstore.utils.Randomizer;

public class Student extends Customer {

    public final String name;

    public Student (int number) {
        super(number);
        this.name = "Customer №" + number + " (Student)";
    }

    @Override
    public int quantityNeed() {
        return Randomizer.get(0,2);
    }

    @Override
    public String toString() {
        return name;
    }
}
