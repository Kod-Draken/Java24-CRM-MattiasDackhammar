package se.yrgo.services.calls;

import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.services.customers.CustomerManagementMockImpl;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.Collection;

public class CallHandlingServiceImpl implements CallHandlingService {
    private CustomerManagementService customerManagement;
    private DiaryManagementService diaryManagement;

    public CallHandlingServiceImpl(CustomerManagementService customerManagement, DiaryManagementService diaryManagement) {
        this.customerManagement = customerManagement;
        this.diaryManagement = diaryManagement;

    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        customerManagement.recordCall(customerId, newCall);
        for (Action action : actions) {
        diaryManagement.recordAction(action);
        }
    }
}
