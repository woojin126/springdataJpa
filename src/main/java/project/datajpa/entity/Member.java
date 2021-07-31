package project.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})//팀을(연관관계필드) 출력하면 큰일남 무한루프일어남
@NamedQuery( //잘안씀 ,진짜 좋은장점하나 애플리케이션 로딩시점에 아래 쿼리를 파싱해서 오류가있으면 알려줌
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
public class Member extends DataBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

   /* protected Member(){//아무대서나 호출되지않도록, 엔티티는 기본적으로 기본생성자가 필요
        
    }*/

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null){
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    //세터대신 그냥
    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
