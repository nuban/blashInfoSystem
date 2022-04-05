package vip.imagin.blast.dto.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class Test implements Serializable {

    private int id;
    private int age;
    private String name;
    private Cat cat;

}
