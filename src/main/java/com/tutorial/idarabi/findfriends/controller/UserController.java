package com.tutorial.idarabi.findfriends.controller;


import com.tutorial.idarabi.findfriends.dto.UserProfileDto;
import com.tutorial.idarabi.findfriends.dto.LocationDto;
import com.tutorial.idarabi.findfriends.model.User;
import com.tutorial.idarabi.findfriends.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId,
                                            @RequestParam(required = false) String currentUserId) {
        try {
            UserProfileDto profile = userService.getUserProfile(userId, currentUserId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok(new UserProfileDto(user));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam String q) {
        try {
            List<User> users = userService.searchUsers(q);
            List<UserProfileDto> profiles = users.stream()
                    .map(UserProfileDto::new)
                    .collect(java.util.stream.Collectors.toList());
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllActiveUsers();
            List<UserProfileDto> profiles = users.stream()
                    .map(UserProfileDto::new)
                    .collect(java.util.stream.Collectors.toList());
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/{userId}/follow")
    public ResponseEntity<?> followUser(@PathVariable String userId, @RequestParam String followerId) {
        try {
            boolean success = userService.followUser(followerId, userId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "User followed successfully" : "Already following this user");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/{userId}/unfollow")
    public ResponseEntity<?> unfollowUser(@PathVariable String userId, @RequestParam String followerId) {
        try {
            boolean success = userService.unfollowUser(followerId, userId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "User unfollowed successfully" : "Not following this user");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable String userId) {
        try {
            List<UserProfileDto> followers = userService.getFollowers(userId);
            return ResponseEntity.ok(followers);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<?> getFollowing(@PathVariable String userId) {
        try {
            List<UserProfileDto> following = userService.getFollowing(userId);
            return ResponseEntity.ok(following);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{userId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable String userId,
                                            @Valid @RequestBody LocationDto locationDto) {
        try {
            User user = userService.updateUserLocation(userId, locationDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Location updated successfully");
            response.put("location", user.getLocation());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}