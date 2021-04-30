package com.summer.tools.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class Target {
    private String pro1;

    private String pro2;

    private PlainBean pro3;

    private String pro4;
}
