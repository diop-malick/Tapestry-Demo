package net.ilabs.skillbrowser.domain.model;

// Generated 23 sept. 2013 15:55:50 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.tapestry5.dom.Element;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "skillbrowser", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String login;
	private String password;
	private String fullname;
	private Set<Skill> skills = new HashSet<Skill>(0);

	public User() {
	}

	public User(String login, String password, String fullname) {
		this.login = login;
		this.password = password;
		this.fullname = fullname;
	}

	public User(String login, String password, String fullname,
			Set<Skill> skills) {
		this.login = login;
		this.password = password;
		this.fullname = fullname;
		this.skills = skills;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "login", unique = true, nullable = false, length = 25)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false, length = 25)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "fullname", nullable = false, length = 45)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@ManyToMany(fetch = FetchType.EAGER , cascade = { CascadeType.ALL })
	@JoinTable(name = "user_skills", catalog = "skillbrowser", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "skill_id", nullable = false, updatable = false) })
	public Set<Skill> getSkills() {
		return this.skills;
		
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public void addSkill(Skill skill) {
		skills.add(skill);
	}
 
	public void removeSkill(Skill skill) {
		if (skills!=null && skills.size() > 0){
			for (Skill skillTmp : skills) {
				if (skillTmp.getSkillId() != null && skillTmp.getSkillId().equals(skill.getSkillId())){
					skills.remove(skillTmp);
					break;
				}
			}
		}
	}
}
