package com.sumdu.edu.ua.ppjk.ebooks.dto.response;

import java.time.LocalDateTime;

public record BookControllerErrorResponse(String message, LocalDateTime time) {
}
