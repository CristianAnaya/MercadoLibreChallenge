# Desafío técnico — Mercado Libre
Versión simplificada de la aplicación móvil de Mercado Libre para Android desarrollada como reto técnico para aplicar al cargo de desarrollador móvil.

## Descripción general
La aplicación permite buscar productos dado el nombre de un producto usando la API pública de Mercado Libre, así como mostrar un detalle de cada producto.

## - Funcionalidades
* Busqueda de productos
* Mostrar los detalles de un producto seleccionado
* Guardado de busquedas recientes

## Arquitectura
La aplicación se desarrolló con clean architecture, tambien siguiendo el patron MVVM (*model-view-view model*) y se distribuye en tres módulos de la siguiente manera:

### domain
Definición de alto nivel de los repositorios, los casos de uso de la aplicacion y de los objetos modelo a usar. Estas definiciones son independientes de la fuente de datos.

### data
Implementación de los repositorios de acuerdo a la fuente de datos. Incluye la definición de objetos DTO (*data transfer object*, por sus siglas en inglés) usados para la deserialización de la respuesta de la API REST y Database, así como clases para la conversión de estos objetos a los objetos modelo de la capa de dominio.

### app/presentation
Contiene los componentes de la vista (actividades, fragmentos, adapters, view models, etc...)

## Librerías
La aplicación usa varias libreras de Android y de terceros con el fin de simplificar el desarrollo. En todos los casos se trata de librerías ampliamente usadas por la comunidad.

### Tecnologias utilizadas
* **Retrofit:** simplifica el codigo necesario para la comunicación con la API REST.
* **Gson:** deserialización del resultado de la API REST. En lugar de Gson se consideró también Jackson, que en general se conoce por ser más rápida que la primera. Sin embargo, de acuerdo a las pruebas realizadas se notó que Gson resultó ser ligeramente más eficiente, particularmente al realizar la primera consulta luego de iniciar el proceso de la aplicación.
* **Dagger Hilt:** encargado de la creación e inyección de las dependencias de la aplicación en todos los módulos.
* **Room:** encargado de implementar una capa intermedia entre esta base de datos y el resto de la aplicación
* **Coroutines:** para simplificar el código que se ejecuta de forma asíncrona
* **Flow:** maneja flujos de datos de forma asíncrona
* **Glide:** carga de imágenes desde internet.
* **JUnit:** *framework* para la ejecución de pruebas unitarias.
* **Mockito:** creación de *mocks* de las dependencias de cada clase a probar, según se requiera.

## - Testing:

* Cada capa contiene sus propias pruebas unitarias
* Capa data contiene prueba de integracion para probar la interacción con la base de datos
* Pruebas automatizadas para los flujos básicos del app

| Home | Search History |  Home loaded |  Details |
|:-:|:-:|:-:|:-:|
| ![1](assets/screenshots/screenshot_1.jpeg?raw=true) | ![2](assets/screenshots/screenshot_2.jpeg?raw=true) | ![3](assets/screenshots/screenshot_3.jpeg?raw=true) | ![4](assets/screenshots/screenshot_4.jpeg?raw=true) |

### Desarrollado por

Cristian Anaya [linkedIn](https://www.linkedin.com/in/cristian-david-anaya-alba-46122a1a7/)
