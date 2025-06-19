package web.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CourseDTO {
        Long id;
        String title;
        String desc;
        Long categoryId;
        Long instructorId;
}

