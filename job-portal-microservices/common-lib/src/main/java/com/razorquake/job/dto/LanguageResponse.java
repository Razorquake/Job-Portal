package com.razorquake.job.dto;

import com.razorquake.job.domain.LanguageProficiency;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageResponse {

    private Long id;
    private String name;
    private LanguageProficiency proficiency;
    private Integer displayOrder;
}
