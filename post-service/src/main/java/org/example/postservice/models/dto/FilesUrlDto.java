package org.example.postservice.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilesUrlDto {
    List<String> files;

    public FilesUrlDto(List<String> files) {
        this.files = files;
    }
}
