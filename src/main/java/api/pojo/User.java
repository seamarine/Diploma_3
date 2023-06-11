package api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String email;
    private String password;
    private String name;
    public static final String EMAIL = "@gmail.com";
    public static User randomUser() {

        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL;
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);

        return new User(email, password, name);
    }
}
