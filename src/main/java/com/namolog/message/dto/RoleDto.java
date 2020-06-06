
package com.namolog.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto{

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    private String description;

}


