package com.minyov.chatserver.database.dao;

import com.minyov.chatserver.database.domain.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao<UserEntity> {

    @Override
    public Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }


}
