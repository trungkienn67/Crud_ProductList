package thidk.codelean.jdbc;

public class User {

    private int id;
    private String Name;
    private String email;
    private String Password;

    public User(String Name, String email, String Password) {
        this.Name = Name;
        this.email = email;
        this.Password = Password;
    }

    public User(int id, String Name, String email, String Password) {
        this.id = id;
        this.Name = Name;
        this.email = email;
        this.Password = Password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void Name(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void getPassword(String Password) {
        this.Password = Password;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", Name=" + Name + ", email=" + email + ",  Password=" + Password + "]";
    }
}
