package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.dto.validators.CreateValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient" ,description = "API for managing Patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {

        List<PatientResponseDto> patientList = patientService.getPatients();
        return ResponseEntity.ok().body(patientList);
    }

    @PostMapping
    @Operation(summary = "Create new patients")
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class, CreateValidationGroup.class}) @RequestBody PatientRequestDto patientRequestDto) {
        PatientResponseDto patient = patientService.createpatient(patientRequestDto);
        return ResponseEntity.ok().body(patient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patients")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto) {
        patientService.updatepatient(id, patientRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patients")
    public ResponseEntity<PatientResponseDto> deletePatient(@PathVariable UUID id) {
        patientService.deletepatient(id);
        return ResponseEntity.ok().build();
    }
}
