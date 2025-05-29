package com.api.domain.interfaces.incoming;

import com.api.app.dto.PersonalExpenseDTO;
import com.api.domain.services.util.Response;

public interface IPersonalExpenserService {
    public Response createPersonalExpenses(PersonalExpenseDTO dto);
}
