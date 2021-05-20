package com.summer.tools.flowable.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Expense {

    private String userId;

    private Integer amount;

    private String remarks;

    public static Expense getExpense() {
        return new Builder().build();
    }

    static class Builder {
        public Expense build() {
            return new Expense().setUserId(UUID.randomUUID().toString())
                    .setAmount(100).setRemarks("测试费用报销");
        }
    }
}
