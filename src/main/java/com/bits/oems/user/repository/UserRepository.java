package com.bits.oems.user.repository;

import com.bits.oems.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	User findByEmail(String email);

	@Query("{ '$or' : [ { 'username': ?0 }, { 'email': ?1 } ] }")
	User findByUsernameOrEmail(String username, String email);

}
