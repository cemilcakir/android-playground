package com.ccakir.architecture.arch

interface UIView<State : UIState> {
    fun renderState(state: State)
}