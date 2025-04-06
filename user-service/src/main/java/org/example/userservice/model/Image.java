package org.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile_pictures")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Image implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Image(String url, LocalDateTime uploadedAt, User user) {
        this.url = url;
        this.uploadedAt = uploadedAt;
        this.user = user;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", post=" + user.getId() +
                '}';
    }

    @Override
    public Image clone() {
        try {
            Image cloned = (Image) super.clone();
            cloned.uploadedAt = (this.uploadedAt != null) ? LocalDateTime.from(this.uploadedAt) : null;
            cloned.user = null;
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
