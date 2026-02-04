package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFouncdException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream().map(PatientMapper::toPatientResponseDto).toList();
    }

    public PatientResponseDto createpatient(PatientRequestDto patientRequestDto) {
        if (patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("patient with this email alreay exists" + patientRequestDto.getEmail());
        }
        Patient newpatient = patientRepository.save(PatientMapper.toPatient(patientRequestDto));
        return new PatientMapper().toPatientResponseDto(newpatient);
    }

    public PatientResponseDto updatepatient(UUID id, PatientRequestDto patientRequestDto) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFouncdException("Patient does not exits for the given " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)) {
            throw new EmailAlreadyExistsException("patient with this email alreay exists" + patientRequestDto.getEmail());
        }
        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        Patient updatepatient = patientRepository.save(patient);

        return new PatientMapper().toPatientResponseDto(updatepatient);
    }

    public void deletepatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
