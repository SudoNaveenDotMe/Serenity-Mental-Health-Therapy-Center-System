package lk.ijse.the_seranity_mental_health_therapy_center.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentRegistrationDTO {
    private String id;
    private String patientName;
    private String programName;
    private LocalDate registrationDate;
}
