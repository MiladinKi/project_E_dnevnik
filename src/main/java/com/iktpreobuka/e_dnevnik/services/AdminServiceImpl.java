package com.iktpreobuka.e_dnevnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_dnevnik.entities.AdminEntity;
import com.iktpreobuka.e_dnevnik.repositories.AdminRepository;


@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	public AdminRepository adminRepository;
	
	

}
