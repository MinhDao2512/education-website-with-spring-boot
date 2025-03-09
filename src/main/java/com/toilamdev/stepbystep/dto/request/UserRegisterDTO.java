package com.toilamdev.stepbystep.dto.request;

import com.toilamdev.stepbystep.validation.annotation.EmailAddress;
import com.toilamdev.stepbystep.validation.annotation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

@Getter
public class UserRegisterDTO {
    @EmailAddress(message = "Email đã tồn tại hoặc không hợp lệ")
    @NotBlank(message = "Bạn chưa nhập thông tin này")
    @Size(max = 100, message = "Số lượng ký tự không được > 100")
    private String email;

    @NotBlank(message = "Bạn chưa nhập thông tin này.")
    @Size(min = 1, max = 20, message = "Số lượng ký tự không được < 1 hoặc > 20")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Tên không được chứa ký tự đặc biệt và số")
    private String firstName;

    @NotBlank(message = "Bạn chưa nhập thông tin này.")
    @Size(min = 2, max = 80, message = "Số lượng ký tự không được < 2 hoặc > 80")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Họ và tên đệm không được chứa ký tự đặc biệt và số")
    private String lastName;

    @NotBlank(message = "Bạn chưa nhập thông tin này.")
    @Size(min = 8, max = 32, message = "Số lượng ký tự không được < 8 hoặc > 32")
    private String password;

    @NotBlank(message = "Bạn chưa nhập thông tin này.")
    @Size(min = 10, max = 20, message = "Số lượng ký tự không được < 10 và > 20")
    @PhoneNumber(message = "Số điện thoại đã tồn tại")
    private String phoneNumber;
}
