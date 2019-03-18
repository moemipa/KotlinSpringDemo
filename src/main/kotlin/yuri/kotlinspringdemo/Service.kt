package yuri.kotlinspringdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeService {

    @Autowired
    lateinit var repository: EmployeeRepository

    @Transactional
    fun findAll(page: Int, size: Int): Page<Employee> {
        ZSLog("findAll(page: $page, size: $size)")
        val pageable: Pageable = PageRequest.of(page, size)
        return repository.findAll(pageable)
    }

    @Transactional
    fun find(id: Long): Employee? {
        return repository.findById(id).orElse(null)
    }

    @Transactional
    fun create(employee: Employee) {
        ZSLog("create(employee: $employee)")
        when {
            !repository.existsById(employee.id) -> repository.save(employee)
            else -> throw CustomException(ErrorEnum.ALREADY_EXISTS_ERROR)
        }
    }

    @Transactional
    fun update(employee: Employee) {
        ZSLog("update(employee: $employee)")
        when {
            repository.existsById(employee.id) -> repository.save(employee)
            else -> throw CustomException(ErrorEnum.RESOURCE_ERROR)
        }
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
    fun findAll(): Collection<Department> {
        return repository.findAll()
    }

    @Transactional
    fun find(id: Long): Department? {
        return repository.findById(id).orElse(null)
    }

    @Transactional
    fun create(department: Department) {
        ZSLog("create(department: $department)")
        when {
            !repository.existsById(department.id) -> repository.save(department)
            else -> throw CustomException(ErrorEnum.ALREADY_EXISTS_ERROR)
        }
    }

    @Transactional
    fun uptdate(department: Department) {
        ZSLog("update(department: $department)")
        when {
            repository.existsById(department.id) -> repository.save(department)
            else -> throw CustomException(ErrorEnum.RESOURCE_ERROR)
        }
    }

    @Transactional
    fun delete(id: Long) {
        repository.deleteById(id)
    }

}