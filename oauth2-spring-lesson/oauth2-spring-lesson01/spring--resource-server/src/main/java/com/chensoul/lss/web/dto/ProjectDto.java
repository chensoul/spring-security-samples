package com.chensoul.lss.web.dto;

import java.time.LocalDate;

public record ProjectDto(

        Long id,

        String name,

        LocalDate dateCreated) {

}