# Español
___
Este proyecto está dedicado al manejo de excepciones de forma global en APIs Restfull utilizando spring boot y sprind data.

## Instrucciones de uso

Importa la dependencia en tu archivo pom.xml

```xml
<dependency>
    <groupId>com.cursosapi.common</groupId>
    <artifactId>global-exception-handler-test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Crea una clase de configuración utilizando ***@Configuration***.
Configura el escaneo de componentes sobre el paquete donde se encuentra el/los rest controllers advice, utiliza la anotación ***@ComponentScan***

```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.cursosapi.common.globalexceptionhandlertest.webadvice")
public class InitGlobalExceptionHandler {

}
```

Importa tu clase de configuración en la clase principal de tu proyecto utilizando ***@Import***
```java
package com.example.tuapp;

import com.cursoapis.catalogoproductostest.config.InitGlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan

@Import(InitGlobalExceptionHandler.class)
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoProductosTestApplication.class, args);
	}

}
```

## Paquetes principales
1. exception: incluye todas las excepciones que puedes lanzar en tu aplicación y que el framework de control de excepciones en api rest manejará 
2. supplier: aquí se encuentran algunos **java.util.function.Supplier<T>** para obtener un proveedor que envuelva excepciones que existen dentro del paquete de exception
3. dto: contiene el DTO **ApiError.java**. Este es el DTO principal que se retornará envuelto en un **ResponseEntity** durante el manejo de una excepción


## Excepciones que se manejan
### Proveídas por Spring
* org.springframework.beans.ConversionNotSupportedException
* org.springframework.beans.TypeMismatchException
* org.springframework.http.converter.HttpMessageNotReadableException
* org.springframework.http.converter.HttpMessageNotWritableException
* org.springframework.validation.BindException
* org.springframework.web.ErrorResponseException
* org.springframework.web.HttpMediaTypeNotAcceptableException
* org.springframework.web.HttpMediaTypeNotSupportedException
* org.springframework.web.HttpRequestMethodNotSupportedException
* org.springframework.web.bind.MethodArgumentNotValidException
* org.springframework.web.bind.MissingPathVariableException
* org.springframework.web.bind.MissingServletRequestParameterException
* org.springframework.web.bind.ServletRequestBindingException
* org.springframework.web.bind.annotation.Exception
* org.springframework.web.context.request.async.AsyncRequestTimeoutException
* org.springframework.web.multipart.support.MissingServletRequestPartException
* org.springframework.web.servlet.NoHandlerFoundException

### Proveídas por el proyecto
* com.cursosapi.common.globalexceptionhandlertest.exception.EntityNotFoundException

# English
___
This project is dedicated to handling exceptions globally in Restful APIs using Spring Boot and Spring Data.

## Usage instructions:

Import the dependency in your pom.xml file.

```xml
<dependency>
    <groupId>com.cursosapi.common</groupId>
    <artifactId>global-exception-handler-test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Create a configuration class using **@Configuration***.
Configure the component scanning on the package where the REST controller advice is located, using the annotation ***@ComponentScan***.


```java
package com.example.yourapp.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.cursosapi.common.globalexceptionhandlertest.webadvice")
public class InitGlobalExceptionHandler {

}
```

Import your configuration class in the main class of your project using @Import.

```java
package com.example.yourapp.config;

import com.cursoapis.catalogoproductostest.config.InitGlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan

@Import(InitGlobalExceptionHandler.class)
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoProductosTestApplication.class, args);
	}

}
```

## Main packages
1. **exception**: includes all the exceptions that can be thrown in your application and will be handled by the REST API exception handling framework.
2. **supplier**: this package contains some **java.util.function.Supplier<T>** to provide a supplier that wraps exceptions within the exception package.
3. **dto**: contains the **ApiError.java** DTO. This is the main DTO that will be returned wrapped in a **ResponseEntity** during exception handling.

## Exceptions being handled
### Provided by Spring
* org.springframework.beans.ConversionNotSupportedException
* org.springframework.beans.TypeMismatchException
* org.springframework.http.converter.HttpMessageNotReadableException
* org.springframework.http.converter.HttpMessageNotWritableException
* org.springframework.validation.BindException
* org.springframework.web.ErrorResponseException
* org.springframework.web.HttpMediaTypeNotAcceptableException
* org.springframework.web.HttpMediaTypeNotSupportedException
* org.springframework.web.HttpRequestMethodNotSupportedException
* org.springframework.web.bind.MethodArgumentNotValidException
* org.springframework.web.bind.MissingPathVariableException
* org.springframework.web.bind.MissingServletRequestParameterException
* org.springframework.web.bind.ServletRequestBindingException
* org.springframework.web.bind.annotation.Exception
* org.springframework.web.context.request.async.AsyncRequestTimeoutException
* org.springframework.web.multipart.support.MissingServletRequestPartException
* org.springframework.web.servlet.NoHandlerFoundException

### Exceptions provided by the project
* com.cursosapi.common.globalexceptionhandlertest.exception.EntityNotFoundException