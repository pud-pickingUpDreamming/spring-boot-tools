package com.summer.tools.common.model;

import com.summer.tools.common.utils.BeanUtil;
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
public class Source {
    private String pro1;

    private String pro2;

    private PlainBean pro3;
}
