package qwerdsa53.shared.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "DTO для представления поста в системе")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @Schema(description = "Уникальный идентификатор поста", example = "1024")
    private Long id;

    @Schema(description = "Идентификатор пользователя, который создал пост", example = "42")
    private Long userId;

    @Schema(description = "Заголовок поста", example = "Мой первый пост")
    private String title;

    @Schema(description = "Содержимое поста", example = "Это текст моего первого поста!")
    private String text;

    @Schema(description = "Количество лайков у поста", example = "125")
    private int cntLikes;

    @Schema(description = "Список тегов, прикрепленных к посту", example = "[\"java\", \"spring\", \"backend\"]")
    private List<String> tags = new ArrayList<>();

    @Schema(description = "Список URL прикрепленных файлов", example = "[\"http://example.com/image1.png\", \"http://example.com/image2.png\"]")
    private List<String> files = new ArrayList<>();

    @Schema(description = "Дата и время создания поста", example = "2025-03-19T16:30:00")
    private LocalDateTime createdAt;
}
