package yuri.kotlinspringdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
    fun delete(id: Long) {
        repository.deleteById(id)
    }

    @Transactional
    fun update(id: Long, employee: Employee) {
        println("update: $id $employee")
        if (repository.existsById(id)) {
            employee.id = id
            repository.save(employee)
        }
    }

    @Transactional
    fun find(id: Long): Employee? {
        return repository.findById(id).orElse(null)
    }

    @Transactional
    fun findAll(pageable: Pageable): Page<Employee> {
        println("findAll: $pageable")
        return repository.findAll(pageable)
    }

}

@Service
class DepartmentService {

    @Autowired
    lateinit var repository: DepartmentRepository

    @Transactional
    fun find(id: Long): Department? {
        return repository.findById(id).orElse(null)
    }

    @Transactional
    fun findAll(): Collection<Department> = repository.findAll()
}