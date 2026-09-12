package com.bet.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.bet.domain.enums.BetType;
import com.bet.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "USER")
@AttributeOverride(name = "id", column = @Column(name = "ID_USER"))
public class User extends BaseDomain {

	private String name;

	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String email;

	@JsonIgnore
	private String password;

	@JsonIgnore
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "Perfis")
	private Set<Integer> perfis = new HashSet<>();

	@JsonIgnore
	@ElementCollection
	@OneToMany(mappedBy = "user")
	private List<Bet4bet> bets = new ArrayList<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "USER_FOLLOWERS", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_FOLLOWER"))
	private List<User> followers = new ArrayList<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "USER_FOLLOWERS", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_FOLLOWER"))
	private List<User> followBy = new ArrayList<>();

	@Column(name = "enabled")
	private boolean enabled;

	public User() {
		addPerfil(Perfil.CLIENT);
		this.enabled = false;
	}

	public User(String username, String name, String password, String email) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		addPerfil(Perfil.CLIENT);
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Bet4bet> getBets() {
		return bets;
	}

	public void setBets(List<Bet4bet> bets) {
		this.bets = bets;
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public List<User> getFollowBy() {
		return followBy;
	}

	public void addFollower(User user) {
		this.followers.add(user);
		// user.getFollowBy().add(this);
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

	/**
	 * 
	 * @param user
	 * @return true se o user passado em argumento segue o user
	 */
	public boolean isFollower(User user) {
		for (User follower : this.followers) {
			if (follower.getId().equals(user.getId())) {
				return true;
			}
		}
		return false;
	}

	public List<Bet4bet> getPublicBets() {
		List<Bet4bet> publicBets = new ArrayList<>();

		for (Bet4bet bet : bets) {
			if (bet.getBetType().equals(BetType.PUBLIC)) {
				publicBets.add(bet);
			}
		}
		return publicBets;
	}

}
