# Sistema de gestión de bazar

El sistema de gestión de ventas para un bazar es una aplicación web diseñada para poder administrar en un bazar de forma sencilla su registro de productos, clientes y también de sus ventas. Desarrollado con tecnologías modernas como Spring Boot, Spring Cloud y siguiendo una arquitectura de software de microservicios.

# Tecnologías Utilizadas

El sistema se basa en las siguientes tecnologías:
- Spring Boot: Un marco de desarrollo de aplicaciones Java que simplifica la creación de aplicaciones Spring.
- Spring Cloud: Conjunto de herramientas y librerías de código abierto desarrolladas para facilitar la creación y el despliegue de aplicaciones en la nube con Java.
- Hibernate: Un marco ORM (Mapeo Objeto-Relacional) que simplifica el acceso a la base de datos relacional.
- MySQL: Un sistema de gestión de bases de datos relacional de código abierto.
- Maven: Una herramienta de gestión de proyectos y construcción de software.
- Docker: Tecnología que te permite armar, administrar y desplegar aplicaciones de manera mucho más fácil mediante el uso de contenedores.

# Patrones Aplicados

- MVC (Modelo Vista Controlador): Busca separar la lógica de negocio y la vista que se le presenta al usuario, utilizando como intermediario un controlador.
- DTO (Data Transfer Object): Busca crear un objeto plano con atributos necesarios y simplificados para facilitar el envío de datos.
- API Gateway: Permite utilizar una puerta de enlace para facilitar la comunicacion entre los clientes externos y la aplicación.
- Config Server: Permite crear un servicio de configuracion centralizada para que cada servicio acceda a su configuracion. 
- Service Registry: Actua como una fuente centralizada de información, permitiendo que los microservicios se registren y actualicen dinámicamente su ubicación y otros metadatos relevantes.
- Service Discovery: Permite a los microservicios descubrir y comunicarse entre sí de forma dinámica. 
- Circuit Breaker: Permite controlar y manejar las posibles fallas que pudieran existir en la comunicación entre los microservicios.
- Load Balancer: Permite administrar numerosas peticiones entre múltiples instancias de un microservicio.
  
# Estructura del Proyecto

El proyecto sigue una arquitectura de microservicio que divide una aplicación en servicios más pequeños y autónomos que se ejecutan en procesos separados. Cada microservicio tiene su propio modelado, su propia lógica de negocio, su propia base de datos y se comunica con otros microservicios a través de APIs. Cada servicio a su vez estará diseñado siguiendo la siguiente arquitectura de capas:
- Controller: La capa encargada de atender las solicitudes HTTP y derivarlas a donde corresponda.
- Service: La capa en la cual estará toda la lógica de negocio de cada servicio.
- Repository: La capa encargada de la persistencia de los datos.
- Model: La capa en la cual estará el modelado de las clases.
- DTO: La capa en la cual estarán las clases del patrón DTO.
- Exception: La capa en la cual estarán modeladas las excepciones personalizadas.

# Funcionalidades

- CRUD de Clientes.
- CRUD de Productos.
- CRUD de Ventas.
- No hay CRUD de Detalle de Venta, ya que es manejado por el servicio Ventas.
- Obtención de productos cuyo stock actual sea menor a 5.
- Recopilación de la lista de productos de una venta específica.
- Solicitud del monto recaudado en una fecha específica.
- Búsqueda de la mayor venta histórica.
