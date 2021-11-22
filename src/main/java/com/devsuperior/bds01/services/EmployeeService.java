package com.devsuperior.bds01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import com.devsuperior.bds01.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private DepartmentRepository depRepository;
	
	@Transactional(readOnly=true)
	public Page<EmployeeDTO> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new EmployeeDTO(x));
	}

	@Transactional
	public EmployeeDTO insert(EmployeeDTO dto) {
		Employee employee = new Employee();
		this.createEmployeeFromDTO(employee, dto);
		return new EmployeeDTO(repository.save(employee));
	}
	
	@Transactional(readOnly=true)
	private void createEmployeeFromDTO(Employee employee, EmployeeDTO dto) {
		Department department = new Department(dto.getDepartmentId(), null);
		employee.setDepartment(department);
		employee.setEmail(dto.getEmail());
		employee.setId(dto.getId());
		employee.setName(dto.getName());
	}

}
