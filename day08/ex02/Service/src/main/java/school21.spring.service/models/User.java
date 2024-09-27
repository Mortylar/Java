package school21.spring.service.models;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
public class User {

    private Long id;
    private String email;

    public User() {}

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public void setId(Long id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }

    public Long getId() { return this.id; }

    public String getEmail() { return this.email; }

    @Override
    public String toString() {
        return String.format("User(id = %d, email = %s)", getId(), getEmail());
    }
}
