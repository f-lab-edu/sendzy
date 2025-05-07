package com.donggi.sendzy.account.application;

import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.dto.AccountBalanceResponse;
import com.donggi.sendzy.remittance.application.RemittanceExpirationService;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountBalanceQueryService {

    private final AccountService accountService;
    private final RemittanceRequestRepository remittanceRequestRepository;
    private final RemittanceExpirationService remittanceExpirationService;

    @Transactional(readOnly = true)
    public AccountBalanceResponse getBalanceWithRequestExpiredCheck(final long memberId) {
        final var account = accountService.getByMemberId(memberId);
        final var now = LocalDateTime.now();
        final List<RemittanceRequest> pendingRequests = remittanceRequestRepository.findPendingRequestsBySenderId(memberId);

        final Map<Boolean, List<RemittanceRequest>> requestsByExpiration = pendingRequests.stream()
            .collect(Collectors.partitioningBy(r -> r.isExpired(now)));
        final var expiredRequests = requestsByExpiration.get(true);
        final var activeRequests = requestsByExpiration.get(false);

        // 만료된 요청 갱신
        expiredRequests.forEach(remittanceExpirationService::expireRequest);

        return AccountBalanceResponse.from(account, activeRequests);
    }
}
