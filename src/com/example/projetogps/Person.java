package com.example.projetogps;

import java.io.Serializable;

public class Person implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private int age;

  public Person(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Person [name=" + name + ", age=" + age + "]";
  }
}