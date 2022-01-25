package com.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class Component {

    private int id;

    private String name;

    @Builder.Default
    private List<String> children = Collections.emptyList();
}
