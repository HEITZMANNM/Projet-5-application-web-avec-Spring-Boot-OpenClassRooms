package com.projet5.api.service;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;


@Data
@Service
public class MedicalRecordsService {

    private static final Logger logger = LogManager.getLogger("Medical Records service");

}
