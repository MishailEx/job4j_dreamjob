package ru.job4j.dream.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String description;
    private int cityId;
    private String created;
    private String email;

    public Candidate(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Candidate(int id, String name, String description, int cityId, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityId = cityId;
        this.email = email;
    }

    public Candidate(int id, String name, String description, int cityId, String created, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityId = cityId;
        this.created = created;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return cityId == candidate.cityId && Objects.equals(name, candidate.name) && Objects.equals(description, candidate.description) && Objects.equals(email, candidate.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, cityId, email);
    }
}