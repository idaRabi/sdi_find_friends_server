package com.tutorial.idarabi.findfriends.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Indexed(unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonIgnore
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String profilePicture;
    private String bio;
    private boolean isActive = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Following/Followers relationships
    private Set<String> following = new HashSet<>();
    private Set<String> followers = new HashSet<>();

    // Location for nearby friends feature
    private Location location;

    // Constructors
    public User() {}

    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Set<String> getFollowing() { return following; }
    public void setFollowing(Set<String> following) { this.following = following; }

    public Set<String> getFollowers() { return followers; }
    public void setFollowers(Set<String> followers) { this.followers = followers; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    // Helper methods
    public void addFollowing(String userId) {
        this.following.add(userId);
    }

    public void removeFollowing(String userId) {
        this.following.remove(userId);
    }

    public void addFollower(String userId) {
        this.followers.add(userId);
    }

    public void removeFollower(String userId) {
        this.followers.remove(userId);
    }

    public int getFollowingCount() {
        return following.size();
    }

    public int getFollowersCount() {
        return followers.size();
    }
}