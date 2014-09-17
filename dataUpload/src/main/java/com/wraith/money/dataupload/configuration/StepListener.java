package com.wraith.money.dataupload.configuration;


import com.wraith.money.data.Transaction;
import com.wraith.money.dataupload.processor.MoneyTransaction;
import org.springframework.batch.core.ItemProcessListener;

/**
 * User: rowan.massey
 * Date: 12/09/2014
 * Time: 00:20
 */
public class StepListener implements ItemProcessListener<MoneyTransaction, Transaction> {

    @Override
    public void beforeProcess(MoneyTransaction moneyTransaction) {
        //TODO - check if the description exists in the payee mapping table, and replace with a new payee if so.
    }

    @Override
    public void afterProcess(MoneyTransaction moneyTransaction, Transaction transaction) {

    }

    @Override
    public void onProcessError(MoneyTransaction moneyTransaction, Exception e) {

    }
}
