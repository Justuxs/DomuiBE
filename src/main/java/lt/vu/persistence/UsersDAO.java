package lt.vu.persistence;

import lt.vu.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class UsersDAO {
    @Inject
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(User user){
        this.em.persist(user);
    }

    public User findOne(String email) {
        return em.find(User.class, email);
    }

    public void flush(){
        this.em.flush();
    }

    public User update(User user){
        return em.merge(user);
    }

    public void refresh(User user){
        em.refresh(user);
    }


}
