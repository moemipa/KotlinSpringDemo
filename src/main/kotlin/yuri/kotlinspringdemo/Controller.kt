package yuri.kotlinspringdemo

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/employee")
@Api(tags = ["Employee"])
class EmployeeController {

    @Autowired
    lateinit var service: EmployeeService

    @PostMapping
    fun createEmployee(@RequestBody employee: Employee?): Result<*> {
        println("createEmployee: $employee?")
        return when {
            employee != null ->  Result(service.create(employee))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Long?): Result<*> {
        return when {
            id != null ->  Result(service.delete(id))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable id: Long?, @RequestBody employee: Employee?): Result<*> {
        println("updateEmployee: $employee?")
        return when {
            employee != null && id != null ->  Result(service.update(id, employee))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable id: Long?): Result<Employee> {
        return when {
            id != null -> Result(service.find(id) ?: throw CustomException(ErrorEnum.RESOURCE_ERROR))
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @GetMapping
    fun getAllEmployee(
            @RequestParam(required = false) page: Int?,
            @RequestParam(required = false) size: Int?
    ): Result<PageResult<Employee>> {
        val pageable: Pageable = PageRequest.of(page ?: 0, size ?: 10)
        val pageResult: Page<Employee> = service.findAll(pageable)
        return Result(PageResult(pageResult, page ?: 0))
    }

}

