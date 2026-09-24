package com.razorquake.job.dto;

import com.razorquake.job.domain.LanguageProficiency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageRequest {

    @NotBlank(message = "Language name cannot be blank")
    private String languageName;

    @NotNull(message = "Language proficiency cannot be null")
    private LanguageProficiency proficiency;

    private Integer displayOrder;
}
