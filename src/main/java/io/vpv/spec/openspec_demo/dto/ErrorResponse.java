package io.vpv.spec.openspec_demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Error response")
public record ErrorResponse(
    @Schema(description = "Timestamp of the error")
    LocalDateTime timestamp,

    @Schema(description = "HTTP status code", example = "400")
    int status,

    @Schema(description = "Error type", example = "Bad Request")
    String error,

    @Schema(description = "Error message", example = "Validation failed")
    String message,

    @Schema(description = "Detailed field errors")
    List<FieldError> errors
) {
    @Schema(description = "Field-level error")
    public record FieldError(
        @Schema(description = "Field name", example = "title")
        String field,

        @Schema(description = "Error message", example = "Title is required")
        String message
    ) {}
}
