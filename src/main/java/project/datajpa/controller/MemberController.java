package project.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.datajpa.dto.MemberDto;
import project.datajpa.entity.Member;
import project.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;


    /**
     *컨트롤러에서 파라미터들이 바인딩될때 Pageable이있으면 PageRequest 생성해서
     * 값을채워서 주입해줌
     * 포스트맨
     * http://localhost:8080/members?page=0&size=3&sort=id,desc&sort=username,desc

     한페이지당 개수 를 글로벌설정으로도 가능,@PageableDefault로도 가능
     */
    @GetMapping("/members")
    public Page<MemberDto> list(
            @PageableDefault(size = 5, sort = "username")
                    Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);

        return page.map(MemberDto::new);

    }

    @PostConstruct
    public void init(){
        for(int i=0; i<100; i++){
            memberRepository.save(new Member("user"+i, i));
        }
    }


    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id")Long id){
        Member member = memberRepository.findById(id).get();

        return member.getUsername();
    }

    /*
      권장하지는 않는다. 그냥딲 조회용으로만 사용함 트랜잭션안에서 돌아가는게아님
     */
    @GetMapping("/members2/{id}")//컨버팅과정을 끝내고 그냥바로 member에 파라미터결과를 넣어줌
    public String findMember2(@PathVariable("id") Member member){

        return member.getUsername();
    }

/*    @PostConstruct
    public void init(){
        memberRepository.save(new Member("userA"));
    }*/

}
