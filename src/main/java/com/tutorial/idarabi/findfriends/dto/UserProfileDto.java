package com.tutorial.idarabi.findfriends.dto;


import com.tutorial.idarabi.findfriends.model.User;

public class UserProfileDto {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private String profilePicture;
    private String bio;
    private boolean isActive;
    private int followingCount;
    private int followersCount;
    private boolean isFollowing;
    private boolean isFollowedBy;
    private java.time.LocalDateTime createdAt;

    // Constructors
    public UserProfileDto() {}

    public UserProfileDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.profilePicture = user.getProfilePicture();
        this.bio = user.getBio();
        this.isActive = user.isActive();
        this.followingCount = user.getFollowingCount();
        this.followersCount = user.getFollowersCount();
        this.createdAt = user.getCreatedAt();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public int getFollowingCount() { return followingCount; }
    public void setFollowingCount(int followingCount) { this.followingCount = followingCount; }

    public int getFollowersCount() { return followersCount; }
    public void setFollowersCount(int followersCount) { this.followersCount = followersCount; }

    public boolean isFollowing() { return isFollowing; }
    public void setFollowing(boolean following) { isFollowing = following; }

    public boolean isFollowedBy() { return isFollowedBy; }
    public void setFollowedBy(boolean followedBy) { isFollowedBy = followedBy; }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
}