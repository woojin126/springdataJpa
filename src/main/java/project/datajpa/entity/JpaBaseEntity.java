package project.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass//속성들을 테이블에서 같이 사용할수있게하는것,(데이터만 공유)
public class JpaBaseEntity {

    @Column(updatable = false)//생성일자는 변경이안됨
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist//영속상태로 저장 되기전에 무언가를 한다
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        this.createDate=now;
        this.updateDate=now;
    }
    @PreUpdate //업데이트 되기전에 호출
    public void preUpdate(){
        updateDate = LocalDateTime.now();
    }
}
