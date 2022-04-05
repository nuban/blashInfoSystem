package vip.imagin.blast.dto.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cat implements Serializable {

    private int id;
    private String name;
    private String age;
    private String addr;
}
