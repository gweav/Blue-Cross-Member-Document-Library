package edu.ncsu.csc.BCBS.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a Blue Cross Blue Shield user (subscriber or dependent)
 *
 * @author Gabe Weaver, Godsend Cheung
 */
public class User {

    /**
     * The user's ID
     */
    private String id;

    /**
     * The user's name
     */
    private String name;

    /**
     * The user's data of birth
     */
    private LocalDate dob;

    /**
     * The user's gender
     */
    private String gender;

    /**
     * The user's address
     */
    private Address addr;

    /**
     * Policies owned by this user
     * Policies may span up to three years
     */
    private List<Policy> policies;

    /**
     * Empty constructor for Jackson mapping
     */
    public User() {
    }

    /**
     * Constructor for the user object
     *
     * @param id     the user's ID
     * @param name   the user's name
     * @param dob    the user's date of birth
     * @param gender the user's gender
     */
    public User(String id, String name, LocalDate dob, String gender, Address addr) {
        setId(id);
        setName(name);
        setDob(dob);
        setGender(gender);
        setAddr(addr);
        setPolicies(new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    private void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    private void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddr() {
        return addr;
    }

    private void setAddr(Address addr) {
        this.addr = addr;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    private void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    /**
     * Adds the specified policy to this user's list of policies
     *
     * @param policy the policy to add
     * @return true if added successfully
     */
    public boolean addPolicy(Policy policy) {
        if (policy.getYear().getValue() >= LocalDate.now().getYear() - 2) {
            return policies.add(policy);
        }
        return false;
    }
}
