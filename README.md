# Desafío Android para Banco Galicia

## Estructura del proyecto

com.nmarchelli.desafiotecnico
- data
  - model (Clases de datos)
  - network (API Service con Retrofit)
  - repository (Repositorios)
- di (Configuración de Retrofit)
- ui
  - view (Pantallas y Composables)
  - viewmodel (Capa de negocio)


## Decisiones Técnicas

1. Jetpack Compose
   - Interfaz declarativa y rápida para listar usuarios y mostrar dialogs.
   - LazyColumn para paginación.
2. Coroutines + Retrofit
   - Llamadas de red asíncronas con funciones suspend
   - ViewModelScope para manejar el ciclo de vida de las coroutines
3. Hilt para inyección de dependencias
   - Repos y ViewModels inyectados.
   - Facilita testing y escalabilidad
4. Testing con JUnit y FakeRepository
   - FakeUserRepository simula la API para testear la carga de usuarios y consistencia de la seed


## Para correr la app

1. Abrir el proyecto en Android Studio.
2. Ejecutar MainActivity
3. Navegar entre usuarios, marcar favoritos y aplicar filtros de nacionalidad.


## Para correr los tests

1. Los tests unitarios se encuentran en src/test/java.
2. Ejecutar desde Android Studio:
   - Click derecho en la clase de test → Run 'UserRepositoryTest'
   - Se puede usar FakeUserRepository para validar consistencia de datos y favoritos.

