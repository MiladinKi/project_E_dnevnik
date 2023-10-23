package com.iktpreobuka.e_dnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.e_dnevnik.entities.EmailEntity;
import com.iktpreobuka.e_dnevnik.entities.MarkEntity;
import com.iktpreobuka.e_dnevnik.entities.ParentEntity;
import com.iktpreobuka.e_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.e_dnevnik.entities.dtos.EmailDTO;
import com.iktpreobuka.e_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.e_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.e_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.e_dnevnik.services.EmailService;


@RestController
@RequestMapping(path = "/api/v1/emails")
public class EmailController {

	@Autowired
	public EmailService emailService;

	@Autowired
	public ParentRepository parentRepository;

	@Autowired
	public SubjectRepository subjectRepository;

	@Autowired
	public MarkRepository markRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/{parentId}/subject/{subjectId}/mark/{markId}")
	public String sendSimpleMessage(@PathVariable Integer parentId,
			@PathVariable Integer subjectId, @PathVariable Integer markId) {

		ParentEntity parent = parentRepository.findById(parentId).orElse(null);
		MarkEntity mark = markRepository.findById(markId).orElse(null);
		SubjectEntity subjectInfo = subjectRepository.findById(subjectId).orElse(null);
		if (parent != null) {
			String subject = "Obaveštenje o oceni";
			String text = "Vašem detetu je dodeljena nova ocena iz predmeta " + subjectInfo.getName() + ". Ocena je " + mark.getMark()
					+ ".";
			EmailDTO email = new EmailDTO(parent.getEmail(), subject, text);

			emailService.sendSimpleMessage(email);
			return "Your mail has been sent!";
		}
		return null;
	}
}
