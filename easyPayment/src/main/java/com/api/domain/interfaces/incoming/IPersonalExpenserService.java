package com.api.domain.interfaces.incoming;

import com.api.app.dto.PersonalExpenseDTO;

public interface IPersonalExpenserService {
    public void createPersonalExpenses(PersonalExpenseDTO dto);
}
