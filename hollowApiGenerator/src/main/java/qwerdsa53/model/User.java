package qwerdsa53.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Cloneable {
    private Long id;

    private String username;

    private String password;

    private String email;

    private boolean enabled;

    private Set<Long> friends = new HashSet<>();

    private Set<Long> userBlackList = new HashSet<>();

    private Set<Long> friendRequests = new HashSet<>();

    private String profilePictureUrl;

    private LocalDateTime createdAt;

    private LocalDate birthday;

    private String description;

    private LocalDateTime lastSeen;

    private LocalDateTime updatedAt;

    public User(Long userId) {
        this.id = userId;
    }

}
