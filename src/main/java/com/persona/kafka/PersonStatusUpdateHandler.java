package com.persona.kafka;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.bootcamp.common.dto.ClientDto;
import com.nttdata.bootcamp.common.dto.DocumentsDto;
import com.nttdata.bootcamp.common.dto.PersonDto;
import com.nttdata.bootcamp.common.event.AccountStatus;
import com.nttdata.bootcamp.common.event.PersonStatus;
import com.persona.dao.PersonDao;
import com.persona.model.Person;
import com.persona.service.impl.PersonStatusPublisher;

@Configuration
public class PersonStatusUpdateHandler {
	
//	 @Autowired
//	 private IPersonDao repository;
	
	@Autowired
	private PersonDao repository;
	 
	 @Autowired
	 private PersonStatusPublisher publisher;

	@Transactional
	public void updatePerson(String id, Consumer<Person>  consumer) {
		(repository.findById(id)).doOnSuccess((consumer.andThen(this::updateOrder)));
//		Mono<Person> monoPerson =  repository.findById(id);
//		monoPerson.doOnSuccess(consumer.andThen(this::updateOrder));
//		
//		consumer.andThen(this::updateOrder);
//		Person personModel = mapper.map( , Person.class);
//		Mono<Person> mono = Mono.just(personModel);
//		monoPerson.zipWith(mono,(bd,ps) ->{
//			bd.setAccount(ps.get)
//		});		
	}

	private void updateOrder(Person  client) {
		boolean isPaymentComplete = AccountStatus.CREATED.equals(client.getAccountStatus());
        PersonStatus personStatus = isPaymentComplete ? PersonStatus.CREATED : PersonStatus.CANCELLED;
        client.setPersonStatus(personStatus);
        if (!isPaymentComplete) {
            publisher.publishPersonEvent(convertEntityToDto(client), personStatus);;
        }
	}

	private PersonDto convertEntityToDto(Person  clients) {
		PersonDto personDto = new PersonDto();
		personDto.setId(clients.getId());
		/*Inicio Seteamos los valores al Dto Client*/
		ClientDto clientDto =  new ClientDto();
		clientDto.setBusinessName(clients.getClient().getBusinessName());
		clientDto.setNames(clients.getClient().getNames());
		clientDto.setSurnames(clients.getClient().getSurnames());
		/*Fin de la operacion Dto Client*/
		personDto.setClient(clientDto);
		personDto.setAccount(clients.getAccount());
		personDto.setTypeAccount(clients.getTypeAccount());
		personDto.setTypePerson(clients.getTypePerson());
		/*Inicio Seteamos los valores al Dto Documents*/
		DocumentsDto documentsDTO =  new DocumentsDto();
		documentsDTO.setDocumentType(clients.getDocuments().getDocumentType());
		documentsDTO.setNroDocument(clients.getDocuments().getNroDocument());
		/*Fin de la operacion Dto Docuements*/
		personDto.setDocuments(documentsDTO);
		
        return personDto;
	}



}
