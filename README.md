# GestorVentaParaBazarMicroservicio

Este proyecto es el trabajo final de un curso de desarrollo de APIs con Spring Boot.

El objetivo principal es gestionar las ventas de productos a clientes. Para ello, se han implementado los siguientes endpoints:

* CRUD de Clientes.
* CRUD de Productos.
* CRUD de Ventas.
* Obtención de productos cuyo stock actual sea menor a 5.
* Recopilación de la lista de productos de una venta específica.
* Solicitud del monto recaudado en una fecha específica.
* Búsqueda de la mayor venta histórica.

Este trabajo se desarrolló utilizando Java con el framework Spring, específicamente Spring Boot. La conexión a la base de datos se realizó mediante JPA + Hibernate, y se siguió el protocolo HTTP para crear APIs REST. El diseño se basó en la arquitectura MVC desde la perspectiva del backend.

Agregado: En comparación con el proyecto anterior, se ha migrado de una arquitectura monolítica a una arquitectura de microservicios. Se han aplicado estructuras de software y algunos de los protocolos más conocidos, como:

*Service Registry y Service Discovery
*Load Balancing
*Circuit Breaker
*API Gateway
*Config Server

Además, el proyecto se ha desarrollado y administrado dentro de contenedores de Docker.
ACLARACIONES:
* Dentro del docker-compose.yml se utilizan credenciales que no deseo que se hagan publicas, asi que utilice un archivo .env para usar variables de entorno de docker y asi mantener segura dichas credenciales.
