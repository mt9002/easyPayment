package com.api.personalExpenser.domain.incoming;

import com.api.bill.app.dto.PersonalExpenseDTO;
import com.api.util.Response;

public interface IPersonalExpenserService {
    public Response createPersonalExpenses(PersonalExpenseDTO dto);
}
