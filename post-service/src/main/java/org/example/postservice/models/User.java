package org.example.postservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "friend_id")
    private Set<Long> friends = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_blacklisted_user", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "blacklisted_user_id")
    private Set<Long> userBlackList = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "friend_requests", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "requester_id")
    private Set<Long> friendRequests = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Image> profilePictures = new ArrayList<>();


    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column()
    private String description;

    @Column()
    private LocalDateTime lastSeen;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public User(Long id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public User clone() {
        try {
            User cloned = (User) super.clone();
            cloned.roles = new HashSet<>(this.roles);
            cloned.friends = new HashSet<>(this.friends);
            cloned.userBlackList = new HashSet<>(this.userBlackList);
            cloned.friendRequests = new HashSet<>(this.friendRequests);
            cloned.profilePictures = this.profilePictures != null
                    ? this.profilePictures.stream().map(Image::clone).collect(Collectors.toList())
                    : new ArrayList<>();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
