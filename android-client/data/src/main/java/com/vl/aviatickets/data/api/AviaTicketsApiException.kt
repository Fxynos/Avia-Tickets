package com.vl.aviatickets.data.api

import okhttp3.ResponseBody

class AviaTicketsApiException(
    response: ResponseBody?
): RuntimeException("payload: ${response?.string()}")