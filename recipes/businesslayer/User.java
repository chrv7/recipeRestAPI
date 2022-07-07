package recipes.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "email")
    @NotBlank
    @NotNull
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @Column(name = "password")
    @NotBlank
    @NotNull
    @Size(min=8)
    private String password;
}
