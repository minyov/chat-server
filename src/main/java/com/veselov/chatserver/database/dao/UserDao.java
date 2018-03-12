package com.veselov.chatserver.database.dao;

import com.veselov.chatserver.database.domain.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends AbstractDao<UserEntity> {

    @Override
    public Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    public UserEntity getByName(String name) {
        Criteria criteria = createCriteria();

        criteria.add(Restrictions.eq("name", name));
        System.out.println(name);
        return (UserEntity)criteria.uniqueResult();
    }

    public List<UserEntity> getFriends(String name) {
        Criteria criteria = createCriteria();
        System.out.println(name);
        criteria.add(Restrictions.eq("name", name));

        return ((UserEntity)criteria.uniqueResult()).getFriends();
    }
}
