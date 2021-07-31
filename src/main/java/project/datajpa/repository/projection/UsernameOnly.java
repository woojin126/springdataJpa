package project.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;
//인터페이스기반
//Projections : 내가필요한 필드만 DTO로 뽑고싶을떄 사용 ,실제구현체는 스프링 Data JPA가 만들어버림
public interface UsernameOnly {
    /*
    오픈 프로젝션이라함 spl member 엔티티 필드를 다 가져옴, 그다음에 필요한거만 찝어서 가져옴
     */
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
