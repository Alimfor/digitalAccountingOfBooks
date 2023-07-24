package com.gaziyev.spring.dao;

import com.gaziyev.spring.models.Person;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("FROM Person", Person.class)
                                    .getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class,id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void update(int id,Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person existingPerson = session.get(Person.class,id);

        if (existingPerson != null){
            existingPerson.setBirthYear(person.getBirthYear());
            existingPerson.setFullName(person.getFullName());
            session.merge(existingPerson);
        }
    }

    @Transactional
    public void deleted(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class,id));
    }
}
