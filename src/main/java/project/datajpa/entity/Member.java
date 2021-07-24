package project.datajpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    protected Member(){//아무대서나 호출되지않도록, 엔티티는 기본적으로 기본생성자가 필요
        
    }

    public Member(String username) {
        this.username = username;
    }

    //세터대신 그냥
    public void changeUsername(String username){
        this.username=username;
    }
}
