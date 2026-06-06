---
name: skill-dto
description: 'Generate DTO classes from domain classes in the Obligatorio project. Use when you need a NombreClaseDto in src/main/java/ort/da/Obligatorio/dtos, following the Credencial/CredencialDto pattern, with constructors and conversion helpers. Also use for skilDto requests.'
argument-hint: 'NombreClase objetivo'
---

# Skill DTO

## When to Use
- Create a DTO from a domain class in `src/main/java/ort/da/Obligatorio/dominio`
- Follow the existing `Credencial` -> `CredencialDto` pattern
- Place the generated file in `src/main/java/ort/da/Obligatorio/dtos`
- Name the class `NombreClaseDto`

## Output Contract
- The DTO must be in package `ort.da.Obligatorio.dtos`
- The file must be named `NombreClaseDto.java`
- The class must be named `NombreClaseDto`
- The DTO should mirror the source class fields relevant for transport
- Prefer Lombok `@Data` when the project already uses it

## Generation Procedure
1. Read the source class and identify its state fields.
2. Create the DTO class in the `dtos` package.
3. Add matching fields using the same types unless a transport-friendly type is required.
4. Add a no-args constructor.
5. Add a constructor that receives the domain class and copies its values.
6. Add a `toNombreClase()` method that rebuilds the domain object.
7. Add `fromList(List<NombreClase>)` when batch conversion is useful.
8. Keep imports minimal and preserve the existing code style.

## Pattern Example
- Source class: `Credencial`
- DTO class: `CredencialDto`
- Conversion methods:
  - `new CredencialDto(credencial)`
  - `credencialDto.toCredencial()`

## Rules
- Do not place the DTO outside `ort.da.Obligatorio.dtos`
- Do not rename the source class
- Do not add business logic to the DTO
- Keep the DTO focused on mapping and transport
- Use ASCII-only identifiers and comments unless the existing file already requires otherwise
