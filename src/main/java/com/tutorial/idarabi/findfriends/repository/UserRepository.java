package com.tutorial.idarabi.findfriends.repository;


import com.tutorial.idarabi.findfriends.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("{'fullName': {$regex: ?0, $options: 'i'}}")
    List<User> findByFullNameContainingIgnoreCase(String fullName);

    @Query("{'username': {$regex: ?0, $options: 'i'}}")
    List<User> findByUsernameContainingIgnoreCase(String username);

    @Query("{'$or': [{'username': {$regex: ?0, $options: 'i'}}, {'fullName': {$regex: ?0, $options: 'i'}}]}")
    List<User> searchUsers(String searchTerm);

    List<User> findByIsActiveTrue();

    @Query("{'followers': ?0}")
    List<User> findUsersByFollowerId(String followerId);

    @Query("{'following': ?0}")
    List<User> findUsersByFollowingId(String followingId);
}
