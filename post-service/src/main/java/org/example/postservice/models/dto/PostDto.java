package org.example.postservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private int cntLikes;
    private List<String> tags = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    private LocalDateTime createdAt;
}

