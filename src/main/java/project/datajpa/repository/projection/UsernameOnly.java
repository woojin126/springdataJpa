package project.datajpa.repository.projection;

import org.springframework.beans.factory.annotation.Value;
//인터페이스기반
//Projections : 내가필요한 필드만 DTO로 뽑고싶을떄 사용 ,실제구현체는 스프링 Data JPA가 만들어버림
public interface UsernameOnly {
    //@Value가 없으면 Close 프로젝션 == 정확히 필드만 매칭되서 가져오는것
    //오픈 프로젝션이라함 == spl member 엔티티 필드를 다 가져옴, 그다음에 필요한거만 찝어서 가져옴
    @Value( "#{target.username + ' ' + target.age}" )
    String getUsername();



    //오픈 프로젝션 사용시 결과값 모든 필드를 다긁어온다음 애플리케이션단에서는 이름과, 나이만 사용
    /*
        select
        member0_.member_id as member_id1_1_,
        member0_.create_date as create_date2_1_,
        member0_.last_modified_date as last_modified_date3_1_,
        member0_.create_by as create_by4_1_,
        member0_.last_modified_by as last_modified_by5_1_,
        member0_.age as age6_1_,
        member0_.team_id as team_id8_1_,
        member0_.username as username7_1_
    from
        member member0_
    where
        member0_.username=?
     */
}
