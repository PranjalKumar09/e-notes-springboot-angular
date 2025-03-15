package com.pranjal.config;

import com.pranjal.enitity.User;
import com.pranjal.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;


@Slf4j
public class AuditAwareConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        log.info("AuditAwareConfig : getCurrentAuditor()");
        User loggedInUser = CommonUtil.getLoggedInUser();
        if (loggedInUser != null) {
            log.debug("Current auditor: {}", loggedInUser.getId());
            return Optional.of(loggedInUser.getId());
        } else {
            log.debug("No logged-in user, using default auditor: 0");
            return Optional.of(0);
        }
    }
}
