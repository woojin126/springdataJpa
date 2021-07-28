package project.datajpa.dto;

import lombok.Data;
import project.datajpa.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    //엔티티는 dto를 보면안되. dto들은 엔티티를 봐도됨 //필드에쓰는건안된다그래도 엔티티를
    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
