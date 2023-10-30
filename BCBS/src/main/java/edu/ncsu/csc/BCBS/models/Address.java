package edu.ncsu.csc.BCBS.models;

/**
 * A class representing a user's address
 *
 * @author Gabe Weaver, Godsend Cheung
 */
public class Address {

    /**
     * The city that the associated user resides in
     */
    private String city;

    /**
     * The state that the associated user resides in
     */
    private String state;

    /**
     * The associated user's street address
     */
    private String address;

    /**
     * The ZIP Code associated with the address
     */
    private String zip;

    /**
     * Empty constructor for Jackson mapping
     */
    public Address() {
    }

    /**
     * Constructor for the address object
     *
     * @param city    the associated user's city of residence
     * @param state   the associated user's state of residence
     * @param address the associated user's street address
     * @param zip     the associated user's ZIP Code
     */
    public Address(String city, String state, String address, String zip) {
        setCity(city);
        setState(state);
        setAddress(address);
        setZip(zip);
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    private void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    private void setZip(String zip) {
        this.zip = zip;
    }
}
