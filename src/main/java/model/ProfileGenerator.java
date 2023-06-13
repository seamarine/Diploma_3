package model;

import com.github.javafaker.Faker;

public class ProfileGenerator {

    public static ProfileForm getRandom() {

        Faker faker = new Faker();
        return new ProfileForm(faker.name()
                .firstName(), faker.internet()
                .emailAddress(), faker
                .internet().password());
    }
}
