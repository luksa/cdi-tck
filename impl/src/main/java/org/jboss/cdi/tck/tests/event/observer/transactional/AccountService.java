package org.jboss.cdi.tck.tests.event.observer.transactional;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;

@Named
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AccountService {

    @Resource
    private UserTransaction userTransaction;

    @Inject
    Event<Withdrawal> event;

    /**
     * 
     * @param amount
     * @throws Exception
     */
    public long withdrawSuccesTransaction(int amount) throws Exception {

        userTransaction.begin();
        event.fire(new Withdrawal(amount));
        long checkpoint = now();
        userTransaction.commit();
        return checkpoint;
    }

    /**
     * 
     * @param amount
     * @throws Exception
     */
    public long withdrawFailedTransaction(int amount) throws Exception {

        userTransaction.begin();
        event.fire(new Withdrawal(amount));
        long checkpoint = now();
        // Failed for any reason
        userTransaction.rollback();
        return checkpoint;
    }

    /**
     * 
     * @param amount
     * @throws Exception
     */
    public long withdrawNoTransaction(int amount) throws Exception {

        event.fire(new Withdrawal(amount));
        return now();
    }

    private long now() {
        return System.currentTimeMillis();
    }

}
