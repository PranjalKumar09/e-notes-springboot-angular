package com.pranjal.util;

import com.pranjal.config.security.CustomUserDetails;
import com.pranjal.dto.UserResponse;
import com.pranjal.enitity.User;
import com.pranjal.handler.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message("message")
                .data(data)
                .build();
        return response.create();
    }
    public static ResponseEntity<?> createBuildResponseMessage( String message, HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message(message)
                .build();
        return response.create();
    }
    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message("failed")
                .data(data)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message(message)
                .build();
        return response.create();
    }

    public static String getContentType(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "application/octet-stream"; // Default type for unknown files
        }

        String extension = FilenameUtils.getExtension(originalFileName).toLowerCase();

        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "svg":
                return "image/svg+xml";
            case "webp":
                return "image/webp";

            case "pdf":
                return "application/pdf";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";

            case "txt":
                return "text/plain";
            case "csv":
                return "text/csv";
            case "html":
                return "text/html";
            case "xml":
                return "application/xml";
            case "json":
                return "application/json";

            case "mp4":
                return "video/mp4";
            case "avi":
                return "video/x-msvideo";
            case "mov":
                return "video/quicktime";
            case "wmv":
                return "video/x-ms-wmv";
            case "flv":
                return "video/x-flv";
            case "mkv":
                return "video/x-matroska";

            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            case "ogg":
                return "audio/ogg";
            case "flac":
                return "audio/flac";

            case "zip":
                return "application/zip";
            case "rar":
                return "application/x-rar-compressed";
            case "tar":
                return "application/x-tar";
            case "gz":
                return "application/gzip";

            default:
                return "application/octet-stream"; // Default for unknown types
        }
    }

    public static String getUrl(HttpServletRequest request) {
        String apiUrl = request.getRequestURL().toString();
        apiUrl = apiUrl.replace(request.getServletPath(), "");
        return apiUrl;
    }

    public static User getLoggedInUser(){

        try{

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getPrincipal() == null || authentication.getPrincipal().equals("anonymousUser")) {
                log.warn("No authenticated user found, returning null.");
                return null;  // Or return a default User object if needed
            }

            CustomUserDetails logUser = (CustomUserDetails) authentication.getPrincipal();
            return logUser.getUser();


        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}