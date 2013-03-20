package com.wraith.repository.entity;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.rest.repository.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User: rowan.massey
 * Date: 06/07/12
 * Time: 23:23
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "user_id")),
        @AttributeOverride(name = "version", column = @Column(name = "user_version")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "user_created_date")),
        @AttributeOverride(name = "updatedDate", column = @Column(name = "user_updated_date"))
}
)
public class Users extends BaseEntity implements Serializable {
    private long version;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int enabled;
    private Address address;
    private Set<Groups> groups = new HashSet<>();

    public Users() {

    }

    public Users(String firstName, String lastName, String userName, String password) {
        super();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUserName(userName);
        this.setPassword(password);
        this.setEnabled(1);
    }

    @Version
    @Column(name = "user_version")
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    @Column(name = "user_firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //    @NotNull(message = "User last name cannot be null.")
    @Column(name = "user_lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NaturalId
    @Column(name = "user_userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @RestResource(exported = false)
//    @NotNull(message = "Password cannot be null")
    @Column(name = "user_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "user_enabled", nullable = false)
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Column(name = "user_dateofbirth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "User_Groups", joinColumns = {@JoinColumn(name = "user_groups_user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_groups_group_id", referencedColumnName = "group_id")})
    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    @Transient
    public String getUserFullName() {
        return this.getFirstName().concat(" ").concat(getLastName());
    }
}
