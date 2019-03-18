package yuri.kotlinspringdemo

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/employee")
@Api(tags = ["Employee"])
class EmployeeController {

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var departmentService: DepartmentService

    @GetMapping
    fun getAllEmployee(
            @RequestParam(required = false, defaultValue = "0") page: Int,
            @RequestParam(required = false, defaultValue = "10") size: Int
    ): Result<PageResult<Employee>> {
        return Result(PageResult(employeeService.findAll(page, size)))
    }

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable id: Long): Result<Employee> {
        return Result(employeeService.find(id) ?: throw CustomException(ErrorEnum.RESOURCE_ERROR))
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: Employee?): Result<*> {
        return when {
            employee != null -> {
                employee.department?.id
                        ?.let { departmentService.find(it) }
                        ?.let { employee.department = it }
                Result(employeeService.create(employee))
            }
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @PutMapping
    fun updateEmployee(@RequestBody employee: Employee?): Result<*> {
        return when {
            employee != null -> {
                employee.department?.id
                        ?.let { departmentService.find(it) }
                        ?.let { employee.department = it }
                Result(employeeService.update(employee))
            }
            else -> throw CustomException(ErrorEnum.PARAM_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Long): Result<*> {
        return Result(employeeService.delete(id))
    }

}

