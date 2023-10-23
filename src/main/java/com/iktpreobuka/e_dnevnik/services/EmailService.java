package com.iktpreobuka.e_dnevnik.services;

import com.iktpreobuka.e_dnevnik.entities.EmailEntity;
import com.iktpreobuka.e_dnevnik.entities.dtos.EmailDTO;

public interface EmailService {
	public void sendSimpleMessage(EmailDTO email);
}
