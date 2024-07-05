# Final Programacion Orientada a Objetos
Trabajo Practico Final

## Descripción del proyecto:
La idea principal del proyecto es diseñar una aplicación con JavaFX, separando bien el frontend del backend y utilizando apropiadamente el paradigma de la programación orientada a objetos(POO). Además se debe poder reutilizar el front para otro back implementado con otra tecnologia diferente a JavaFX.

La aplicación debe permitir dibujar figuras (Circulos, elipses, rectangulos y cuadrados), con distintos colores (con posibilidad de un gradiente entre 2 colores), con distintos tipos de sombras, grosor de linea del borde variable y distintos tipos de borde. Además el programa debe tener un sistema de capas y uno de etiquetas.

## Descripcion detallada de las Funcionalidades:

### 1.Sombra, Relleno y Borde:
#### 1.1 Sombra
Ademas de Poder dibujar las cuatro figuras ya mencionadas, el programa debe permitir añadir sombrar o crear figuras con sombras. Las sombras deben tener el siguíente comportamiento:

- Simple: Dibujar la sombra consiste en dibujar la figura desplazada en un offset positivo utilizando el color gris.
- Coloreada: Dibujar la sombra consiste en dibujar la figura desplazada en un offset positivo utilizando una versión oscurecida del color de relleno de la figura
- Simple Inversa: Dibujar la sombra consiste en dibujar la figura desplazada en un offset negativo utilizando el color gris. 
- Coloreada Inversa: Dibujar la sombra consiste en dibujar la figura desplazada en un offset negativo utilizando una versión oscurecida del color de relleno de la figura
#### 1.2 Relleno
Se deben poder elegir 2 colores de relleno, estos deben formar un gradiente entre ambos. Para poder elegir un solo color ambos colores seleccionados deben ser iguales. Para esto se utilizan dos ColorPiker que modificaran el comportamiento de la figura seleccionada.
#### 1.3 Borde
Los bordes de las figuras permiten modificar su grosor mediante un silider ( Una barra de dezplazamiento ), además se dispone de una ChoiceBox que permite la modificación del estilo de una sombra. Los estilos de sombras son los siguientes:

- Normal: La línea continua de la implementación original
- Punteado simple: Un patrón uniforme de “-” separados por espacios, es decir, todos del mismo ancho
- Punteado complejo: Un patrón de “--” y “-” separados por espacios, es decir, uno de ancho simple seguido de uno de doble ancho, seguido de uno de ancho simple, etc.
### 2. Duplicar, Dividir y Mover al Centro
#### 2.1 Duplicar 
El boton duplicar, genera una nueva figura identica a la seleccionada, no solo forma, sino que tambien el resto de propiedades. Esta nueva figura se genera con un pequeño offset en ambos ejes.
#### 2.2 Dividir
El boton dividir, elimina la figura seleccionada y genera 2 nuevas de mitad de alto y mitad de ancho, las cuales se posicionan una al lado de la otra tocandose en el medio de la figura original.
#### 2.3 Mover al centro
El boton Mov. Centro como indica su nombre mueve la figura seleccionada al centro del lienzo.
### 3. Capas: Crear y Mostrar
En la barra superior se incluyen varios botones que permiten la creacion y eliminacion de capas. Estas se pueden hacer visibles como invisibles con los botones mostrar y ocultar, respectivamente.
> [!NOTE]
> Las primeras tres capas no son eliminables.
### 4. Etiquetas: Mostrar y Ocultar
En la barra superior se encuentra una barra de texto que permite mostrar las figuras que tienen la etiqueta que se escriba en ella, siendo considerado etiqueta a la primer palabra ingresada. Esta funcionalidad se activa con el boton solo y se desactiva con todas.

## Integrantes:
    Mateo Buela             - 64680 - mbuela@itba.edu.ar
    Matias Cuneo Gima       - 64492 - mcuneogima@itba.edu.ar
    Nahuel Ignacio Prado    - 64276 - naprado@itba.edu.ar
    Augusto Andres Lanari   - 64679 - alanari@itba.edu.ar
