package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Long join(Member member) {

//		Optional<Member> result = memberRepository.findByName(member.getName());
//		result.ifPresent(m -> {
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
//		});

		long start = System.currentTimeMillis();
		try {
			validateDuplicateMember(member);
			memberRepository.save(member);
			return member.getId();
		} finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			System.out.println(timeMs);
		}
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getName()).ifPresent(m ->
		{throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
