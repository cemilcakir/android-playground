package com.ccakir.architecture.arch

sealed class UIEventUnderTest : UIEvent {
    data class InitWithData(val data: String) : UIEventUnderTest()
}