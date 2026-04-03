package pl.kaminski.forum.users.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class PersonalInfoVO {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public static PersonalInfoVO create(String firstName, String lastName, LocalDate birthDate) {
        var newPersonalInfo = new PersonalInfoVO();
        newPersonalInfo.setFirstName(firstName);
        newPersonalInfo.setLastName(lastName);
        newPersonalInfo.setBirthDate(birthDate);
        return newPersonalInfo;
    }
}
