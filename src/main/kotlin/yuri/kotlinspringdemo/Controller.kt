package yuri.kotlinspringdemo

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/employee")
@Api(tags = ["Employee"])
class EmployeeController {

    @Autowired
    lateinit var service: EmployeeService

    @GetMapping
    fun getAllEmployee(): Collection<Employee> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable("id") id: Long?): Employee {
        return when {
            id != null -> service.find(id) ?: throw ClientException(ErrorEnum.RESOURCE_ERROR)
            else -> throw ClientException(ErrorEnum.PARAM_ERROR)
        }
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: Employee?) {
        println("createEmployee: $employee?")
        return when {
            employee != null -> service.create(employee)
            else -> throw ClientException(ErrorEnum.PARAM_ERROR)
        }
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable("id") id: Long?, @RequestBody employee: Employee?) {
        println("updateEmployee: $employee?")
        return when {
            employee != null && id != null -> service.uptate(id, employee)
            else -> throw ClientException(ErrorEnum.PARAM_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable("id") id: Long?) {
        return when {
            id != null -> service.delete(id)
            else -> throw ClientException(ErrorEnum.PARAM_ERROR)
        }
    }

}