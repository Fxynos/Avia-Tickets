package com.vl.aviatickets

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MockAviaTicketsController {
    @GetMapping("/offers")
    fun getOffers() = ResponseEntity.ok("""
        {
          "offers": [
            {
              "id": 1,
              "title": "Die Antwoord",
              "town": "Будапешт",
              "price": {
                "value": 5000
              }
            },
            {
              "id": 2,
              "title": "Socrat&Lera",
              "town": "Санкт-Петербург",
              "price": {
                "value": 1999
              }
            },
            {
              "id": 3,
              "title": "Лампабикт",
              "town": "Москва",
              "price": {
                "value": 2390
              }
            }
          ]
        }
    """.trimIndent())

    @GetMapping("/tickets_offers")
    fun getTicketsOffers() = ResponseEntity.ok("""
        {
          "tickets_offers": [
            {
              "id": 1,
              "title": "Уральские авиалинии",
              "time_range": [
                "07:00",
                "09:10",
                "10:00",
                "11:30",
                "14:15",
                "19:10",
                "21:00",
                "23:30"
              ],
              "price": {
                "value": 3999
              }
            },
            {
              "id": 10,
              "title": "Победа",
              "time_range": [
                "09:10",
                "21:00"
              ],
              "price": {
                "value": 4999
              }
            },
            {
              "id": 30,
              "title": "NordStar",
              "time_range": [
                "07:00"
              ],
              "price": {
                "value": 2390
              }
            }
          ]
        }
    """.trimIndent())

    @GetMapping("/tickets")
    fun getTickets() = ResponseEntity.ok("""
        {
          "tickets_offers": [
            {
              "id": 1,
              "title": "Уральские авиалинии",
              "time_range": [
                "07:00",
                "09:10",
                "10:00",
                "11:30",
                "14:15",
                "19:10",
                "21:00",
                "23:30"
              ],
              "price": {
                "value": 3999
              }
            },
            {
              "id": 10,
              "title": "Победа",
              "time_range": [
                "09:10",
                "21:00"
              ],
              "price": {
                "value": 4999
              }
            },
            {
              "id": 30,
              "title": "NordStar",
              "time_range": [
                "07:00"
              ],
              "price": {
                "value": 2390
              }
            }
          ]
        }
    """.trimIndent())
}