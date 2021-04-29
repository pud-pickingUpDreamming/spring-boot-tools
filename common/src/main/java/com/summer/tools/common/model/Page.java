package com.summer.tools.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author john
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    private Integer count;
    private List<T> rows;
}
