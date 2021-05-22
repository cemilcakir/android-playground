package com.ccakir.androidplayground.arch

interface UIView<State : UIState> {
    fun renderState(state: State)
}