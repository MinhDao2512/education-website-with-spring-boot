package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IResponseService {
    /**
     * @param httpStatus {@link HttpStatus} Mã trạng thái HTTP phản hồi thành công.
     * @param message    {@link String} Thông báo phản hồi.
     * @param data       {@link Object} Đối tượng chứa dữ liệu phản hồi (Có thể có hoặc không)
     * @return {@link ResponseEntity} chứa {@link ApiResponseDTO}.
     */
    ResponseEntity<ApiResponseDTO> success(HttpStatus httpStatus, String message, Object... data);

    /**
     * @param request      {@link HttpServletRequest}
     * @param httpStatus   {@link HttpStatus} Mã trạng thái HTTP phản hồi thất bại.
     * @param message      {@link String} Thông báo phản hồi.
     * @param errorDetails {@link Object} Đối tượng chứa chi tiết lỗi (Có thể có hoặc không)
     * @return {@link ResponseEntity} chứa {@link ApiResponseDTO}.
     */
    ResponseEntity<ApiResponseDTO> fail(HttpServletRequest request, HttpStatus httpStatus, String message,
                                        Object... errorDetails);
}
