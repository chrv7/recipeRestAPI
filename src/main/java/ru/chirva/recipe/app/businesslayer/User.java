package ru.chirva.recipe.app.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
