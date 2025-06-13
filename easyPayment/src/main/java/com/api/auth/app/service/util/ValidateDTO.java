package com.api.auth.app.service.util;

import com.api.auth.presentation.dto.RegisterDTO;
import java.util.ArrayList;
import java.util.List;

public class ValidateDTO {
    
    public ValidateDTO(){}
    
    public static void validateRegisterDTO(RegisterDTO registerDTO) {
        List<String> errors = new ArrayList<>();

        if (NullorBlank(registerDTO.getName())) {
            errors.add("El name no debe etsar vacio");
        }

        if (NullorBlank(registerDTO.getLastName())) {
            errors.add("El lastName no debe etsar vacio");
        }

        if (NullorBlank(registerDTO.getPhone())) {
            errors.add("El email no debe etsar vacio");
        }

        if (NullorBlank(registerDTO.getPassword())) {
            errors.add("El password no debe etsar vacio");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }

    private static boolean NullorBlank(String value) {
        return value == null || value.isBlank();
    }
}
