package project.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import project.datajpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;


    /*
        이렇게내가 id값을 직접 생성해주면 save할때 이미 값이 있는걸로 알아서 PERSIST 호출이안됨
        save 내부구조는 값이 만약에 NULL 이면 영속화 시키고 @GenerateValue가 동작해서 아이디값이 반환되는데
        내가 직접 아이디값을 생성자에 주입하면 아래 isNew == false 가되고 else 구문을 타게된다
        Merge는 이미 DB에 A가 있는지 찾는다 (그래서 ITEM에서 ID를찾는 SELECT문이 한방더 나가버린다)
        SAVE 내부구조
        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
 */

    @Test
    @Rollback(value = false)
    public void save(){
        Item item = new Item("A");
        itemRepository.save(item);
    }

}