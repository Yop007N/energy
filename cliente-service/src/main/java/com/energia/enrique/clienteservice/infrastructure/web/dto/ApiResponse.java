package com.energia.enrique.clienteservice.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta API estandarizada para todo el sistema.
 * Proporciona un formato consistente para todas las respuestas HTTP.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;
    private ErrorDetail error;
    private PaginationInfo pagination;
    private MetaInfo meta;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String path;
    private String requestId;

    // Métodos estáticos para crear respuestas exitosas
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    // Métodos estáticos para crear respuestas de error
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, ErrorDetail errorDetail) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(errorDetail)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String code, String field) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .code(code)
                .message(message)
                .field(field)
                .timestamp(LocalDateTime.now())
                .build();

        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(errorDetail)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Métodos para respuestas paginadas
    public static <T> ApiResponse<List<T>> paginated(List<T> data, PaginationInfo pagination) {
        return ApiResponse.<List<T>>builder()
                .success(true)
                .data(data)
                .pagination(pagination)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<List<T>> paginated(List<T> data, PaginationInfo pagination, String message) {
        return ApiResponse.<List<T>>builder()
                .success(true)
                .message(message)
                .data(data)
                .pagination(pagination)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Clase interna para detalles de error
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetail {
        private String code;
        private String message;
        private String field;
        private List<String> details;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime timestamp;
    }

    // Clase interna para información de paginación
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PaginationInfo {
        private Integer page;
        private Integer size;
        private Long totalElements;
        private Integer totalPages;
        private Boolean hasNext;
        private Boolean hasPrevious;
        private Boolean isFirst;
        private Boolean isLast;
    }

    // Clase interna para metainformación
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MetaInfo {
        private String version;
        private String service;
        private Long executionTime;
        private String correlationId;
    }

    // Métodos de utilidad
    public boolean isSuccessful() {
        return Boolean.TRUE.equals(success);
    }

    public boolean hasError() {
        return error != null;
    }

    public boolean hasPagination() {
        return pagination != null;
    }

    public boolean hasData() {
        return data != null;
    }

    @Override
    public String toString() {
        return String.format("ApiResponse{success=%s, message='%s', hasData=%s, hasError=%s}",
                           success, message, hasData(), hasError());
    }
}