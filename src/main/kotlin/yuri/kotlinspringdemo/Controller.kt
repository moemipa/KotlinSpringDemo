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
        return Result(HttpStatusEnum.OK, service.findAll())
    }

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable("id") id: Long?): Result<Employee?> {
        return Result(HttpStatusEnum.OK, id?.let { service.find(it) })
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: Employee?): Result<Nothing?> {
        println("createEmployee: $employee?")
        return when {
            employee != null -> {
                service.create(employee)
                Result(HttpStatusEnum.OK, null)
            }
            else -> {
                println("employee is null")
                Result(HttpStatusEnum.UNSUPPORTED_MEDIA_TYPE, null)
            }
        }
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable("id") id: Long?, @RequestBody employee: Employee?): Result<Nothing?> {
        println("updateEmployee: $employee?")
        return when {
            employee != null && id != null -> {
                service.uptate(id, employee)
                Result(HttpStatusEnum.OK, null)
            }
            else -> {
                println("id or employee is null")
                Result(HttpStatusEnum.UNSUPPORTED_MEDIA_TYPE, null)
            }
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable("id") id: Long?): Result<Nothing?> {
        return when {
            id != null -> {
                service.delete(id)
                Result(HttpStatusEnum.OK, null)
            }
            else -> Result(HttpStatusEnum.UNSUPPORTED_MEDIA_TYPE, null)
        }
    }

}