package BoardAdv.AnonymLog.service;

import BoardAdv.AnonymLog.dto.MemberDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.mapper.MemberMapper;
import BoardAdv.AnonymLog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    public Member save(MemberDto dto) {
        Member member = memberMapper.memberDtoToEntity(dto);
        memberRepository.save(member);
        return member;
    }

    public Member findById(Long id) {
       return memberRepository.findById(id).orElseThrow();
    }

    public MemberDto memberEntityToDto(Member member) {
        return memberMapper.memberEntityToDto(member);
    }
}
