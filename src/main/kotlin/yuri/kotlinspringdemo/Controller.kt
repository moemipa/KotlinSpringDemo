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
    fun getAllEmployee(): Result<Collection<Employee>> {
        return Result(service.findAll())
    }

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable("id") id: Long?): Result<Employee> {
        return when {
            id != null -> Result(service.find(id) ?: throw CustomException(ErrorEnum.RESOURCE_ERROR))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: Employee?): Result<*> {
        println("createEmployee: $employee?")
        return when {
            employee != null ->  Result(service.create(employee))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable("id") id: Long?, @RequestBody employee: Employee?): Result<*> {
        println("updateEmployee: $employee?")
        return when {
            employee != null && id != null ->  Result(service.uptate(id, employee))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable("id") id: Long?): Result<*> {
        return when {
            id != null ->  Result(service.delete(id))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

}

