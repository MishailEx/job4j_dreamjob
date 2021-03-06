package ru.job4j.dream.model;

import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String description;
    private String created;
    private String email;

    public Post() {
    }

    public Post(int id, String name, String description, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
    }

    public Post(int id, String name, String description, String created, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Post post = (Post) o;
        return Objects.equals(name, post.name) && Objects.equals(description, post.description) && Objects.equals(email, post.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, email);
    }
}