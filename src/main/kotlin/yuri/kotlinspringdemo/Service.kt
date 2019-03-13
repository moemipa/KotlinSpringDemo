package yuri.kotlinspringdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeService {

    @Autowired
    lateinit var repository: EmployeeRepository

    @Transactional
    fun create(employee: Employee) {
        println("create: $employee")
        repository.save(employee)
    }

    @Transactional
    fun uptate(id: Long, employee: Employee) {
        println("uptate: $id $employee")
        if (repository.existsById(id)) {
            employee.id = id
            repository.save(employee)
        }
    }

    @Transactional
    fun findAll(): Collection<Employee> {
        return repository.findAll()
    }

    @Transactional
    fun find(id: Long): Employee? {
        return repository.findById(id).orElse(null)
    }

    @Transactional
    fun delete(id: Long) {
        repository.deleteById(id)
    }
}

@Service
class DepartmentService {

    @Autowired
    lateinit var repository: DepartmentRepository

    @Transactional
    fun findAll(): Collection<Department> = repository.findAll()

    @Transactional
    fun find(id: Long): Department? {
        return repository.findById(id).orElse(null)
    }
}