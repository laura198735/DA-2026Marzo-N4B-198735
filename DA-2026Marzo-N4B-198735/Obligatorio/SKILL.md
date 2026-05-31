# SKILL: crear-dto-desde-dominio

## Propósito
Automatiza la creación de una clase DTO (Data Transfer Object) en el paquete `dtos` a partir de una clase de dominio existente. El nombre de la clase DTO será el nombre de la clase de dominio más el sufijo `Dto` (por ejemplo, `Credencial` → `CredencialDto`).

## Proceso paso a paso
1. El usuario indica el nombre de la clase de dominio (por ejemplo, `Credencial`).
2. El agente busca la clase de dominio en el paquete correspondiente.
3. Se genera una nueva clase en la carpeta `dtos` con el nombre `[NombreDominio]Dto`.
4. La clase DTO replica los atributos públicos de la clase de dominio y agrega constructores, getters/setters y métodos de conversión si es necesario.
5. Se sugiere agregar anotaciones como `@Data` de Lombok para simplificar el código.

## Decisiones y criterios de calidad
- El DTO debe contener solo los atributos necesarios para transferencia de datos (evitar lógica de negocio).
- El nombre debe seguir el patrón `[NombreDominio]Dto`.
- El archivo debe guardarse en la carpeta `dtos`.
- Si la clase de dominio cambia, se recomienda regenerar el DTO.

## Ejemplo de uso
- "Crear un DTO para la clase Credencial."
- "Generar UsuarioDto a partir de Usuario."

## Sugerencias de personalización
- Agregar opción para incluir/excluir atributos específicos.
- Permitir generación de métodos de conversión (toDto, fromDto).
- Integrar validaciones básicas (por ejemplo, anotaciones de validación).

---

**Este skill permite estandarizar y acelerar la creación de DTOs en proyectos Java siguiendo buenas prácticas.**
