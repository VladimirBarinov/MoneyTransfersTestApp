package com.barinov.vladimir.model;

/**
 * Information about users
 */
public class Person {
    /**
     * User Id
     */
    private Integer personId;
    /**
     * User full name
     */
    private String fullname;

    public Person() {
    }

    public Person(String fullname, Integer personId) {
        this.fullname = fullname;
        this.personId = personId;
    }

    public Person(Integer id, String fullname) {
        this.fullname = fullname;
        this.personId = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
