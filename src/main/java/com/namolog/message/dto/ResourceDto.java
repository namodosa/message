package com.namolog.message.dto;


import com.namolog.message.domain.NmRole;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {

    @NotBlank
    private Integer id;
    @NotBlank
    private String name;
    private String httpMethod;
    @NotBlank
    private String resourceType;
    @NotBlank
    private Integer orderNum;
    @Builder.Default
    private List<NmRole> roleList = new ArrayList<>();
    //
    private String roleName;

    public ResourceDto(Integer id, String name, String httpMethod, String resourceType, Integer orderNum) {
        this.id = id;
        this.name = name;
        this.httpMethod = httpMethod;
        this.resourceType = resourceType;
        this.orderNum = orderNum;
    }
}
