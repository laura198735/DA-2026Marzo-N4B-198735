---
name: generar-dto-desde-clase
user-invocable: true
description: "Use when the user asks to generate a DTO from a domain class in this repository, copying its attributes and creating the constructor, toX method, and list conversion helper similar to CredencialDto. Trigger phrases include: generar dto, crear dto, dto a partir de una clase, dto de dominio, to... y fromList."
---

# Generador de DTOs desde clases de dominio

## Objetivo
Crear una clase DTO en `src/main/java/ort/da/Obligatorio/dtos/` a partir de una clase del paquete `dominio`, siguiendo el estilo de [CredencialDto.java](Obligatorio/src/main/java/ort/da/Obligatorio/dtos/CredencialDto.java).

## Flujo de trabajo
1. Identificar la clase de origen y leer todos sus atributos expuestos por getters o campos visibles para la conversión.
2. Crear el DTO con el mismo nombre base y el sufijo `Dto`.
3. Declarar en el DTO los mismos atributos como campos privados, en el mismo orden de la clase origen.
4. Importar la clase de dominio y `java.util.List`.
5. Implementar el constructor que recibe la clase de dominio y copia todos los atributos uno por uno.
6. Implementar el constructor vacio como  public ClaseDto() {
    }
7. Implementar el método de conversión individual `toX()` que reconstruye la clase de dominio y asigna todos los atributos con setters.
8. Implementar el método de conversión de listas siguiendo la convención del proyecto.
   - Por defecto, usar `toXDtoList(List<X> items)` para mantener la forma de [CredencialDto.java](Obligatorio/src/main/java/ort/da/Obligatorio/dtos/CredencialDto.java).
   - Si el pedido del usuario exige explícitamente `fromList`, usar ese nombre solo si no rompe la convención ya existente en el archivo objetivo.
9. Mantener el estilo simple del proyecto: sin utilidades adicionales, sin reflexión y sin lógica extra fuera de la conversión.

## Criterios de calidad
- Cada atributo de la clase origen queda representado en el DTO.
- El constructor copia todos los atributos.
- El método `toX()` reconstruye la entidad sin omitir campos.
- La conversión de lista usa `stream().map(...).toList()`.
- Los nombres de clase y métodos coinciden con el nombre base de la clase original.

## Resultado esperado
El skill debe producir un DTO listo para compilar, ubicado junto al resto de DTOs del proyecto, y consistente con el patrón de [CredencialDto.java](Obligatorio/src/main/java/ort/da/Obligatorio/dtos/CredencialDto.java).

## Ejemplo de uso
- "Genera el DTO de `Carrera` siguiendo el patrón de `CredencialDto`."
- "Crea `CaballoDto` con constructor, `toCaballo()` y método de lista."
