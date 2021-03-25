package ey.prac;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class FirstLevelCache {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello-jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try {
            //비영속성
            Member member = new Member();
            member.setId(100L);
            member.setName("Hello JPA");

            //영속
            System.out.println("=== BEFORE ===");
            entityManager.persist(member);
            System.out.println("=== AFTER ===");

            Member findMember = entityManager.find(Member.class, 100L);
            Member findMember2 = entityManager.find(Member.class, 100L);

            System.out.println(findMember == findMember2);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transaction.rollback();
        }
    }
}
