package project.datajpa.repository;

//Projections : 내가필요한 필드만 DTO로 뽑고싶을떄 사용
public interface UsernameOnly {
    String getUsername();
}
