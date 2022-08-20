package com.persona.kafka.consumer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nttdata.bootcamp.common.event.AccountEvent;
import com.persona.kafka.PersonStatusUpdateHandler;

@Configuration
public class PassiveConsumer {
	@Autowired
    private PersonStatusUpdateHandler handler;


    @Bean
    public Consumer<AccountEvent> accountPassiveConsumer(){
        //listen payment-event-topic
        //will check payment status
        //if payment status completed -> complete the order
        //if payment status failed -> cancel the order
        return (account)-> handler.updatePerson(account.getAccountPassiveDto().getPerson().getId(),po->{
            po.setAccountStatus(account.getAccountStatus());
        });
  
    }

}
