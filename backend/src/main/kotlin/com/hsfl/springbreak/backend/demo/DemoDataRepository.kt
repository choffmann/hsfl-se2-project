package com.hsfl.springbreak.backend.demo

import org.springframework.data.repository.CrudRepository

interface DemoDataRepository : CrudRepository<DemoData, Long> {
}