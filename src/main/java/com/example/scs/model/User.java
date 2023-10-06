package com.example.scs.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class User {

    @Id
    private Long user_id;
    private String email;
    private boolean enabled;
    private String password;
    private String fullName;

	private String reset_password_token;

    public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStud_id() {
		return stud_id;
	}

	public void setStud_id(String stud_id) {
		this.stud_id = stud_id;
	}

	public String getFac_id() {
		return fac_id;
	}

	public void setFac_id(String fac_id) {
		this.fac_id = fac_id;
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	private String role;
    private String stud_id;
    private String fac_id;

    //vertical
    private String vertical;

//    private Student student;
//    private Faculty faculty;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<User_Role> roles = new HashSet<User_Role>();
//    public void addRole(User_Role role) {
//        this.roles.add(role);
//    }
//    @MappedCollection(idColumn = "id")
//    private Set<User_Role> roles = new HashSet<>();
//    public void addRole(Role role) {
//        roles.add(new User_Role(role.getRole_id()));
//    }
//    private String role_ind;
//
//    public String getRole_ind() {
//        return role_ind;
//    }
//
//    public void setRole_ind(String role_ind) {
//        this.role_ind = role_ind;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", stud_id='" + stud_id + '\'' +
                ", fac_id='" + fac_id + '\'' +
                ", vertical='" + vertical + '\'' +
                '}';
    }


//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
}