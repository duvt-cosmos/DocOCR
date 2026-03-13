# DocOCR Design Specification

## Overview
DocOCR is a Kotlin Multiplatform (KMP) web application that allows users to upload documents (images, PDFs) and receive extracted text using AWS Textract. 

## Architecture

*   **Frontend Client:** KMP Web Application (targeting `wasmJs` and `js`).
*   **UI Framework:** Compose Multiplatform (Material 3).
*   **Backend Services:**
    *   Existing Java Spring Boot REST API.
    *   Hosted on AWS ECS, fronted by an Application Load Balancer (ALB).
    *   Integrates with AWS Textract SDK to process documents.

## User Interface Design: Dual-Panel Split View

The core interface is designed around a side-by-side comparison model, optimizing for large screens (desktop/tablet web).

### 1. Left Panel (Input / Preview)
*   **Purpose:** Document upload and visual reference.
*   **Initial State:** A large, prominent drag-and-drop zone with a clear call-to-action (e.g., "Drop image/PDF here or click to browse").
*   **Processing State:** Displays a loading indicator overlaid on the upload zone.
*   **Completed State:** Displays a preview thumbnail or scalable image of the uploaded document.

### 2. Right Panel (Output / Results)
*   **Purpose:** Displaying extracted text.
*   **Initial State:** Empty placeholder or subtle guidance text (e.g., "Extracted text will appear here").
*   **Processing State:** Displays a skeleton loader or indeterminate progress indicator.
*   **Completed State:** A scrollable text area containing the plain text extracted from the document. Includes a highly visible "Copy to Clipboard" action button.

## Data Flow

1.  **Upload:** User selects a file via the KMP web interface.
2.  **Request:** The KMP app makes an HTTP POST request to the Spring Boot backend (`/api/ocr/extract`), sending the document data (e.g., as `multipart/form-data`).
3.  **Processing:** 
    *   The Spring Boot backend receives the file.
    *   It securely authenticates with AWS services.
    *   It sends the document payload to the AWS Textract API (`DetectDocumentText` or `StartDocumentTextDetection`).
4.  **Response:** The backend parses the Textract response and returns a simplified JSON payload containing the extracted text string to the KMP app.
5.  **Render:** The KMP app updates the right-hand panel with the returned text.

## Error Handling

*   **Network Errors:** Should gracefully inform the user if the backend is unreachable.
*   **Processing Errors:** Should display specific error messages if AWS Textract fails to read the document (e.g., blurriness, unsupported format).
*   **Validation:** App should restrict uploads to supported file types (e.g., `.png`, `.jpg`, `.pdf`) and potentially enforce a maximum file size limit before sending to the backend.

## Next Steps

1.  Mock the backend response to allow immediate development of the Compose UI.
2.  Implement the Split View layout framework in `commonMain`.
3.  Develop the File Picker / Drag-and-Drop component.
4.  Develop the Results view with clipboard functionality.
5.  Integrate real HTTP calls once the UI is stable.
