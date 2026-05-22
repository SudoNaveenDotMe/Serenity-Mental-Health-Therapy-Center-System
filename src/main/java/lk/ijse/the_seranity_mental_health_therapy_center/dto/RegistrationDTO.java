package lk.ijse.the_seranity_mental_health_therapy_center.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    private String id;
    private LocalDate registrationDate;
    private String patientId;
    private String programId;
}
