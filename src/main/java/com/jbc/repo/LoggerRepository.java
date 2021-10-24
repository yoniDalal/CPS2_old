package com.jbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jbc.logger.Logger;

public interface LoggerRepository extends JpaRepository<Logger, Long> {

}
