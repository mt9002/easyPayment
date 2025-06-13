package com.api.bill.domain.ports.incoming;

import com.api.bill.presentation.dto.PersonalExpenseDTO;
import com.api.auth.app.service.util.Response;

public interface IPersonalExpenserService {
    public Response createPersonalExpenses(PersonalExpenseDTO dto);
}
