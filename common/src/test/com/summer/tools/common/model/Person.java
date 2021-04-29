package com.summer.tools.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "age")
    private Integer age;

    @XmlAttribute(name = "sex")
    private String sex;

    @XmlElement(name = "favors")
    private Favors favors;

    public static Person getPerson() {
        return new Builder().build();
    }

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Favors {
        @XmlAttribute(name = "favor")
        private List<String> favorList;
        @XmlValue
        private int value;
    }

    static class Builder {
        public Person build() {
            Favors favors = new Favors();
            favors.setFavorList(Arrays.asList("篮球","旅游")).setValue(2);
            return new Person().setAge(18).setName("张三").setSex("男").setFavors(favors);
        }
    }
}
