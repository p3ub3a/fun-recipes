package com.funrecipesspringaop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funrecipesspringaop.entity.RequestInfo;

public interface MonitoringRepository extends JpaRepository<RequestInfo, Long> {

    RequestInfo findFirstByOrderByGetRequestFailedAttemptsAsc();
}
