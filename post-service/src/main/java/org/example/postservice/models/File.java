package org.example.postservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public File(String url, Post post) {
        this.url = url;
        this.post = post;
    }

    public File(String url) {
        this.url = url;
    }
}
