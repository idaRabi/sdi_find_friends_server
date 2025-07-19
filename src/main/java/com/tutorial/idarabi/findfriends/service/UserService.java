package com.tutorial.idarabi.findfriends.service;

import com.tutorial.idarabi.findfriends.dto.UserProfileDto;
import com.tutorial.idarabi.findfriends.dto.UserRegistrationDto;
import com.tutorial.idarabi.findfriends.dto.LocationDto;
import com.tutorial.idarabi.findfriends.model.User;
import com.tutorial.idarabi.findfriends.model.Location;
import com.tutorial.idarabi.findfriends.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public User createUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
//        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPassword(registrationDto.getPassword());
        user.setFullName(registrationDto.getFullName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User updateUser(String userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFullName(updatedUser.getFullName());
            user.setBio(updatedUser.getBio());
            user.setProfilePicture(updatedUser.getProfilePicture());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<User> searchUsers(String searchTerm) {
        return userRepository.searchUsers(searchTerm);
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    @Transactional
    public boolean followUser(String followerId, String followingId) {
        if (followerId.equals(followingId)) {
            throw new RuntimeException("Users cannot follow themselves");
        }

        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> following = userRepository.findById(followingId);

        if (follower.isPresent() && following.isPresent()) {
            User followerUser = follower.get();
            User followingUser = following.get();

            if (followerUser.getFollowing().contains(followingId)) {
                return false; // Already following
            }

            followerUser.addFollowing(followingId);
            followingUser.addFollower(followerId);

            userRepository.save(followerUser);
            userRepository.save(followingUser);

            return true;
        }

        throw new RuntimeException("User not found");
    }

    @Transactional
    public boolean unfollowUser(String followerId, String followingId) {
        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> following = userRepository.findById(followingId);

        if (follower.isPresent() && following.isPresent()) {
            User followerUser = follower.get();
            User followingUser = following.get();

            if (!followerUser.getFollowing().contains(followingId)) {
                return false; // Not following
            }

            followerUser.removeFollowing(followingId);
            followingUser.removeFollower(followerId);

            userRepository.save(followerUser);
            userRepository.save(followingUser);

            return true;
        }

        throw new RuntimeException("User not found");
    }

    public List<UserProfileDto> getFollowers(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getFollowers().stream()
                    .map(followerId -> userRepository.findById(followerId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(UserProfileDto::new)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("User not found");
    }

    public List<UserProfileDto> getFollowing(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getFollowing().stream()
                    .map(followingId -> userRepository.findById(followingId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(UserProfileDto::new)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("User not found");
    }

    public UserProfileDto getUserProfile(String userId, String currentUserId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            UserProfileDto profile = new UserProfileDto(u);

            if (currentUserId != null && !currentUserId.equals(userId)) {
                Optional<User> currentUser = userRepository.findById(currentUserId);
                if (currentUser.isPresent()) {
                    profile.setFollowing(currentUser.get().getFollowing().contains(userId));
                    profile.setFollowedBy(currentUser.get().getFollowers().contains(userId));
                }
            }

            return profile;
        }
        throw new RuntimeException("User not found");
    }

    public User updateUserLocation(String userId, LocationDto locationDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            Location location = new Location(locationDto.getLatitude(), locationDto.getLongitude(), locationDto.getAddress());
            u.setLocation(location);
            u.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(u);
        }
        throw new RuntimeException("User not found");
    }
}
