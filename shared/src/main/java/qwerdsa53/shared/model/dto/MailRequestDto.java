package qwerdsa53.shared.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qwerdsa53.shared.model.type.MailType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailRequestDto {
    private String recipient;
    private MailType mailType;
}
