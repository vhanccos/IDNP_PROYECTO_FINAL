# 🏛️ Guía Interactiva de Museos de Arequipa

## 📘 Resumen Ejecutivo

Este proyecto es una **aplicación móvil Android**, desarrollada en **Kotlin con Jetpack Compose**, diseñada para enriquecer la **experiencia turística en la ciudad de Arequipa**.  
La aplicación actúa como una **guía interactiva de museos**, permitiendo a los usuarios:

- Explorar museos mediante un **mapa interactivo**.
- Consultar **información detallada** de cada museo.
- Realizar **recorridos virtuales o en tiempo real** dentro de las instalaciones.

Una de las características clave es la **simulación de presencia del usuario** dentro de un museo, activando la pantalla de “Recorrido Activo” donde se muestran datos curiosos y contenido dinámico.  
Además, la app incluye un componente **social y de gamificación**, con un perfil de usuario, estadísticas de visitas y una **galería de fotos personal** asociada a cada aventura o visita.

---

## 🖥️ Descripción de Interfaces Implementadas

A continuación se detallan las principales pantallas e interfaces de la aplicación:

### `HomeScreen`

- **Propósito:** Pantalla de inicio y punto central de navegación.
- **Comportamiento Principal:** Muestra un mapa interactivo centrado en Arequipa, permitiendo visualizar museos. Incluye botón para ir a `MuseumListScreen` y pop-up para iniciar recorrido (`ActiveTourScreen`) si el usuario está dentro de un museo.

### `MuseumListScreen`

- **Propósito:** Mostrar una lista completa de museos disponibles.
- **Comportamiento Principal:** Presenta tarjetas con imagen, nombre y calificación. Al seleccionar un museo, se navega a `MuseumDetailScreen`.

### `MuseumDetailScreen`

- **Propósito:** Brindar información detallada sobre un museo seleccionado.
- **Comportamiento Principal:** Muestra descripción, imágenes, calificación y pestañas informativas. Contiene un botón “Iniciar Recorrido” que dirige a `ActiveTourScreen`.

### `ActiveTourScreen`

- **Propósito:** Simular un recorrido en tiempo real dentro del museo.
- **Comportamiento Principal:** Presenta fondo visual del museo con datos curiosos superpuestos y nombre del museo actual, ofreciendo una experiencia inmersiva.

### `EventsScreen`

- **Propósito:** Informar sobre eventos y notificaciones turísticas relevantes.
- **Comportamiento Principal:** Lista eventos con nombre, fecha y ubicación. Puede mostrar alertas o recordatorios configurables por el usuario.

### `ProfileScreen`

- **Propósito:** Mostrar el perfil del usuario y su actividad dentro de la app.
- **Comportamiento Principal:** Incluye nombre, estadísticas de visitas, configuraciones y acceso a “Mis Aventuras” (`TripListScreen`).

### `TripListScreen`

- **Propósito:** Agrupar las visitas o “aventuras” del usuario.
- **Comportamiento Principal:** Muestra una cuadrícula de tarjetas representando cada visita. Al seleccionar una, se abre `TripGalleryScreen`.

### `TripGalleryScreen`

- **Propósito:** Mostrar la galería de fotos de una aventura específica.
- **Comportamiento Principal:** Presenta fotos tomadas durante una visita. Permite agregar nuevas imágenes (FAB) y verlas en detalle (`GalleryDetailScreen`).

### `GalleryDetailScreen`

- **Propósito:** Visualizar una foto seleccionada en tamaño completo.
- **Comportamiento Principal:** Permite ver, compartir o descargar la imagen, y regresar a la galería anterior.

---

## ⚙️ Instrucciones de Ejecución

Sigue los siguientes pasos para compilar o ejecutar el proyecto:

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/vhanccos/IDNP_PROYECTO_FINAL
   ```

o descargar el archivo ZIP desde GitHub.

2. **Abrir el proyecto:**
   - Descomprime el archivo (si descargaste el ZIP).
   - Abre **Android Studio** y selecciona la carpeta del proyecto.

3. **OPCION A: Ejecutar la aplicación en modo desarrollo:**
   - Conecta un dispositivo Android o utiliza un emulador.
   - Haz clic en **Run ▶️** dentro de Android Studio.

4. **OPCION B: Generar APK de lanzamiento y firmar manualmente:**

   ```bash
   ./gradlew assembleRelease
   ```

   El archivo generado se encontrará en:

   ```
   app/build/outputs/apk/release/app-release-unsigned.apk
   ```

   Para publicar o instalar en un dispositivo real, el APK debe ser firmado.
   Usa el siguiente comando (ajustando tus rutas y alias):

   ```bash
   apksigner sign \
     --ks-pass pass:password \
     --ks ~/AndroidStudioProjects/my-release-key.jks \
     --ks-key-alias miAlias \
     app/build/outputs/apk/release/app-release-unsigned.apk
   ```

   📘 [Guía oficial para firmar APKs](https://developer.android.com/studio/publish/app-signing)
