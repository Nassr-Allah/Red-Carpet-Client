package com.maduo.redcarpet.domain.usecase

expect class GetImageFileUseCase

expect fun GetImageFileUseCase.toByteArray(): ByteArray

expect fun GetImageFileUseCase.getFileName(): String