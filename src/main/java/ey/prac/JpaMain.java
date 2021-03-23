package ey.prac;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello-jpa");
    Long id = 2L;

    // 요청마다 entity manager를 생성한다.
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    // JPA 의 모든 변경은 트랜젝션 안에서 일어나야 한다.
    transaction.begin();
    try {
      Member member = new Member();
      member.setId(id);
      member.setName("A1");
      //insert
      entityManager.persist(member);

      transaction.commit();

    } catch (Exception e) {
      transaction.rollback();
    } finally {
      entityManager.close();
    }


    entityManager = emf.createEntityManager();
    transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      //select
      Member findMember = entityManager.find(Member.class, id);
      System.out.println("find member id : " + findMember.getId());
      System.out.println("find member name" + findMember.getName());

      transaction.commit();

    } catch (Exception e) {
      transaction.rollback();
    } finally {
      entityManager.close();
    }


    entityManager = emf.createEntityManager();
    transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      Member findMember = entityManager.find(Member.class, id);
      //update
      findMember.setName("B1");
      transaction.commit();

    } catch (Exception e) {
      transaction.rollback();
    } finally {
      entityManager.close();
    }

    entityManager = emf.createEntityManager();
    transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      //delete
      entityManager.remove(2L);

      transaction.commit();

    } catch (Exception e) {
      transaction.rollback();
    } finally {
      entityManager.close();
    }

    entityManager = emf.createEntityManager();
    transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      //JPQL
      //entity 객체를 대상으로 쿼리
      List<Member> memberList = entityManager.createQuery("select m from Member as m", Member.class).getResultList();
      for(Member member : memberList) {
        System.out.println("loop) find member id : " + member.getId());
        System.out.println("loop) find member name : " + member.getName());
      }
      transaction.commit();

    } catch (Exception e) {
      transaction.rollback();
    } finally {
      entityManager.close();
    }

    emf.close();
  }
}
