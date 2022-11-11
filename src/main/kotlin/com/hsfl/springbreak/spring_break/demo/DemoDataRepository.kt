package com.hsfl.springbreak.spring_break.demo

import org.springframework.data.repository.CrudRepository

interface DemoDataRepository : CrudRepository<DemoData, Long> {
}