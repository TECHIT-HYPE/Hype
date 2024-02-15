package com.ll.hype.domain.admin.admin.dto.request;

import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.global.enums.Gender;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberModifyRequest {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private Long phoneNumber;
    private MemberRole role;
    private Gender gender;
    private Integer shoesSize;
    private List<MultipartFile> files = new ArrayList<>();
}
