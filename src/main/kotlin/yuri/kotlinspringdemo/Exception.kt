package yuri.kotlinspringdemo

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.RuntimeException
import javax.servlet.http.HttpServletRequest
import org.springframework.web.context.request.WebRequest

enum class ErrorEnum (val code: Int, val msg: String) {
    UNKONW_ERROR(999, "未知错误"),
    PARAM_ERROR(101, "参数错误"),
    RESOURCE_ERROR(102, "资源不存在"),
}

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleException(request: HttpServletRequest, exception: CustomException): String {
        exception.printStackTrace()
        val status = when (exception) {
            is ClientException -> 400
            is ServerException -> 500
            else -> 999
        }
        request.setAttribute("javax.servlet.error.status_code", status)
        request.setAttribute("custom", exception)
        return "forward:/error";
    }
}

@Component
class MyErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(request: WebRequest, includeStackTrace: Boolean): Map<String, Any> {
        val exception: CustomException? = request.getAttribute("custom", 0) as? CustomException
        val map = super.getErrorAttributes(request, includeStackTrace)
        map["code"] = exception?.code ?: 999
        return map
    }
}

class ServerException(errorEnum: ErrorEnum): CustomException(errorEnum)
class ClientException(errorEnum: ErrorEnum): CustomException(errorEnum)
abstract class CustomException(errorEnum: ErrorEnum) : RuntimeException(errorEnum.msg) {
    var code: Int = errorEnum.code
}


