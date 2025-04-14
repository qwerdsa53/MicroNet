package org.example.userservice.repo;

import jakarta.persistence.LockModeType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qwerdsa53.shared.model.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.id = :id")
    void enableUserById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.enabled = true WHERE u.email = :email")
    int enableUserByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastSeen = :time WHERE u.id = :id")
    void updateLastSeen(@Param("id") Long id, @Param("time") LocalDateTime time);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :id")
    boolean existsById(@NotNull @Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id IN :friendIds")
    Page<User> findFriendRequestsByUserId(@Param("friendIds") Set<Long> friendIds, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id IN :friendIds")
    Page<User> findBlackListedUsers(@Param("friendIds") Set<Long> friendIds, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findUserWithLock(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id IN :friendIds")
    Page<User> findFriendsByUserId(@Param("friendIds") Set<Long> friendIds, Pageable pageable);

}
