# SKILL: crea-dto — Generador de clases Dto

## Purpose
Automatiza la creación de clases Dto en el package `ort.da.Obligatorio.dtos` siguiendo el patrón establecido en `CredencialDto.java`.

## Input Parameters
- **NombreClase**: Nombre de la clase de dominio (ej: `Credencial`, `Jugador`, `Carrera`)
- **Atributos**: Lista de pares (nombre, tipo) que definen los campos de la clase Dto
- **Path dominio** (opcional): Ubicación de la clase de dominio; por defecto `ort.da.Obligatorio.dominio`

## Target Path
```
C:\DDA\DA-2026Marzo-N4B-198735\DA-2026Marzo-N4B-198735-256380\Obligatorio\src\main\java\ort\da\Obligatorio\dtos\{NombreClase}Dto.java
```

## Workflow

### Step 1: Validar existencia de clase de dominio
- Verificar que `{NombreClase}.java` existe en `ort.da.Obligatorio.dominio`
- Si no existe, reportar error y solicitar crear la clase dominio primero

### Step 2: Extraer atributos de la clase de dominio
- Leer `{NombreClase}.java` para obtener campos privados y sus tipos
- Almacenar lista ordenada de atributos

### Step 3: Generar `{NombreClase}Dto.java`
Crear archivo con estructura:
```java
package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.{NombreClase};
import java.util.List;
import lombok.Data;

@Data
public class {NombreClase}Dto {
    // 1. Declarar atributos (copiar de dominio)
    // 2. Constructor vacío
    // 3. Constructor que recibe {NombreClase}
    // 4. Método to{NombreClase}()
    // 5. Método estático fromList()
}
```

### Step 4: Importaciones dinámicas
- Incluir `import java.util.List` solo si el método `fromList()` se genera
- Incluir `import ort.da.Obligatorio.dominio.{NombreClase}`
- Siempre incluir `import lombok.Data`

### Step 5: Validación y compilación
- Verificar que el archivo se crea en la ubicación correcta
- Ejecutar compilación con Maven para detectar errores tempranos

## Completion Criteria
✅ Archivo `{NombreClase}Dto.java` creado en path correcto  
✅ Todos los atributos mapeados correctamente  
✅ Métodos de conversión funcionan sin errores de compilación  
✅ Nombres de getters/setters coinciden con atributos de dominio  

## Decision Points
- **Clase dominio existe?** → SÍ: continuar; NO: pausar y solicitar crear dominio primero
- **Atributos complejos (objetos/listas)?** → Documentar en comentarios de conversión

## Example Prompts
- "Crea Dto para Credencial"
- "Genera {NombreClase}Dto con atributos de {NombreClase}"
- "Crear CredencialDto automáticamente"

## Related Skills
- **crear-dominio**: Generar clase de dominio base con atributos
- **crear-controller**: Generar controlador REST que use Dtos
- **crear-servicio**: Generar servicio que convierta entre dominio y Dtos
