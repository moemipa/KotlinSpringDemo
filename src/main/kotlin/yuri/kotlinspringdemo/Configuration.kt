package yuri.kotlinspringdemo

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.text.SimpleDateFormat
import java.util.*

fun <T> ZSLog(msg: T, tag: String? = null) {
    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    val info: String = when (msg) {
        is Exception -> "${msg.stackTrace[0]}"
        else -> "${Throwable().stackTrace[2]} $msg"
    }
    println("${tag ?: date}  $info")
}

@Configuration
@EnableSwagger2
@ComponentScan("yuri.kotlinspringdemo")
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("REST API")
                .version("1.0")
                .build()
    }
}