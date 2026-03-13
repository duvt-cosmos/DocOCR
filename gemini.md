# DocOCR - Context & Guidelines for AI Assistants

## Project Overview
This project, **DocOCR**, is a **Kotlin Multiplatform (KMP)** application utilizing **Compose Multiplatform** to build a shared UI.
Currently, the project targets the **Web** platform (using both `wasmJs` and `js` targets). The bulk of the development should happen in the shared source set located in `composeApp/src/commonMain/kotlin`, while web-specific entry-points and layout (e.g., Split View) reside in `composeApp/src/webMain/kotlin`.

## Technology Stack
- **Languages**: Kotlin (Kotlin DSL for Gradle builds `.gradle.kts`)
- **Key Frameworks**: 
  - Compose Multiplatform (Runtime, Foundation, Material3, Material Icons Extended, UI, Resources)
  - AndroidX Lifecycle for ViewModel (`androidx.lifecycle.viewmodelCompose`) 
- **Platform Targets**: Web (`wasmJs` / `js`)

## Tech Assumptions & Development Guidelines
1. **Kotlin Idioms**: Leverage idiomatic Kotlin, including extension functions, coroutines, sealed classes, and collection processing where appropriate.
2. **Compose Standards**:
   - Favor pure state-driven UI structures. 
   - Write granular, independent, and reusable `@Composable` UI building blocks.
   - Utilize ViewModels when UI state complexity requires it.
3. **Multiplatform Approach**:
   - Write logic and generic UI components (like `UploadPanel` and `ResultPanel`) in `commonMain` to maximize shared code.
   - Use target-specific source sets like `webMain` for main application execution patterns and web-specific layouts.
   - Restrict platform specific codes (`expect`/`actual`) to OS-level operations (e.g., file system access, browser APIs).
4. **Skills/Workflows Awareness**: It is critical to consult the local `.agents/skills` directory and invoke available skills when responding to user instructions (as directed by `using-superpowers`).

## Essential Commands

> Run these commands from the project root (`/Users/tinduvo/Desktop/Projects/aws-practice/DocOCR`):

### Development Server
To launch the app locally for development, run one of the following Gradle commands:

- **Wasm Target** *(Recommended - Faster, Modern Browsers)*
  ```bash
  ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
  ```

- **JS Target** *(Slower, Supports Older Browsers)*
  ```bash
  ./gradlew :composeApp:jsBrowserDevelopmentRun
  ```

### Build Details
- Wasm target build relies on the `@OptIn(ExperimentalWasmDsl::class)` annotation in the `build.gradle.kts`. Use this target as default unless asked otherwise.
- Dependencies such as formatting or external libraries should be kept consistent via `libs.versions.toml` if available, or synchronized across the project configurations.
