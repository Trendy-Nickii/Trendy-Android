package com.kh.ite.rupp.edu.trendy.Util

import okio.IOException

class ApiException(message: String): IOException(message)
class NoInternetException(message: String): IOException(message)