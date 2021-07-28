package project.datajpa.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
@EntityListeners(AuditingEntityListener.class)//AuditorAware 정보를 가져오기위해
@MappedSuperclass
@Getter
public class DataBaseEntity extends DataBaseTimeEntity { //이렇게 등록,수정 시간을 클래스로따로뺴서 확장하는이유는 (등록,수정은 웬만하면 모든곳에서 다쓰지만
                                                         //등록자 수정자는 쓰는곳이있고 안쓰는곳이 있을수 있기떄문

    // 등록, 수정될떄마다 AuditorAware<String> Bean을 호출해서 결과물을 꺼낸다
    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
