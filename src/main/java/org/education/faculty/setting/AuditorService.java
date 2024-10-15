package org.education.faculty.setting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.education.faculty.config.SpringSecurityAuditorAware;
import org.education.faculty.dao.entity.Auditor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditorService {
    final SpringSecurityAuditorAware auditorAware;

    public void installAuditInfo(Auditor auditor) {
        String username = auditorAware.getCurrentAuditor().orElse("SYSTEM");
        if (StringUtils.hasText(auditor.getCreatedBy())) {
            auditor.setCreatedBy(username);
            auditor.setCreatedAt(LocalDateTime.now());
        }
        auditor.setModifiedAt(LocalDateTime.now());
        auditor.setModifiedBy(username);
    }
}
