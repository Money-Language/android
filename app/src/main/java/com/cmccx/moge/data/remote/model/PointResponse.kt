package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse

data class PointResponse(
    val result: List<Point>
) : BaseResponse()
