package BoardAdv.AnonymLog.mapper;

import BoardAdv.AnonymLog.dto.MemberDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MemberMapper {
    public Member memberDtoToEntity(MemberDto dto) {
        Member entity = Member.builder()
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .isHen(dto.getIsHen())
                .build();
        return entity;
    }

    public MemberDto memberEntityToDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setNickname(member.getNickname());
        dto.setPassword(member.getPassword());
        return dto;
    }
}
