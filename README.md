# Arkano Test App

Una aplicación Android nativo que mustre una lista de caracteres del universo de Rick y Morty.

## Tech Stack

| Category | Technology                               |
|----------|------------------------------------------|
| **Language** | Kotlin 2.0.21                            |
| **UI Framework** | Jetpack Compose with Material 3          |
| **Architecture** | Clean Architecture + MVVM                |
| **Dependency Injection** | Hilt 2.53.1                              |
| **Networking** | Retrofit 2.11.0 + OkHttp 4.12.0          |
| **Serialization** | Kotlinx Serialization 1.7.3              |
| **Pagination** | Paging 3 (3.3.5)                         |
| **Image Loading** | Coil 2.7.0                               |
| **Navigation** | Navigation Compose 2.8.5                 |
| **Async** | Kotlin Coroutines + Flow                 |
| **Testing** | JUnit 4, MockK, Turbine, Compose Testing |
| **Build System** | Gradle 8.7.2 with Kotlin DSL             |
| **Min SDK** | 24 (Android 7.0)                         |
| **Target SDK** | 36                                       |

## Architecture

Esta aplicación sigue los principios de **Clean Architecture** con una clara separación de responsabilidades.

### Layers

```
app/
├── data/                    # Data Layer
│   ├── mapper/              # DTO to Domain mappers
│   ├── paging/              # Paging sources
│   ├── remote/
│   │   ├── api/             # Retrofit API interfaces
│   │   └── dto/             # Data Transfer Objects
│   └── repository/          # Repository implementations
├── domain/                  # Domain Layer
│   ├── model/               # Domain models
│   ├── repository/          # Repository interfaces
│   └── usecase/             # Business logic use cases
├── presentation/            # Presentation Layer
│   ├── characters/          # Characters feature (Screen, Content, ViewModel)
│   ├── components/          # Reusable UI components
│   └── navigation/          # Navigation setup
├── di/                      # Dependency Injection modules
└── ui/theme/                # Material 3 theming
```

### Data Flow

```
UI (Compose) <-> ViewModel <-> UseCase <-> Repository <-> API Service
                    |
              StateFlow/Flow
```

1. **Presentation Layer**: Pantallas en Jetpack Compose observe `Flow<PagingData>` from ViewModel
2. **Domain Layer**: Los casos de uso orquestan la lógica empresarial e interactúan con los repositorios.
3. **Data Layer**: Los repositorios abstraen las fuentes de datos, PagingSource gestiona la paginación.

### Key Components

- **CharactersScreen**: Pantalla principal con TopAppBar y contenido
- **CharactersContent**: Gestiona los estados de carga, error y éxito con la función de deslizar para actualizar.
- **CharactersViewModel**: Expone datos de caracteres paginados como flujo
- **GetCharactersUseCase**: Lógica de negocio para la obtención de caracteres
- **CharacterRepositoryImpl**: Configura Pager con PagingSource
- **CharacterPagingSource**: Obtiene datos paginados de la API.

## Decisiones Tecnicas Tomadas

### Por que Clean Architecture

Se eligio Clean Architecture para garantizar una clara separacion de responsabilidades, facilitando el testing, la mantenibilidad y la escalabilidad del proyecto. Esta arquitectura permite cambiar la implementacion de la capa de datos (por ejemplo, agregar cache con Room) sin afectar las capas de dominio o presentacion.

### Por que Paging 3 en lugar de paginacion manual

Paging 3 fue seleccionado porque:
- Maneja automaticamente la logica de carga incremental y estados (Loading, Error, NotLoading)
- Se integra nativamente con Compose a traves de `collectAsLazyPagingItems()`
- Gestiona el ciclo de vida y la configuracion de cambios automaticamente
- Soporta pull-to-refresh y retry de forma nativa
- Elimina la necesidad de implementar manualmente la logica de paginacion y manejo de estados

### Por que Flow en lugar de StateFlow para PagingData

Se utiliza `Flow<PagingData<Character>>` en lugar de `StateFlow` porque:
- `PagingData` es un tipo que encapsula internamente sus propios estados de carga
- Paging 3 esta disenado para trabajar con `Flow`, no con `StateFlow`
- La funcion `cachedIn(viewModelScope)` requiere un `Flow` para cachear los datos durante cambios de configuracion
- `collectAsLazyPagingItems()` espera un `Flow<PagingData>` y maneja la conversion internamente
- Usar `StateFlow` romperia la semantica de PagingData y su manejo de estados

### Por que Kotlinx Serialization en lugar de Gson/Moshi

Se opto por Kotlinx Serialization porque:
- Es una solucion oficial de Kotlin, optimizada para el lenguaje
- Ofrece mejor rendimiento que Gson al evitar reflexion en runtime
- Soporta clases de datos de Kotlin de forma nativa (nullability, default values)
- Genera codigo en tiempo de compilacion, detectando errores antes
- Es mas seguro en terminos de tipos comparado con Gson

### Por que Coil en lugar de Glide

Coil fue elegido porque:
- Esta construido con Kotlin y Coroutines desde cero
- Ofrece integracion nativa con Jetpack Compose via `AsyncImage`
- Es mas ligero que Glide (menor tamano de APK)
- Soporta cache en memoria y disco de forma automatica
- La configuracion de `memoryCacheKey` y `diskCacheKey` optimiza el rendimiento

### Estrategia de caching de imagenes

Se implemento caching de imagenes con Coil utilizando:
- `memoryCacheKey`: Usa la URL de la imagen para cache en memoria
- `diskCacheKey`: Usa la URL de la imagen para cache en disco
- `crossfade(true)`: Transicion suave al cargar imagenes
- Placeholders y error drawables personalizados para mejor UX

### Estrategia de manejo de errores

El manejo de errores se implemento a multiples niveles:
- **PagingSource**: Captura excepciones de red y retorna `LoadResult.Error`
- **CharactersContent**: Muestra estado de error con mensaje y boton de reintento
- **Append errors**: Errores de carga de paginas adicionales se muestran inline
- **Pull-to-refresh**: Permite al usuario reintentar la carga completa

## Que Mejoraria con Mas Tiempo

Con tiempo adicional, se implementarian las siguientes mejoras:

- **RemoteMediator con Room**: Implementar estrategia offline-first combinando Paging 3 con Room para cache persistente
- **Animaciones de carga tipo shimmer/skeleton**: Mejorar la experiencia de carga con animaciones de placeholder
- **Pantalla de detalle de personaje**: Mostrar informacion completa incluyendo episodios, origen y ubicacion
- **Funcionalidad de busqueda**: Implementar busqueda con debounce y resultados en tiempo real
- **Internacionalizacion**: Soporte multi-idioma con recursos de strings