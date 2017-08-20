package com.minyov.chatserver.database.dao;

import com.minyov.chatserver.database.domain.Message;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MessageDao extends AbstractDao<Message> {
    @Override
    public Class<Message> getEntityClass() {
        return Message.class;
    }

    public void saveMessage(Message message, boolean isRead) {
        message.setRead(isRead);
        message.setDate(new Date());

        persist(message);
    }

    public List<Message> getMessages(String sender, String receiver) {
        Criteria criteria = getHibernateSession().createCriteria(getEntityClass(), "m")
                .createAlias("m.sender", "s")
                .createAlias("m.receiver", "r");


        criteria.add(Restrictions.or(
                Restrictions.and(
                        Restrictions.eq("s.name", sender),
                        Restrictions.eq("r.name", receiver)),
                Restrictions.and(
                        Restrictions.eq("r.name", sender),
                        Restrictions.eq("s.name", receiver))));
        criteria.addOrder(Order.asc("date"));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }
}
