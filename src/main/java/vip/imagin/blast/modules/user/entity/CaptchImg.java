package vip.imagin.blast.modules.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
public class CaptchImg {
    private String uuid;
    private String img;
}
