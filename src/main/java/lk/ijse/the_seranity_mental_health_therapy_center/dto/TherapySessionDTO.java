package lk.ijse.the_seranity_mental_health_therapy_center.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapySessionDTO {
    private String id;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String status;
    private String patientId;
    private String therapistId;
}
