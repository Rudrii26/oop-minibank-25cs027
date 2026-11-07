package model;

import model.annotation.Id;

import java.io.Serializable;

// Practical 2 & 3: Customer entity demonstrating encapsulation, static nested classes, and cloning
public class Customer implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String mobile;

    @Id
    private final String customerId;
    private Address address;

    // Static counter for sequential Customer ID generation (Practical 2)
    private static long customerCounter = 100;

    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, null);
    }

    public Customer(String name, String email, String mobile, Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
        this.address = address;
    }

    // Public getters enforcing encapsulation (Practical 2)
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Address getAddress() {
        return address;
    }

    // Practical 3: Object cloning (implements Cloneable)
    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    // Practical 3: Public static nested class representing Address
    public static class Address implements Serializable {

        private static final long serialVersionUID = 1L;

        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() {
            return line;
        }

        public String getCity() {
            return city;
        }

        public String getPincode() {
            return pincode;
        }
    }
}
