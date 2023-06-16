package api.pojo;


import lombok.Data;

@Data
public class Credentials {
    private String email;
    private String password;

    public Credentials(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Credentials getCredentials(User user) {
        return new Credentials(user);
    }
}
