package yuri.kotlinspringdemo

import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

class Result<T>(val data: T) {
    val code: Int = 0
    val message: String = "成功"
}

@Entity
class Employee {

    @Id
    @GeneratedValue
    var id: Long = 0

    @Column
    var name: String = ""

    @Column
    var gender: Int = 0

    @ManyToOne(cascade = [CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE])
    var department: Department = Department()

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var birthDate: Date = Date()

}

@Entity
class Department {

    @Id
    @GeneratedValue
    var id: Long = 0

    @Column(unique = true)
    var departmentName: String = ""

}

