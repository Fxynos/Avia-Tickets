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
              "image_url": "https://i1.sndcdn.com/artworks-nbXEtIsuHFab90iU-mPzLmw-t500x500.jpg",
              "town": "Будапешт",
              "price": {
                "value": 5000
              }
            },
            {
              "id": 2,
              "title": "Socrat&Lera",
              "image_url": "https://spb.afishagoroda.ru/storage/media/Events/31351/images/204905/conversions/30ed39029c493404df7f53f45903de23-large-x2.jpg",
              "town": "Санкт-Петербург",
              "price": {
                "value": 1999
              }
            },
            {
              "id": 3,
              "title": "Лампабикт",
              "image_url": "https://rock63.ru/sites/default/files/afisha/2023/12/01-lampabikt.jpg",
              "town": "Москва",
              "price": {
                "value": 2390
              }
            }
          ]
        }
    """.trimIndent()).also {
        Thread.sleep(3000) // imitate delay to show shimmer
    }

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
          "tickets": [
            {
              "id": 100,
              "badge": "Самый удобный",
              "price": {
                "value": 1999
              },
              "provider_name": "На сайте Купибилет",
              "company": "Якутия",
              "departure": {
                "town": "Москва",
                "date": "2024-02-23T03:15:00",
                "airport": "VKO"
              },
              "arrival": {
                "town": "Сочи",
                "date": "2024-02-23T07:10:00",
                "airport": "AER"
              },
              "has_transfer": false,
              "has_visa_transfer": false,
              "luggage": {
                "has_luggage": false,
                "price": {
                  "value": 1082
                }
              },
              "hand_luggage": {
                "has_hand_luggage": true,
                "size": "55x20x40"
              },
              "is_returnable": false,
              "is_exchangable": true
            },
            {
              "id": 101,
              "price": {
                "value": 9999
              },
              "provider_name": "На сайте Победа",
              "company": "Победа",
              "departure": {
                "town": "Москва",
                "date": "2024-02-23T04:00:00",
                "airport": "VKO"
              },
              "arrival": {
                "town": "Сочи",
                "date": "2024-02-23T08:30:00",
                "airport": "AER"
              },
              "has_transfer": false,
              "has_visa_transfer": false,
              "luggage": {
                "has_luggage": false,
                "price": {
                  "value": 1602
                }
              },
              "hand_luggage": {
                "has_hand_luggage": true,
                "size": "36x30x27"
              },
              "is_returnable": false,
              "is_exchangable": true
            },
            {
              "id": 102,
              "price": {
                "value": 8500
              },
              "provider_name": "На сайте Turkish Airlines",
              "company": "Турецкие авиалинии",
              "departure": {
                "town": "Москва",
                "date": "2024-02-23T15:00:00",
                "airport": "VKO"
              },
              "arrival": {
                "town": "Сочи",
                "date": "2024-02-23T18:40:00",
                "airport": "AER"
              },
              "has_transfer": false,
              "has_visa_transfer": false,
              "luggage": {
                "has_luggage": true
              },
              "hand_luggage": {
                "has_hand_luggage": true,
                "size": "55x20x40"
              },
              "is_returnable": false,
              "is_exchangable": false
            },
            {
              "id": 103,
              "badge": "Рекомендуемый",
              "price": {
                "value": 8086
              },
              "provider_name": "На сайте Уральские авиалинии",
              "company": "Уральские авиалинии",
              "departure": {
                "town": "Москва",
                "date": "2024-02-23T11:30:00",
                "airport": "VKO"
              },
              "arrival": {
                "town": "Сочи",
                "date": "2024-02-23T15:00:00",
                "airport": "AER"
              },
              "has_transfer": false,
              "has_visa_transfer": false,
              "luggage": {
                "has_luggage": false,
                "price": {
                  "value": 1570
                }
              },
              "hand_luggage": {
                "has_hand_luggage": true,
                "size": "55x20x40"
              },
              "is_returnable": true,
              "is_exchangable": true
            },
            {
              "id": 104,
              "price": {
                "value": 11560
              },
              "provider_name": "На сайте Aeroflot",
              "company": "Аэрофлот",
              "departure": {
                "town": "Москва",
                "date": "2024-02-23T11:50:00",
                "airport": "VKO"
              },
              "arrival": {
                "town": "Сочи",
                "date": "2024-02-23T15:20:00",
                "airport": "AER"
              },
              "has_transfer": true,
              "has_visa_transfer": false,
              "luggage": {
                "has_luggage": false,
                "price": {
                  "value": 999
                }
              },
              "hand_luggage": {
                "has_hand_luggage": true,
                "size": "55x20x40"
              },
              "is_returnable": false,
              "is_exchangable": true
            },
            {
              "id": 105,
              "price": {
                "value": 15600
              },
              "provider_name": "На сайте Aeroflot",
              "company": "Аэрофлот",
              "departure": {
                "town": "Москва",
                "date": "2024-02-23T13:50:00",
                "airport": "VKO"
              },
              "arrival": {
                "town": "Сочи",
                "date": "2024-02-23T17:20:00",
                "airport": "AER"
              },
              "has_transfer": true,
              "has_visa_transfer": true,
              "luggage": {
                "has_luggage": false,
                "price": {
                  "value": 1999
                }
              },
              "hand_luggage": {
                "has_hand_luggage": true,
                "size": "55x20x40"
              },
              "is_returnable": true,
              "is_exchangable": true
            },
            {
              "id": 106,
              "price": {
                "value": 9500
              },
              "provider_name": "На сайте Aeroflot",
              "company": "Аэрофлот",
              "departure": {
                "town": "Москва",
                "date": "2024-02-23T21:00:00",
                "airport": "VKO"
              },
              "arrival": {
                "town": "Сочи",
                "date": "2024-02-24T00:20:00",
                "airport": "AER"
              },
              "has_transfer": false,
              "has_visa_transfer": false,
              "luggage": {
                "has_luggage": false,
                "price": {
                  "value": 5999
                }
              },
              "hand_luggage": {
                "has_hand_luggage": false
              },
              "is_returnable": false,
              "is_exchangable": false
            }
          ]
        }
    """.trimIndent())
}